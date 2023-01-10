package com.c.cclientparent.common.util;

import com.alibaba.fastjson.JSON;
import com.c.cclientparent.fanyigou.constants.SupportFileFormat;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理工具
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:31]
 */
public class FileUtils {

    private static String SEPARATOR = "-SEPARATOR";

    private static long FILE_MAX_SIZE = 5242880;

    private static BigDecimal CONVENT_KB_TO_MB = new BigDecimal(1024 * 1024);

    public static boolean checkFileSize(long fileSize) {
        return fileSize < FILE_MAX_SIZE;
    }

    public static BigDecimal conventKbToMb(BigDecimal kb){
        if(null == kb){
            return new BigDecimal(0);
        }
        return kb.divide(CONVENT_KB_TO_MB,2 ,BigDecimal.ROUND_UP);
    }

    /**
     * 检查文件类型是否符合要求
     * @param fileSuffix 文件后缀
     * @return false -> 不满足
     */
    public static boolean checkFileType(String fileSuffix) {
        return SupportFileFormat.contain(fileSuffix);
    }

    /**
     * 获取文件后缀
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0] + SEPARATOR, "." + filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }





    //public static void main(String[] args) {
    //    String result = "{" + "msg" + ":" + "请求成" + "," + "code" + ":" + "100" + "," + "data" + ":" + "{" + "tid" + ":" + "44090965" + "}" + "}";
    //    BaseResult baseResult = new BaseResult();
    //    baseResult.setCode(100);
    //    baseResult.setMsg("请求成功");
    //    Map<String, Integer> tidMap = new HashMap<>();
    //    tidMap.put("tid", 44090965);
    //    baseResult.setData(tidMap);
    //    System.out.println(JSON.toJSONString(baseResult));
    //    String s = JSON.toJSONString(baseResult);
    //
    //    BaseResult baseResult1 = JSON.parseObject(s, BaseResult.class);
    //    System.out.println(
    //
    //            baseResult1
    //    );
    //}


}
