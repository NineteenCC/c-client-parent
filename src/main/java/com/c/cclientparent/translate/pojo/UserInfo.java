package com.c.cclientparent.translate.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * TODO
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/4 12:04]
 */

/**
 * 用户详情表
 */
@Data
@TableName(value = "user_info")
public class UserInfo {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 登录用户名(手机号)
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 是否删除用户
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;

    /**
     * 创建用户
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 更新用户
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 权限设置(1. 管理员, 2. 普通用户)
     */
    @TableField(value = "auth_setting")
    private Integer authSetting;
}