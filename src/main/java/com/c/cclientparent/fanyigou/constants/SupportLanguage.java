package com.c.cclientparent.fanyigou.constants;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 支持语言
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 20:09]
 */
public class SupportLanguage {

    private static final Map<String, String> SUPPORT_LANGUAGE = new HashMap<>();

    static {
        SUPPORT_LANGUAGE.put("自动检测","auto");
        SUPPORT_LANGUAGE.put("中文","zh");
        SUPPORT_LANGUAGE.put("中文繁体","cht");
        SUPPORT_LANGUAGE.put("中文粤语(繁体)","yue");
        SUPPORT_LANGUAGE.put("英语","en");
        SUPPORT_LANGUAGE.put("西班牙语","spa");
        SUPPORT_LANGUAGE.put("乌克兰语","uk");
        SUPPORT_LANGUAGE.put("土耳其语","tr");
        SUPPORT_LANGUAGE.put("日语","jp");
        SUPPORT_LANGUAGE.put("德语","de");
        SUPPORT_LANGUAGE.put("俄语","ru");
        SUPPORT_LANGUAGE.put("法语","fra");
    }

    public static Set<String> getSupportLanguage(){
        return Sets.newHashSet(SUPPORT_LANGUAGE.keySet());
    }

    /**
     * 根据语言中文名获取语言编码
     * @param cn 中文名称
     * @return 编码
     */
    public static String getLanguageCode(String cn){
        return SUPPORT_LANGUAGE.get(cn);
    }
}
