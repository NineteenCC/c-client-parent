package com.c.cclientparent.oss.utils;

import com.alibaba.cloud.commons.lang.StringUtils;

/**
 * oss工具类
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/9 20:13]
 */
public class OssUtil {

    private OssUtil(){}

    private final static String OSS_ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    private final static String OSS_BUCKET = "translate-core";

    public static String joinPreviewPath(String path){
        if(StringUtils.isBlank(path)){
            return "";
        }
        return OSS_ENDPOINT + "/" + OSS_BUCKET + "/" + path;
    }
}
