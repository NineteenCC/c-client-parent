package com.c.cclientparent.fanyigou.requestpojo.result;


import com.c.cclientparent.common.util.ServiceAssert;
import lombok.Data;

/**
 * 响应结果父类
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/27 19:41]
 */
@Data
public class BaseResult<T> {

    /**
     * 状态码
     */
    private int code;
    /**
     * 返回状态说明
     */
    private String msg;
    /**
     * Json响应结果
     */
    private T data;

    public BaseResult(){
        this.code = 500;
        this.msg = "无效输入";
    }

    private BaseResult(int code, String message){
        this.code = code;
        this.msg = message;
    }


    private BaseResult(int code, String message, T data){
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static <T> BaseResult<T> failed(String message){
        return new BaseResult<>(500, message);
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(100, "请求成功", data);
    }

    public static <T> BaseResult<T> success() {
        return new BaseResult<>(100, "请求成功");
    }

    public T getData(){
        ServiceAssert.isFalse(this.code != 100, "请求失败, 无法获取响应结果, 错误消息 : " + this.msg);
        return data;
    }
}
