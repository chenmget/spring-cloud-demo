package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentTextDetailDTO;
import java.util.List;

public interface ContentTextDetailService{

    public List<ContentTextDetailDTO> queryContentTextDetail(Long textid);

}