package com.c.cclientparent.translate.service;

import com.c.cclientparent.translate.pojo.TranslateInfo;
import com.c.cclientparent.translate.vo.SupportLanguageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文档翻译核心service
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/30 22:21]
 */
public interface TranslateCoreService {



    /**
     * 上传并翻译文件
     * @param multipartFile 原文件
     * @param sourceCode 源语言
     * @param targetCode 目标语言
     */
    void uploadFileAndTranslated(MultipartFile multipartFile, String sourceCode, String targetCode);


    /**
     * 下载文件
     * @param tid 翻译狗文件下载ID
     */
    void downloadFile(String tid);

    /**
     * 仅上传文件
     * @param multipartFile 要上传的文件
     */
    void uploadFile(MultipartFile multipartFile);


    /**
     * 获取翻译支持语言
     * @return 支持语言列表
     */
    List<String> getSupportLanguage();

    /**
     * 获取当前登录用户上传的文件列表
     * @return 文件列表
     */
    List<TranslateInfo> getFileList();

    /**
     * 开始翻译
     * @param translateInfoId 翻译文件详情主键
     * @param sourceLanguage 源语言名称
     * @param targetLanguage 目标语言名称
     */
    void startTranslate(String translateInfoId, String sourceLanguage, String targetLanguage);
}
