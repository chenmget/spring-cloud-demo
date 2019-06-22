package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.GroupQueryForPageReq;
import com.iwhalecloud.retail.member.dto.response.GroupQueryResp;
import com.iwhalecloud.retail.member.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: GroupMapper
 * @author autoCreate
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group>{

    /**
     * 分页查询群组列表
     * @param page
     * @param req
     * @return
     */
    Page<GroupQueryResp> queryGroupForPage(Page<GroupQueryResp> page, @Param("req") GroupQueryForPageReq req);
}