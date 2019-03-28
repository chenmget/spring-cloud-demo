package com.iwhalecloud.retail.oms.dto.resquest.content;

import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentTagDTO;
import com.iwhalecloud.retail.oms.dto.ContentTextDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ContentAddReq implements Serializable {

    //内容基础信息
    private ContentBaseDTO contentBase;

    //内容标签
    private List<ContentTagDTO> contentTagList;

    //软文
    private List<ContentTextDetailDTO> contentTextDetail;

    //素材信息
    private List<Map<String,Object>> itemInfo;
}
