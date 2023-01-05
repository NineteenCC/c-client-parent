package com.c.cclientparent.fanyigou.requestpojo.dto;

import lombok.Data;

/**
 * 文件上传并翻译Dto
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:35]
 */
@Data
public class UploadTranslateDto {

    /**
     * 应用的唯一识别标记(必填)
     */
    private String appid;
    /**
     * 签名(必填)
     */
    private String token;
    /**
     * 随机字符串(必填)
     */
    private String nonce_str;
    /**
     * 源语言-见语言列表(必填)
     */
    private String from;
    /**
     * 目标语言（不支持自动检测）-见语言列表(必填)
     */
    private String to;
    /**
     * 源文件md5 - 小写(必填)
     */
    private String md5;
    /**
     * 行业代码 - 见行业列表
     */
    private Integer industryId;
    /**
     * 文档内图片翻译 (0：不翻译文档内图片（默认），1：翻译文档内图片。目前支持中、英、日、韩的文档内图片翻译。（如有需要请联系销售开通）)
     */
    private Integer transImg;
    /**
     * 指定excel翻译模式(0：只翻译当前打开sheet（默认），1：翻译全部sheet（页数按全部sheet字符数来计算）)
     */
    private Integer excelMode;
    /**
     * 指定翻译模式(0：译文单独为一个文档（默认），1：双语对照（原文和译文在一个文档）)
     */
    private Integer bilingualControl;



}
