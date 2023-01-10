package com.c.cclientparent.translate.enums;

import com.c.cclientparent.common.enums.EnumType;
import lombok.Getter;

import java.util.Objects;

/**
 * 翻译状态枚举
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/5 11:37]
 */
public enum TranslateState implements EnumType {

    WAIT_TRANSLATE(1, "待翻译"),
    IS_TRANSLATED(2, "已翻译"),
    OTHER(3, "其他"),
    ;


    TranslateState(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Getter
    public int key;

    @Getter
    public String value;

    public static String getValueByKey(int key){
        for (TranslateState value : TranslateState.values()) {
            if(value.key == key){
                return value.getValue();
            }
        }
        return null;
    };

}
