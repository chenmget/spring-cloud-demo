package com.iwhalecloud.retail.goods.manager;

import com.iwhalecloud.retail.goods.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsCatListResp;
import com.iwhalecloud.retail.goods.entity.ProdGoodsCat;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsCatMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProdGoodsCatManager{
    @Resource
    private ProdGoodsCatMapper prodGoodsCatMapper;
    
    /**
     * 获取商品分类（顶级）
     * @param req
     * @return
     */
    public List<ProdGoodsCatListResp> listCats(ProdGoodsCatsListByIdReq dto){
    	List<ProdGoodsCat> listCats = prodGoodsCatMapper.listCats(dto);
    	if (null == listCats || listCats.isEmpty()) {
			return null;
		}
    	List<ProdGoodsCatListResp> list = new ArrayList<ProdGoodsCatListResp>();
    	for (ProdGoodsCat prodGoodsCat : listCats) {
    		ProdGoodsCatListResp t = new ProdGoodsCatListResp();
			BeanUtils.copyProperties(prodGoodsCat, t);
			list.add(t);
		}
    	
    	return list;
    }
}
