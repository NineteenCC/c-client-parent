package com.c.cclientparent.translate.controller;

import com.alibaba.fastjson.JSON;
import com.c.cclientparent.common.result.ResponseBean;
import com.c.cclientparent.common.util.FileUtils;
import com.c.cclientparent.fanyigou.requestpojo.vo.DetectPageVo;
import com.c.cclientparent.fanyigou.requestpojo.vo.QueryProgressVo;
import com.c.cclientparent.fanyigou.utils.FanYiGouHelper;
import com.c.cclientparent.oss.helper.OssHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;


/**
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 1:28]
 */
@RestController
@CrossOrigin
@RequestMapping
public class TestController {


    @Resource
    OssHelper ossHelper;

    @Resource
    FanYiGouHelper fanYiGouHelper;


    @GetMapping("test")
    public ResponseBean<String> testTemp(){
        String filePath = "2023-01-05/56da0001883f43a3bd06aa51e01ea0c2testPDF.pdf";
        InputStream fileInputStream = ossHelper.getFileInputStream(filePath);
        File file = new File("C:\\Users\\Chen\\Desktop\\1-221223162A0.docx");
        try {
            byte[] bytes = fileInputStream.readAllBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseBean.success();
    }

    @GetMapping("test1")
    public ResponseBean<QueryProgressVo> testTemp1(@RequestParam("file") MultipartFile file){
        DetectPageVo detectPageVo = this.fanYiGouHelper.checkPageNumber(file);
        QueryProgressVo queryProgressVo = this.fanYiGouHelper.queryProgress("44090965");
        System.out.println(JSON.toJSONString(queryProgressVo));
        return ResponseBean.success(queryProgressVo);
    }



}
