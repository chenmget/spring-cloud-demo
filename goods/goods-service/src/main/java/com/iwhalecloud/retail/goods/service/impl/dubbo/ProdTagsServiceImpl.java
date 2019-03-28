package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import com.iwhalecloud.retail.goods.manager.ProdTagsManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdTagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service
public class ProdTagsServiceImpl implements ProdTagsService {

    @Autowired
    private ProdTagsManager prodTagsManager;


    /**
     * 查询标签列表通过goodsId
     * @param goodsId
     * @return
     */
    @Override
    public List<ProdTagsDTO> getTagsByGoodsId(String goodsId){
        return prodTagsManager.getTagsByGoodsId(goodsId);
    }

    @Override
    public List<ProdTagsDTO> listProdTags() {
        return prodTagsManager.listProdTags();
    }


}