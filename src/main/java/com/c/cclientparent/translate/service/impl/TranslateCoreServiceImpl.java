package com.c.cclientparent.translate.service.impl;

import com.c.cclientparent.auth.util.SecurityUtil;
import com.c.cclientparent.common.util.FileUtils;
import com.c.cclientparent.common.util.ServiceAssert;
import com.c.cclientparent.common.util.SnowFlakeUtil;
import com.c.cclientparent.fanyigou.constants.SupportLanguage;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
import com.c.cclientparent.fanyigou.utils.FanYiGouHelper;
import com.c.cclientparent.oss.helper.OssHelper;
import com.c.cclientparent.translate.mapper.TranslateInfoMapper;
import com.c.cclientparent.translate.pojo.TranslateInfo;
import com.c.cclientparent.translate.service.TranslateCoreService;
import com.c.cclientparent.translate.vo.SupportLanguageVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * 文档翻译核心service
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/30 22:23]
 */
@Service
@Slf4j
public class TranslateCoreServiceImpl implements TranslateCoreService {

    private final FanYiGouHelper fanYiGouHelper;
    private final OssHelper ossHelper;
    private final TranslateInfoMapper translateInfoMapper;

    public TranslateCoreServiceImpl(FanYiGouHelper fanYiGouHelper,
                                    OssHelper ossHelper,
                                    TranslateInfoMapper translateInfoMapper) {
        this.fanYiGouHelper = fanYiGouHelper;
        this.ossHelper = ossHelper;
        this.translateInfoMapper = translateInfoMapper;
    }

    @Override
    public void uploadFileAndTranslated(MultipartFile multipartFile, String sourceCode, String targetCode) {
        BaseResult baseResult = this.fanYiGouHelper.uploadFileAndTranslate(multipartFile, sourceCode, targetCode);
        //TODO 上传文件到OSS
        //保存数据到数据库

    }

    @Override
    public void downloadFile(String tid) {



    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFile(MultipartFile multipartFile) {
        ServiceAssert.notNull(multipartFile, "upload file is null");
        ServiceAssert.isTrue(FileUtils.checkFileType(FileUtils.getFileSuffix(multipartFile.getOriginalFilename())), "file type not support");
        ServiceAssert.isTrue(FileUtils.checkFileSize(multipartFile.getSize()),"file size is large");
        String filePath = this.ossHelper.uploadFile(multipartFile);
        TranslateInfo translateInfo = new TranslateInfo();
        translateInfo.setTranslateInfoId(SnowFlakeUtil.getSnowFlakeId());
        translateInfo.setSourceFileName(multipartFile.getOriginalFilename());
        translateInfo.setSourceFileOssPath(filePath);
        translateInfo.setSourceFileSize(multipartFile.getSize());
        translateInfo.setSourceFilePageNumber(1);
        translateInfo.setCreateUser(SecurityUtil.getUserId());
        translateInfo.setUpdateUser(SecurityUtil.getUserId());
        this.translateInfoMapper.insert(translateInfo);
    }

    @Override
    public List<String> getSupportLanguage() {
        Set<String> supportLanguage = SupportLanguage.getSupportLanguage();
        return Lists.newArrayList(supportLanguage);
    }


    @Override
    public List<TranslateInfo> getFileList() {
        return this.translateInfoMapper.findFileListByUserId(SecurityUtil.getUserId());
    }

    @Override
    public void startTranslate(String translateInfoId, String sourceLanguage, String targetLanguage) {
        ServiceAssert.notBlank(SupportLanguage.getLanguageCode(sourceLanguage), "翻译文件ID不能为空");
    }
}
