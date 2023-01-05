package com.c.cclientparent.common.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.c.cclientparent.common.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * 断言工具类
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/22 21:58]
 */
public class ServiceAssert {

    private ServiceAssert() {
    }

    public static void isNotEmpty(Object[] array, String message){
        if(CollectionUtils.isEmpty(Arrays.asList(array))){
            throw new ServiceException(message);
        }
    }

    public static void isNotEmpty(Collection<?> collection, String message){
        if(CollectionUtils.isEmpty(collection)){
            throw new ServiceException(message);
        }
    }

    public static void isEmpty(Collection<?> collection, String message){
        if(!CollectionUtils.isEmpty(collection)){
            throw new ServiceException(message);
        }
    }

    public static void notNull(Object o, String message){
        if(Objects.isNull(o)){
            throw new ServiceException(message);
        }
    }
    public static void isNull(Object o, String message){
        if(Objects.nonNull(o)){
            throw new ServiceException(message);
        }
    }



    public static void notBlank(String s, String message){
        if(StringUtils.isBlank(s)){
            throw new ServiceException(message);
        }
    }


    public static void isFalse(boolean b, String message) {
        if(b){
            throw new ServiceException(message);
        }
    }

    public static void isTrue(boolean b, String message) {
        if(!b){
            throw new ServiceException(message);
        }
    }
}
