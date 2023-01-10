package com.c.cclientparent.translate.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/5 11:33]
 */
@Data
public class TranslateInfoVo {

    /**
     * 翻译详情主键ID
     */
    private String translateInfoId;

    /**
     * 源文件名
     */
    private String sourceFileName;

    /**
     * 源文件OSS访问路径
     */
    private String sourceFileOssPath;


    /**
     * 源文件OSS预览全路径
     */
    private String sourceFileOssPreviewPath;

    /**
     * 源文件页数
     */
    private Integer sourceFilePageNumber;

    /**
     * 源文件大小
     */
    private BigDecimal sourceFileSize;

    /**
     * 上传翻译狗后的文件下载ID
     */
    private String tid;

    /**
     * 翻译后文件名称
     */
    private String targetFileName;

    /**
     * 翻译后文件OSS访问路径
     */
    private String targetFileOssPath;

    /**
     * 翻译后文件OSS预览全路径
     */
    private String targetFileOssPreviewPath;

    /**
     * 翻译时间
     */
    private LocalDateTime translateTime;

    /**
     * 翻译状态(1.待翻译, 2. 已翻译. 3.其他)
     */
    private String translateState;

}
