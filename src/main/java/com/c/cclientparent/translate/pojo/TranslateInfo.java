package com.c.cclientparent.translate.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文档翻译信息详情表
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/4 19:57]
 */

@Data
@TableName(value = "translate_info")
public class TranslateInfo {
    /**
     * 翻译详情主键ID
     */
    @TableId(value = "translate_info_id", type = IdType.INPUT)
    private String translateInfoId;

    /**
     * 源文件名
     */
    @TableField(value = "source_file_name")
    private String sourceFileName;

    /**
     * 源文件OSS访问路径
     */
    @TableField(value = "source_file_oss_path")
    private String sourceFileOssPath;

    /**
     * 源文件页数
     */
    @TableField(value = "source_file_page_number")
    private Integer sourceFilePageNumber;

    /**
     * 源文件大小
     */
    @TableField(value = "source_file_size")
    private Long sourceFileSize;

    /**
     * 上传翻译狗后的文件下载ID
     */
    @TableField(value = "tid")
    private String tid;

    /**
     * 翻译后文件名称
     */
    @TableField(value = "target_file_name")
    private String targetFileName;

    /**
     * 翻译后文件OSS访问路径
     */
    @TableField(value = "target_file_oss_path")
    private String targetFileOssPath;

    /**
     * 翻译时间
     */
    @TableField(value = "translate_time")
    private LocalDateTime translateTime;

    /**
     * 翻译状态(1.待翻译, 2. 已翻译. 3.其他)
     */
    @TableField(value = "translate_state")
    private Integer translateState;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新用户
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}