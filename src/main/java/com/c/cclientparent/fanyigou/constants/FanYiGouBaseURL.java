package com.c.cclientparent.fanyigou.constants;

/**
 * 翻译狗
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:17]
 */
public class FanYiGouBaseURL {

    private FanYiGouBaseURL() {
    }

    /**
     * APPID
     */
    public static final String APPID = "1672147768795";
    /**
     * SECRET
     */
    public static final String SECRET = "14bf4dba099d8bef7d0beee887e117c96925ed8c";



    /**
     * 翻译狗host
     */
    public static final String HOST = "https://www.fanyigou.com";

    /**
     * 文件上传
     */
    public static final String UPLOAD_FILE = "/TranslateApi/api/uploadTranslate";


    /**
     * 文件下载
     */
    public static final String DOWNLOAD_FILE  = "/TranslateApi/api/downloadFile";

    /**
     * 上传文件检测页数
     */
    public final static String DETECT_PAGE_PATH = "/TranslateApi/api/detectDocPage";


    /**
     * 查询翻译详情
     */
    public final static String GO_QUERY_PATH = "/TranslateApi/api/queryTransProgress";


    //提交检测页数翻译
    //public final static String TRANSLATE_SUBMIT_DETECT_PAGE_PATH = "/TranslateApi/api/submitForDetectDoc";
    //转换文档
    //public final static String TRANSLATE_CONVERT_DOC_PATH = "/TranslateApi/api/convert";

}
