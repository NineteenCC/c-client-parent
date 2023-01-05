package com.c.cclientparent.common.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 公共返回类
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 20:23]
 */
@Data
public class ResponseBean<T> {

    private int code;
    private String message;
    private T data;

    private ResponseBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResponseBean(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static <T> ResponseBean<T> success(){
        return new ResponseBean<>(200,"请求成功");
    }

    public static <T> ResponseBean<T>  success(T data){
        return new ResponseBean<>(200, "请求成功", data);
    }

    public static <T> ResponseBean<T> failed(){
        return new ResponseBean<>(500,"请求失败");
    }

    public static <T> ResponseBean<T> failed(T data){
        return new ResponseBean<>(500,"请求失败", data);
    }

    public static <T> ResponseBean<T> failed(String message){
        return new ResponseBean<>(500,message);
    }
}
