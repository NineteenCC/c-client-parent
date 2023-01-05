package com.c.cclientparent.oss.constants;

/**
 * OSS使用常量
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/3 9:48]
 */
public class OssConstant {

    public static final String OSS_ACCESS_PREFIX = "https://translate-core.oss-cn-hangzhou.aliyuncs.com/";

    public static final String HTTP_HEADER_TYPE = "https";

    public static final String CONTENT_BMP = "bmp";

    public static final String CONTENT_PDF = "pdf";

    public static final String CONTENT_XLS="xls";

    public static final String CONTENT_XLSX="xlsx";

    public static final String CONTENT_GIF = "gif";

    public static final String CONTENT_JPEG = "jpeg";

    public static final String CONTENT_JPG = "jpg";

    public static final String CONTENT_PNG = "png";

    public static final String CONTENT_HTML = "html";

    public static final String CONTENT_TXT = "txt";

    public static final String CONTENT_VSD = "vsd";

    public static final String CONTENT_PPTX = "pptx";

    public static final String CONTENT_PPT = "ppt";

    public static final String CONTENT_DOCX = "docx";

    public static final String CONTENT_DOC = "doc";

    public static final String CONTENT_XML = "xml";

    public static final String METADATA_IMAGE_BMP = "image/bmp";

    public static final String METADATA_PDF = "application/pdf";

    public static final String APPLICATION_OCTET = "application/octet-stream";

    public static final String APPLICATION_APK = "apk";

    public static final String METADATA_XLS="application/vnd.ms-exce";

    public static final String METADATA_XLSX="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String METADATA_IMAGE_JIF = "image/gif";

    public static final String METADATA_IMAGE_JPG = "image/jpg";

    public static final String METADATA_TEXT_HTML = "text/html";

    public static final String METADATA_TEXT_PLAIN = "text/plain";

    public static final String METADATA_APPLICATION_VND = "application/vnd.visio";

    public static final String METADATA_APPLICATION_VND_MS = "application/vnd.ms-powerpoint";

    public static final String METADATA_APPLICATION_MSWORD = "application/msword";

    public static final String METADATA_TEXT_XML = "text/xml";

    public static final String USER_AGENT = "User-Agent";

    public static final String DATE_TIME = "yyyy-MM-dd";

    public static final int BYTE_LENGTH = 8192;

    public static final String CONTAINS_MSIE = "MSIE";

    public static final String ENCODE_UTF = "UTF-8";

    public static final String CONTAINS_MOZILLA = "Mozilla";

    public static final String CONTENT_DISPOSITION_HEADER = "content-disposition";

    public static final String CONTENT_ATTACHMENT = "attachment;filename=";

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    public static final String UPLOADED_SUCCESSFULLY = "上传成功";

    public static final String UPLOADED_FAILED = "文件格式不匹配不允许上传";


    /**
     * 音频文件上传限制最大为200M 转换 209715200 个字节;
     */
    public static final long AUDIO_FILE_SIZE_MAX = 209715200;

    /**
     * 视频文件上传限制最大为200M 转换 209715200 个字节;
     */
    public static final long VIDEO_FILE_SIZE_MAX = 209715200;

    private OssConstant() {
    }









}
