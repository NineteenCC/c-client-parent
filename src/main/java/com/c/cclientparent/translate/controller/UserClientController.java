/*
 * Copyright (c) 2021-2022. TGZL-CHINA Inc. All Right Reserved.
 */
package com.c.cclientparent.translate.controller;

import com.c.cclientparent.common.util.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 用户客户端控制层
 * @author C
 * @since 1.0.0 [2022/12/12 19:03]
 */
@RestController
@RequestMapping("api/user/client")
public class UserClientController {


    @GetMapping("/getVideoList")
    public void uploadVideo(@RequestParam("file") MultipartFile file){
        File file1 = FileUtils.transferToFile(file);
        System.out.println(file1);
    }





}
