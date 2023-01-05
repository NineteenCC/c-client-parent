package com.c.cclientparent.common.exception;

import com.c.cclientparent.common.result.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常统一处理
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/20 21:12]
 */
@RestControllerAdvice
@Slf4j
public class ConsumerGlobalExceptionHandler {

    /**
     * 数据异常返回
     *
     * @param serviceException 业务异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseBean<String> serviceException(ServiceException serviceException) {
        log.error(serviceException.getErrorCode() + "-" + serviceException.getMessage(), serviceException);
        return ResponseBean.failed(serviceException.getMessage());
    }





}
