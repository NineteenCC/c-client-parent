package com.c.cclientparent.translate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.c.cclientparent.auth.util.SecurityUtil;
import com.c.cclientparent.common.util.FileUtils;
import com.c.cclientparent.common.util.ServiceAssert;
import com.c.cclientparent.common.util.SnowFlakeUtil;
import com.c.cclientparent.fanyigou.constants.SupportLanguage;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
import com.c.cclientparent.fanyigou.requestpojo.vo.DetectPageVo;
import com.c.cclientparent.fanyigou.utils.FanYiGouHelper;
import com.c.cclientparent.oss.helper.OssHelper;
import com.c.cclientparent.oss.utils.OssUtil;
import com.c.cclientparent.translate.enums.TranslateState;
import com.c.cclientparent.translate.mapper.TranslateInfoMapper;
import com.c.cclientparent.translate.pojo.TranslateInfo;
import com.c.cclientparent.translate.service.TranslateCoreService;
import com.c.cclientparent.translate.vo.SupportLanguageVo;
import com.c.cclientparent.translate.vo.TranslateInfoVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

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
        DetectPageVo detectPageVo = this.fanYiGouHelper.checkPageNumber(multipartFile);
        ServiceAssert.isTrue(detectPageVo.getPage() <= 5, "file page max : 5");
        String filePath = this.ossHelper.uploadFile(multipartFile);
        TranslateInfo translateInfo = new TranslateInfo();
        translateInfo.setTranslateInfoId(SnowFlakeUtil.getSnowFlakeId());
        translateInfo.setSourceFileName(multipartFile.getOriginalFilename());
        translateInfo.setSourceFileOssPath(filePath);
        translateInfo.setSourceFileSize(multipartFile.getSize());
        translateInfo.setSourceFilePageNumber(detectPageVo.getPage());
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
    public List<TranslateInfoVo> getFileList() {
        List<TranslateInfoVo> translateInfoVoList = new ArrayList<>();
        List<TranslateInfo> fileList = this.translateInfoMapper.findFileListByUserId(SecurityUtil.getUserId());
        if(CollectionUtils.isEmpty(fileList)){
            return translateInfoVoList;
        }
        for (TranslateInfo translateInfo : fileList) {
            TranslateInfoVo translateInfoVo = new TranslateInfoVo();
            BeanUtil.copyProperties(translateInfo, translateInfoVo);
            translateInfoVo.setTranslateState(TranslateState.getValueByKey(translateInfo.getTranslateState()));
            translateInfoVo.setSourceFileSize(FileUtils.conventKbToMb(translateInfoVo.getSourceFileSize()));
            translateInfoVo.setSourceFileOssPreviewPath(OssUtil.joinPreviewPath(translateInfoVo.getSourceFileOssPath()));
            translateInfoVo.setTargetFileOssPreviewPath(OssUtil.joinPreviewPath(translateInfoVo.getTargetFileOssPath()));
            translateInfoVoList.add(translateInfoVo);
        }
        return translateInfoVoList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startTranslate(String translateInfoId, String sourceLanguage, String targetLanguage) {
        ServiceAssert.notBlank(translateInfoId, "翻译文件ID不能为空");
        //sourceLanguage = SupportLanguage.getLanguageCode(sourceLanguage);
        //targetLanguage = SupportLanguage.getLanguageCode(targetLanguage);
        //ServiceAssert.notBlank(sourceLanguage, "源语言格式不正确");
        //ServiceAssert.notBlank(targetLanguage, "目标语言格式不正确");
        //TranslateInfo translateInfo = this.translateInfoMapper.selectById(translateInfoId);
        //ServiceAssert.notNull(translateInfo, "选择的翻译文件不存在");
        //
        //InputStream fileInputStream = this.ossHelper.getFileInputStream(translateInfo.getSourceFileOssPath());
        //MultipartFile multipartFile = this.ossHelper.getMultipartFile(fileInputStream, translateInfo.getSourceFileName());
        //System.out.println("要翻译的文件名称:"+multipartFile.getOriginalFilename());
        //System.out.println("要翻译的文件名称:"+multipartFile.getName());
        //BaseResult baseResult = this.fanYiGouHelper.uploadFileAndTranslate(multipartFile, sourceLanguage, targetLanguage);
        //Map<String, String> resultMap = (Map<String, String>) baseResult.getData();
        //String tid = resultMap.get("tid");
        ////String tid = "44203533";
        //translateInfo.setTid(tid);
        //translateInfo.setTranslateTime(LocalDateTime.now());
        //InputStream download = this.fanYiGouHelper.download(tid, "2");
        //String filePath = this.ossHelper.uploadFile(download, translateInfo.getSourceFileName());
        //translateInfo.setTargetFileOssPath(filePath);
        //translateInfo.setTargetFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
        //translateInfo.setTranslateState(TranslateState.IS_TRANSLATED.getKey());
        //this.translateInfoMapper.updateById(translateInfo);
        log.info("startTranslate success!!!!!!!");
    }
}
