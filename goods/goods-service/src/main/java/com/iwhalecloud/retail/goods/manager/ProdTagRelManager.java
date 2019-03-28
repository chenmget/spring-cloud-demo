package com.iwhalecloud.retail.goods.manager;

import com.iwhalecloud.retail.goods.entity.ProdTagRel;
import com.iwhalecloud.retail.goods.mapper.ProdTagRelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ProdTagRelManager{
    @Resource
    private ProdTagRelMapper prodTagRelMapper;

    public int deleteTagRelByGoodsId(String goodsId) {
        return prodTagRelMapper.deleteById(goodsId);
    }

    public void addTagRel(String[] tagIds, String goodsId) {
        if (tagIds != null && tagIds.length > 0) {
            for (String tagId : tagIds) {
                ProdTagRel tagRel = new ProdTagRel();
                tagRel.setTagId(tagId);
                tagRel.setGoodsId(goodsId);
                prodTagRelMapper.insert(tagRel);
            }
        }
    }
}
