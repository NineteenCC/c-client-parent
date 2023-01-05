package com.c.cclientparent.mybatispuls.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * 核心CoreMapper
 *
 * @author Nineteen
 * @since 1.0.0 [2023/1/4 11:45]
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入 仅适用于mysql
     * @param entityList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);

}
