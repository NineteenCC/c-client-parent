package com.c.cclientparent.translate.controller;

import com.c.cclientparent.common.result.ResponseBean;
import com.c.cclientparent.oss.helper.OssHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Nineteen
 * @since 1.0.0 [2023/1/2 1:28]
 */
@RestController
@CrossOrigin
@RequestMapping
public class TestController {


    @Autowired
    OssHelper ossHelper;


    @GetMapping("test")
    public ResponseBean<String> testTemp(
            @RequestParam("temp") MultipartFile temp
    ){
        System.out.println("接收到的参数是:" + temp);
        //ossHelper.uploadFile(temp);
        return ResponseBean.failed();
    }

}
