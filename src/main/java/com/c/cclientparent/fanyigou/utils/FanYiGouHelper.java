package com.c.cclientparent.fanyigou.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.c.cclientparent.common.exception.ServiceException;
import com.c.cclientparent.common.util.FileUtils;
import com.c.cclientparent.common.util.HttpPostUtil;
import com.c.cclientparent.common.util.HttpUtils;
import com.c.cclientparent.fanyigou.constants.FanYiGouBaseURL;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
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
    public BaseResult uploadFileAndTranslate(MultipartFile multipartFile, String fromLan, String toLan){
        if(Objects.isNull(multipartFile)){
            return BaseResult.failed("上传文件为空");
        }
        if(StringUtils.isBlank(fromLan)){
            return BaseResult.failed("源语言不能为空");
        }
        if(StringUtils.isBlank(toLan)){
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
            String fileName = multipartFile.getName();
            HttpPostUtil httpPostUtil = new HttpPostUtil(FanYiGouBaseURL.HOST + FanYiGouBaseURL.UPLOAD_FILE, fileName);
            httpPostUtil.addFileParameter("multipartFile", fiss);
            httpPostUtil.addTextParameter("from", fromLan);
            httpPostUtil.addTextParameter("to", toLan);
            httpPostUtil.addTextParameter("md5", md5);
            httpPostUtil.addTextParameter("appid", FanYiGouBaseURL.APPID);
            httpPostUtil.addTextParameter("nonce_str", nonce_str);

            Map<String,String> params = new HashMap<>();
            params.put("from", fromLan);
            params.put("to", toLan);
            params.put("md5", md5);
            params.put("nonce_str", nonce_str);

            String token = CreateSignUtil.createSign(params);
            httpPostUtil.addTextParameter("token", token);

            Date date = new Date();
            byte[] b = httpPostUtil.send();
            System.out.println("耗费：" + (System.currentTimeMillis() - date.getTime()));
            String result = new String(b,"utf-8");
            //打印返回结果
            System.out.println(result);
            return JSON.parseObject(result, BaseResult.class);
        }catch (Exception e) {
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
                    log.info("临时文件删除失败");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }




    public BaseResult download(String tid, String dType) {
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
            Map<String,String> params = new HashMap<>();
            params.put("tid", tid);
            params.put("nonce_str", nonce_str);
            params.put("dtype", dType);
            querys.put("token", CreateSignUtil.createSign(params));

            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, new HashMap<>(), querys);
                String contentType = response.getEntity().getContentType().toString();
                System.out.println(contentType);
                //可以下载文件
                if(contentType.indexOf("application/octet-stream") >= 0) {
                    //下载至本地文件
                    InputStream is = response.getEntity().getContent();
                    File file = new File("C:\\Users\\Chen\\Desktop\\翻译后文件.pdf");
                    FileOutputStream baos = new FileOutputStream(file);
                    byte[] b = new byte[1024];
                    int n = 0;
                    while ((n = is.read(b, 0, 1024)) > 0) {
                        baos.write(b, 0, n);
                    }
                    baos.close();
                    is.close();
                }else {//文本信息
                    String resJson = EntityUtils.toString(response.getEntity());
                    System.out.println(resJson);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return BaseResult.success();
    }


}
