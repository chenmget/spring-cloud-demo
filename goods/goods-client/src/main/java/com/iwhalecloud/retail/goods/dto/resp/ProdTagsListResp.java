package com.iwhalecloud.retail.goods.dto.resp;

import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class ProdTagsListResp implements Serializable {
    /**
     * 标签列表
     */
    List<ProdTagsDTO> tagsList;
}
