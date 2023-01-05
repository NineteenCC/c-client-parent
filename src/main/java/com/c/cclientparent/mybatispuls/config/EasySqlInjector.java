package com.c.cclientparent.mybatispuls.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 批量插入方法自定义sql编辑器
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/25 10:22]
 */
public class EasySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        //添加批量插入方法
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }

}
