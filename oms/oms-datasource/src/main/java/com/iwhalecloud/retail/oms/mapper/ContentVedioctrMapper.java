package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.ContentVedioctr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ContentVedioctrMapper
 * @author autoCreate
 */
@Mapper
public interface ContentVedioctrMapper extends BaseMapper<ContentVedioctr>{
    /**
     * 停用内容轮播
     * @param StorageNum
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    int display(@Param("StorageNum") String StorageNum);

}