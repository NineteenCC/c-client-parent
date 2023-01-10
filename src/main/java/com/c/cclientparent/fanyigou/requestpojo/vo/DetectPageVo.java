package com.c.cclientparent.fanyigou.requestpojo.vo;

import lombok.Data;

/**
 * 翻译狗检查文件页数结果VO
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/9 18:35]
 */
@Data
public class DetectPageVo {


    /**
     * 翻译ID
     */
    private String tid;

    /**
     * 文件页数
     */
    private Integer page;

}
