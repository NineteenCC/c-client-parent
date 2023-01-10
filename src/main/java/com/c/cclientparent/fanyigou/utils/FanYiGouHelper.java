package com.c.cclientparent.fanyigou.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c.cclientparent.common.exception.ServiceException;
import com.c.cclientparent.common.util.FileUtils;
import com.c.cclientparent.common.util.HttpPostUtil;
import com.c.cclientparent.common.util.HttpUtils;
import com.c.cclientparent.fanyigou.constants.FanYiGouBaseURL;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
import com.c.cclientparent.fanyigou.requestpojo.vo.DetectPageVo;
import com.c.cclientparent.fanyigou.requestpojo.vo.QueryProgressVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 翻译狗请求封装
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:16]
 */
@Component
@Slf4j
public class FanYiGouHelper {


    /**
     * @param multipartFile
     * @param fromLan
     * @param toLan
     * @return
     */
    public BaseResult uploadFileAndTranslate(MultipartFile multipartFile, String fromLan, String toLan) {
        if (Objects.isNull(multipartFile)) {
            return BaseResult.failed("上传文件为空");
        }
        if (StringUtils.isBlank(fromLan)) {
            return BaseResult.failed("源语言不能为空");
        }
        if (StringUtils.isBlank(toLan)) {
            return BaseResult.failed("目标语言不能为空");
        }

        File file = FileUtils.transferToFile(multipartFile);
        FileInputStream fis = null;
        FileInputStream fiss = null;

        System.out.println("文件长度" + multipartFile.getSize());

        try {
            fis = new FileInputStream(file);
            fiss = new FileInputStream(file);
            String md5 = DigestUtils.md5Hex(fis);
            String nonce_str = UUID.randomUUID().toString();
            String fileName = multipartFile.getOriginalFilename();
            HttpPostUtil httpPostUtil = new HttpPostUtil(FanYiGouBaseURL.HOST + FanYiGouBaseURL.UPLOAD_FILE, fileName);
            httpPostUtil.addFileParameter("multipartFile", fiss);
            httpPostUtil.addTextParameter("from", fromLan);
            httpPostUtil.addTextParameter("to", toLan);
            httpPostUtil.addTextParameter("md5", md5);
            httpPostUtil.addTextParameter("appid", FanYiGouBaseURL.APPID);
            httpPostUtil.addTextParameter("nonce_str", nonce_str);

            Map<String, String> params = new HashMap<>();
            params.put("from", fromLan);
            params.put("to", toLan);
            params.put("md5", md5);
            params.put("nonce_str", nonce_str);

            String token = CreateSignUtil.createSign(params);
            httpPostUtil.addTextParameter("token", token);

            Date date = new Date();
            byte[] b = httpPostUtil.send();
            System.out.println("耗费：" + (System.currentTimeMillis() - date.getTime()));
            String result = new String(b, "utf-8");
            //打印返回结果
            System.out.println(result);
            return JSON.parseObject(result, BaseResult.class);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fiss != null) {
                    fiss.close();
                }
                if (!file.delete()) {
                    log.info("临时文件删除失败");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }






    /**
     *
     * @param tid
     * @param dType 下载的文件类型(new):
     *     2：翻译后的pdf文件，支持格式：word、pdf、img。
     *     3：翻译后的word文件（支 持格式：word、pdf)；ppt文件（支持格式：img、ppt）；excel文件（支持格式：excel）
     *     6:转换后的文档
     */
    public InputStream download(String tid, String dType) {
        try {
            tid = "44090965";
            dType = "2";
            String host = FanYiGouBaseURL.HOST;
            String path = FanYiGouBaseURL.DOWNLOAD_FILE;
            String method = "GET";
            String nonce_str = UUID.randomUUID().toString();
            Map<String, String> querys = new HashMap<>();
            querys.put("tid", tid);
            querys.put("appid", FanYiGouBaseURL.APPID);
            querys.put("dtype", dType);
            querys.put("nonce_str", nonce_str);

            //参数加签，参数为空不参与加签
            Map<String, String> params = new HashMap<>();
            params.put("tid", tid);
            params.put("nonce_str", nonce_str);
            params.put("dtype", dType);
            querys.put("token", CreateSignUtil.createSign(params));

            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, new HashMap<>(), querys);
                String contentType = response.getEntity().getContentType().toString();
                log.info("contentType : {}", contentType);
                //可以下载文件
                if (contentType.contains("application/octet-stream")) {
                    //下载至本地文件
                    return response.getEntity().getContent();
                } else {//文本信息
                    String resJson = EntityUtils.toString(response.getEntity());
                    log.info("resJson : {}", resJson);
                }
            } catch (Exception e) {
                log.error("翻译狗文件下载失败 : {}", e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            log.error("翻译狗文件下载失败 : {}", e.getMessage());
            e.printStackTrace();
        }
        return new ByteArrayInputStream(new byte[0]);
    }


    public BaseResult download(String tid, String dType, String savePath) {
        try {
            InputStream download = this.download(tid, dType);
            //下载至本地文件
            File file = new File(savePath);
            FileOutputStream baos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = download.read(b, 0, 1024)) > 0) {
                baos.write(b, 0, n);
            }
            baos.close();
            download.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return BaseResult.success();
    }

    public DetectPageVo checkPageNumber(MultipartFile multipartFile){
        return this.checkPageNumber(FileUtils.transferToFile(multipartFile));
    }

    public DetectPageVo checkPageNumber(File file){
        FileInputStream fis = null;
        FileInputStream fiss = null;

        try {
            log.info("开始检查文件页码, 传入文件大小: {}", file.length());
            fis = new FileInputStream(file);
            //文件md5
            String md5 = DigestUtils.md5Hex(fis);
            //如有其它参数请添加进去即可
            fiss = new FileInputStream(file);
            String fileName = file.getName();
            HttpPostUtil u = new HttpPostUtil(FanYiGouBaseURL.HOST + FanYiGouBaseURL.DETECT_PAGE_PATH, fileName);
            u.addFileParameter("file", fiss);
            u.addTextParameter("md5", md5);
            u.addTextParameter("appid", FanYiGouBaseURL.APPID);
		     //u.addTextParameter("transImg", "1");
		     //u.addTextParameter("excelMode", "1");
		     //u.addTextParameter("bilingualControl", "1");
		     //u.addTextParameter("industryId", "6");
            //参数加签，参数为空不参与加签
            Map<String,String> params = new HashMap<>();
            params.put("md5", md5);
		     //params.put("transImg","1");
		     //params.put("excelMode","1");
		     //params.put("bilingualControl","1");
            String nonce_str =  UUID.randomUUID().toString();
            params.put("nonce_str", nonce_str);
		     //params.put("industryId", "6");
            u.addTextParameter("nonce_str", nonce_str);
		     //u.addTextParameter("token", "232323");
            String token = CreateSignUtil.createSign(params);
            log.info("token : {}",token);
            u.addTextParameter("token", token);
            Date date = new Date();
            byte[] b = u.send();
            System.out.println("耗费：" + (System.currentTimeMillis() - date.getTime()));
            String result = new String(b,"utf-8");
            //打印返回结果
            log.info("检查文件页数响应结果:{}",JSON.toJSONString(result));
            BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
            if(baseResult.getCode() == 100){
                String jsonData = JSON.toJSONString(baseResult.getData());
                DetectPageVo detectPageVo = JSON.parseObject(jsonData, DetectPageVo.class);
                return detectPageVo;
            }else {
                throw new ServiceException(baseResult.getMsg());
            }
        } catch (Exception e) {
            log.error("查询文件页码系统异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fiss != null) {
                    fiss.close();
                }
                if(!file.delete()){
                    log.error("本地文件删除失败: {}", file.getName());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public QueryProgressVo queryProgress(String tid){
        try {
            String host = FanYiGouBaseURL.HOST;
            String path = FanYiGouBaseURL.GO_QUERY_PATH;
            String method = "GET";
            String nonce_str = UUID.randomUUID().toString();
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("tid", String.valueOf(tid));
            querys.put("appid", FanYiGouBaseURL.APPID);
            querys.put("nonce_str", nonce_str);

            //参数加签，参数为空不参与加签
            Map<String,String> params = new HashMap<>();
            params.put("tid", String.valueOf(tid));
            params.put("nonce_str", nonce_str);

            querys.put("token", CreateSignUtil.createSign(params));

            HttpResponse response = HttpUtils.doGet(host, path, method, new HashMap<>(), querys);
            response.getHeaders("Content-Type");
            String resJson = EntityUtils.toString(response.getEntity());
            System.out.println(resJson);
            JSONObject json = JSON.parseObject(resJson);
            String code = json.getString("code");
            //请求成功
            if("100".equals(code)) {
                BaseResult baseResult = JSON.parseObject(resJson, BaseResult.class);
                JSONObject dataJson = json.getJSONObject("data");
                int status =  dataJson.getIntValue("status");

                //正在翻译
                if(status == 313) {
                    log.error("查询翻译进度失败 : {}", json.getString("msg"));
                //翻译成功
                }else if(status == 314) {
                    log.info("查询翻译进度成功: {}", tid);
                    //翻译失败
                }else {
                    log.error("查询翻译进度失败 : {}", json.getString("msg"));
                }
                return JSON.parseObject(JSON.toJSONString(baseResult.getData()), QueryProgressVo.class);
            }else {//失败
                log.error("查询翻译进度失败 : {}", json.getString("msg"));
                throw new ServiceException("查询翻译进度失败");
            }

        } catch (Exception e) {
            // TODO: handle exception
            log.error("查询翻译进度失败 : {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

}
