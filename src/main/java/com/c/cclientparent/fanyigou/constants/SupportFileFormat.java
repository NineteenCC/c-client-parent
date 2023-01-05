package com.c.cclientparent.fanyigou.constants;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 翻译狗支持的文件格式
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:22]
 */
public class SupportFileFormat {

    private SupportFileFormat(){}

    /**
     * 支持的文件格式
     */
     private static final Set<String> SUPPORT_FILE_SET = Sets.newHashSet(
             "pdf",
             "doc",
             "docx",
             "dot",
             "dotx",
             "dotm",
             "docm",
             "xml",
             "odt",
             "wps",
             "wpt",
             "xls",
             "xlsx",
             "et",
             "ett",
             "ppt",
             "pptx",
             "dps",
             "dpt",
             "txt",
             "rtf"
     );

    /**
     * 支持的文件格式提示语
     */
    private static final String MARKED_WORDS = "pdf、Microsoft Office（doc、docx、dot、dotx、dotm、docm、xml、odt）、WPS Office（wps、wpt）、xls、xlsx、et、ett、ppt、pptx、dps、dpt、txt、rtf";

    public static Set<String> getSupportFileFormat(){
         return SUPPORT_FILE_SET;
     }

    public static String getMarkedWords(){
         return MARKED_WORDS;
    }

    /**
     * @param suffix 文件后缀
     * @return false -> 不包含
     */
    public static boolean contain(String suffix){
        return SUPPORT_FILE_SET.contains(suffix);
    }


}
