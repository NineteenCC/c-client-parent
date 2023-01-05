package com.c.cclientparent.oss.helper;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.c.cclientparent.common.exception.ServiceException;
import com.c.cclientparent.fanyigou.requestpojo.result.BaseResult;
import com.c.cclientparent.oss.config.OssProperties;
import com.c.cclientparent.oss.constants.OssConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 *
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 19:30]
 */
@Component
@Slf4j
public class OssHelper {

    private final OSS oss;
    private final OssProperties ossProperties;


    public OssHelper(OSS oss, OssProperties ossProperties) {
        this.oss = oss;
        this.ossProperties = ossProperties;
    }


    public String uploadFile(MultipartFile multipartFile){
        Assert.notNull(multipartFile, "oss upload file is null");
        PutObjectRequest putObjectRequest;
        String fileSavePath;
        Long size = multipartFile.getSize();
        try {
            ObjectMetadata objectMetadata = this.objectContent(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            fileSavePath = this.getPutDirectory(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), fileSavePath, multipartFile.getInputStream(), objectMetadata);
            oss.putObject(putObjectRequest);
        } catch (IOException e) {
            log.error("oss error", e);
            throw new ServiceException("oss upload failed");
        }
        log.info("oss upload success, save path: {} , file size : {}", fileSavePath, size);
        return fileSavePath;
    }

    public void getUploadPath(){

    }


    /**
     * 根据上传的文件判断放到哪个文件夹
     *
     * @param fileName 文件名字
     * @return 返回文件夹名称
     */
    public String getPutDirectory(String fileName) {
        String uuid =  UUID.randomUUID().toString().replace("-", "");
        String uuidName = uuid + fileName;
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern(OssConstant.DATE_TIME));
        return datePath + "/" + uuidName;
    }


    /**
     * 修改objectMeta_Date类型信息
     *
     * @param fileName 图片名字
     * @return objectMeta_Date实例  已修改
     */
    public ObjectMetadata objectContent(String fileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding(OssConstant.ENCODE_UTF);
        objectMetadata.setHeader("fileName", fileName);
        objectMetadata.setContentType(determineType(fileName.substring(fileName.lastIndexOf('.') + 1)));
        return objectMetadata;
    }

    /**
     * 判断上传文件的后缀
     *
     * @param fileName 文件后缀名
     * @return 返回ContentType的类型
     */
    public String determineType(String fileName) {
        if (OssConstant.CONTENT_PDF.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_PDF;
        }
        if (OssConstant.APPLICATION_APK.equalsIgnoreCase(fileName)){
            return OssConstant.APPLICATION_OCTET;
        }
        if (OssConstant.CONTENT_XLS.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_XLS;
        }
        if (OssConstant.CONTENT_XLSX.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_XLSX;
        }
        if (OssConstant.CONTENT_BMP.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_IMAGE_BMP;
        }
        if (OssConstant.CONTENT_GIF.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_IMAGE_JIF;
        }
        if (OssConstant.CONTENT_JPEG.equalsIgnoreCase(fileName) ||
                OssConstant.CONTENT_JPG.equalsIgnoreCase(fileName) ||
                OssConstant.CONTENT_PNG.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_IMAGE_JPG;
        }
        if (OssConstant.CONTENT_HTML.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_TEXT_HTML;
        }
        if (OssConstant.CONTENT_TXT.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_TEXT_PLAIN;
        }
        if (OssConstant.CONTENT_VSD.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_APPLICATION_VND;
        }
        if (OssConstant.CONTENT_PPTX.equalsIgnoreCase(fileName) ||
                OssConstant.CONTENT_PPT.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_APPLICATION_VND_MS;
        }
        if (OssConstant.CONTENT_DOCX.equalsIgnoreCase(fileName) ||
                OssConstant.CONTENT_DOC.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_APPLICATION_MSWORD;
        }
        if (OssConstant.CONTENT_XML.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_TEXT_XML;
        }
        return OssConstant.METADATA_IMAGE_JPG;
    }

}
