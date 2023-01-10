package com.c.cclientparent.translate.controller;

import com.c.cclientparent.common.result.ResponseBean;
import com.c.cclientparent.translate.service.TranslateCoreService;
import com.c.cclientparent.translate.vo.TranslateInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 翻译功能核心控制层
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 20:21]
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/translate")
public class TranslateCoreController {

    private final TranslateCoreService translateCoreService;

    public TranslateCoreController(TranslateCoreService translateCoreService) {
        this.translateCoreService = translateCoreService;
    }

    @PostMapping("uploadFileAndTranslated")
    public ResponseBean<String> uploadFileAndTranslated(
            @RequestParam("multipartFile")MultipartFile multipartFile,
            @RequestParam("sourceCode")String sourceCode,
            @RequestParam("targetCode")String targetCode
    ){
        this.translateCoreService.uploadFileAndTranslated(multipartFile, sourceCode, targetCode);
        return ResponseBean.success("请求成功");
    }


    @PostMapping("downloadFile")
    public ResponseBean<String> downloadFile(@RequestParam("tid") String tid){
        this.translateCoreService.downloadFile(tid);
        return ResponseBean.success("请求成功");
    }


    @PostMapping("/uploadFile")
    public ResponseBean<String> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile){
        this.translateCoreService.uploadFile(multipartFile);
        return ResponseBean.success("请求成功");
    }

    @GetMapping("/getSupportLanguage")
    public ResponseBean<List<String>> getSupportLanguage(){
        List<String> supportLanguageVoList = this.translateCoreService.getSupportLanguage();
        return ResponseBean.success(supportLanguageVoList);
    }


    @GetMapping("/startTranslate")
    public ResponseBean<String> startTranslate(
            @RequestParam("sourceLanguage") @NotBlank(message = "源文件名称不能为空")String sourceLanguage,
            @RequestParam("targetLanguage") @NotBlank(message = "目标文件ID不能为空") String targetLanguage,
            @RequestParam("translateInfoId") @NotBlank(message = "翻译文件ID不能为空") String translateInfoId){
        log.info("开始翻译文件");
        log.info("sourceLanguage:{}", sourceLanguage);
        log.info("targetLanguage:{}", targetLanguage);
        log.info("translateInfoId:{}", translateInfoId);
        this.translateCoreService.startTranslate(translateInfoId, sourceLanguage, targetLanguage);
        return ResponseBean.success("请求成功");
    }

    @GetMapping("/getFileList")
    public ResponseBean<List<TranslateInfoVo>> getFileList(){
        return ResponseBean.success(this.translateCoreService.getFileList());
    }

}
