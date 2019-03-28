package com.iwhalecloud.retail.goods.manager;

import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import com.iwhalecloud.retail.goods.mapper.ProdTagsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ProdTagsManager{
   @Resource
    private ProdTagsMapper prodTagsMapper;
    /**
     * 查询标签列表通过goodsId
     * @param goodsId
     * @return
     */
    public List<ProdTagsDTO> getTagsByGoodsId(String goodsId){
        return prodTagsMapper.getTagsByGoodsId(goodsId);
    }

    public List<ProdTagsDTO> listProdTags(){
        return prodTagsMapper.listProdTags();
    }

}
