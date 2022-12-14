package com.c.cclientparent.oss.helper;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.c.cclientparent.common.exception.ServiceException;
import com.c.cclientparent.common.util.ServiceAssert;
import com.c.cclientparent.oss.config.OssProperties;
import com.c.cclientparent.oss.constants.OssConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
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

    public String uploadFile(InputStream inputStream, String fileName) {
        ServiceAssert.notNull(inputStream, "oss upload file is null");
        PutObjectRequest putObjectRequest;
        String fileSavePath;
        ObjectMetadata objectMetadata = this.objectContent(Objects.requireNonNull(fileName));
        fileSavePath = this.getPutDirectory(Objects.requireNonNull(fileName));
        putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), fileSavePath, inputStream, objectMetadata);
        oss.putObject(putObjectRequest);
        log.info("oss upload success, save path: {}", fileSavePath);
        return fileSavePath;
    }



    public String uploadFile(MultipartFile multipartFile) {
        try {
            return uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        } catch (IOException e) {
            log.error("oss upload failed : {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public InputStream getFileInputStream(String filePath) {
        // ??????OSSClient?????????
        OSSObject ossObject = oss.getObject(ossProperties.getBucketName(), filePath);
        InputStream out = ossObject.getObjectContent();
        byte[] bytes = toByteArray(out);
        return new ByteArrayInputStream(bytes);
    }



    /**
     * InputStream??????byte??????
     *
     * @param input
     * @return
     * @throws IOException
     */
    public byte[] toByteArray(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[input.available()];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException exception) {
            log.error("oss download inputStream failed : {}", exception.getMessage());
            throw new ServiceException("oss download inputStream failed");
        }
        return output.toByteArray();
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param fileName ????????????
     * @return ?????????????????????
     */
    public String getPutDirectory(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String uuidName = uuid + "-" +fileName;
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern(OssConstant.DATE_TIME));
        return datePath + "/" + uuidName;
    }


    /**
     * ??????objectMeta_Date????????????
     *
     * @param fileName ????????????
     * @return objectMeta_Date??????  ?????????
     */
    public ObjectMetadata objectContent(String fileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding(OssConstant.ENCODE_UTF);
        objectMetadata.setHeader("fileName", fileName);
        objectMetadata.setContentType(determineType(fileName.substring(fileName.lastIndexOf('.') + 1)));
        return objectMetadata;
    }

    /**
     * ???????????????MultipartFile
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return MultipartFile
     */
    public MultipartFile getMultipartFile(InputStream inputStream, String fileName) {
        FileItem fileItem = createFileItem(inputStream, fileName);
        //CommonsMultipartFile???feign???multipartFile?????????????????????FileItem?????????
        return new CommonsMultipartFile(fileItem);
    }


    /**
     * FileItem???????????????
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return FileItem
     */
    private FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        FileItem item = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[10 * 1024 * 1024];
        OutputStream os = null;
        //???????????????????????????????????????
        try {
            os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error("Stream copy exception", e);
            throw new IllegalArgumentException("??????????????????");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("Stream close exception", e);

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("Stream close exception", e);
                }
            }
        }

        return item;
    }


    /**
     * ???????????????????????????
     *
     * @param fileName ???????????????
     * @return ??????ContentType?????????
     */
    public String determineType(String fileName) {
        if (OssConstant.CONTENT_PDF.equalsIgnoreCase(fileName)) {
            return OssConstant.METADATA_PDF;
        }
        if (OssConstant.APPLICATION_APK.equalsIgnoreCase(fileName)) {
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
