
package com.c.cclientparent.common.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author Nineteen
 * @since 1.0.0
 */
public class ServiceException extends RuntimeException {

    /**
     * 错误编码
     */
    @Getter
    private final String errorCode;

    /**
     * Http状态码
     */
    @Getter
    private final int status;


    public ServiceException(String message) {
        super(message);
        this.errorCode = "500";
        this.status = 200;
    }


}

