package com.c.cclientparent.auth.util;

import com.c.cclientparent.translate.pojo.UserInfo;

/**
 * @author Nineteen
 * @since 1.0.0 [2023/1/3 11:57]
 */
public class SecurityUtil {



    public static String getUserId(){
        return "1672654085130";
    }

    public static UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1672654085130");
        userInfo.setUsername("admin");
        userInfo.setPassword("admin");
        userInfo.setNickname("超级管理员");
        userInfo.setAuthSetting(1);
        return userInfo;
    }

}
