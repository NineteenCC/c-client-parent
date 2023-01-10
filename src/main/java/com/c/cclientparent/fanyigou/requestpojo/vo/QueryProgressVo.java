package com.c.cclientparent.fanyigou.requestpojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 翻译狗查询翻译进度响应结果VO
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/9 18:32]
 */
@Data
public class QueryProgressVo {

    private String tid;

    /**
     * 文件类型
     */
    private String docType;

    /**
     * 源语言
     */
    private String fromLan;

    /**
     * 目标原因
     */
    private String toLan;


    private String msg;

    private String originalCharCount;

    /**
     * 总页数
     */
    private String pageCount;

    private String percent;

    private String retry;

    private int status;

    private String title;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
