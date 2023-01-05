package com.c.cclientparent.translate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.c.cclientparent.translate.pojo.TranslateInfo;import java.util.List;

/**
 * @author Nineteen
 * @since 1.0.0 [2023/1/4 19:57]
 */
public interface TranslateInfoMapper extends BaseMapper<TranslateInfo> {
    /**
     * 根据用户id查询用户有效的文件列表
     *
     * @param userId
     * @return
     */
    List<TranslateInfo> findFileListByUserId(String userId);
}