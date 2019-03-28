package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.NoticeDTO;
import com.iwhalecloud.retail.system.dto.request.NoticePagePersonalReq;
import com.iwhalecloud.retail.system.dto.request.NoticePageReq;
import com.iwhalecloud.retail.system.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: NoticeMapper
 * @author autoCreate
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice>{

    Page<NoticeDTO> pageNotice(Page<NoticeDTO> page, @Param("req") NoticePageReq req);

    Page<NoticeDTO> pagePersonalNotice(Page<NoticeDTO> page, @Param("req") NoticePagePersonalReq req);

}