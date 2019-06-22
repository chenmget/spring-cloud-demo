package com.iwhalecloud.retail.goods.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.common.CharacterParser;
import com.iwhalecloud.retail.goods.common.FileConst;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsListReq;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsQueryReq;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.entity.ProdGoods;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ProdGoodsManager{
    @Resource
    private ProdGoodsMapper prodGoodsMapper;
    @Resource
    private ProdFileManager prodFileManager;
    
    public int insert(ProdGoods req, String catName, String brandName) {
        req.setMarketEnable(GoodsConst.MARKET_ENABLE_ZERO);
        req.setCreateTime(new Date());
        req.setDisabled(GoodsConst.DisabledEnum.ENABLE.getCode().longValue());
        String headChar = "";
        if (StringUtils.isNotEmpty(req.getName())) {
            CharacterParser characterParser = new CharacterParser();
            headChar = characterParser.getSpellingHeadChar(req.getName());
        }
        // _分类_品牌_商品名称_ search_key like %%
        req.setSearchKey("_" + catName + "_" + brandName + "_" + req.getName() + "_" + req.getSearchKey()+ "_" + headChar);
        return prodGoodsMapper.insert(req);
    }
    
    /**
     * 根据分类获取关联商品信息
     * @param catId
     * @return
     */
    public List<ComplexGoodsGetResp> getComplexGoodsByCatId(String catId){
    	List<ProdGoods> prodGoodsList = prodGoodsMapper.getComplexGoodsByCatId(catId);
    	if (null == prodGoodsList || prodGoodsList.isEmpty()) {
			return null;
		}
    	List<ComplexGoodsGetResp> list  = new ArrayList<ComplexGoodsGetResp>();
    	for (ProdGoods prodGoods : prodGoodsList) {
    		ComplexGoodsGetResp dto = new ComplexGoodsGetResp();
    		BeanUtils.copyProperties(prodGoods, dto);
    		
    		List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(prodGoods.getGoodsId(),FileConst.SubType.DEFAULT_SUB);
            if (CollectionUtils.isNotEmpty(prodFileDTOList)) {
            	dto.setDefaultImage(prodFileDTOList.get(0).getFileUrl());
            }
    		
			list.add(dto);
		}
    	return list;
    }

    public Page<ProdGoodsDTO> queryGoodsForPage(ProdGoodsQueryReq req) {
        Page<ProdGoodsDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        String sortType = req.getSortType();
        if(StringUtils.isNotEmpty(sortType)){
            for(GoodsConst.SortTypeEnum m:GoodsConst.SortTypeEnum.values()){
                if(m.getValue().equals(req.getSortType())){
                    req.setSortType(m.getCode().toString());
                }
            }
        }
        Page<ProdGoodsDTO> prodGoodsPage = prodGoodsMapper.queryGoodsForPage(page, req);
        return prodGoodsPage;
    }

    public List<ProdGoodsDTO> listGoods(ProdGoodsListReq req) {
        List<ProdGoodsDTO> prodGoodsList = prodGoodsMapper.listGoods(req);
        return prodGoodsList;
    }

    public int deleteGoodsById(String goodsId) {
        ProdGoods prodGoods = new ProdGoods();
        prodGoods.setGoodsId(goodsId);
        prodGoods.setDisabled(GoodsConst.DisabledEnum.DISABLE.getCode().longValue());
        return prodGoodsMapper.updateById(prodGoods);
    }

    public ProdGoodsDTO queryGoods(String goodsId) {
        ProdGoods prodGoods = prodGoodsMapper.selectById(goodsId);
        if (prodGoods != null) {
            ProdGoodsDTO prodGoodsDTO = new ProdGoodsDTO();
            BeanUtils.copyProperties(prodGoods, prodGoodsDTO);
            return prodGoodsDTO;
        }
        return null;
    }

    public int updateByPrimaryKey(ProdGoods record) {
        return prodGoodsMapper.updateById(record);
    }

    public int updateMarketEnableByPrimaryKey(String goodsId, Integer marketEnable) {
        ProdGoods record = new ProdGoods();
        record.setGoodsId(goodsId);
        record.setMarketEnable(marketEnable);
        return prodGoodsMapper.updateById(record);
    }

    public int updateBuyCountById(String goodsId, Long buyCount) {
        UpdateWrapper<ProdGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("GOODS_ID",goodsId);
        ProdGoods prodGoods = new ProdGoods();
        prodGoods.setBuyCount(buyCount);
        return prodGoodsMapper.update(prodGoods, updateWrapper);
    }
}
