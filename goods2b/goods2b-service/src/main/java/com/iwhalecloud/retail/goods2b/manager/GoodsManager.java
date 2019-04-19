package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.SupplierGroundGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsForPageQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsPageResp;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import com.iwhalecloud.retail.goods2b.mapper.GoodsMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class GoodsManager{
    @Resource
    private GoodsMapper goodsMapper;
    
    public int addGoods(Goods goods) {
        goods.setSearchKey(goods.getGoodsName());
        goods.setIsDeleted(GoodsConst.NO_DELETE);
        goods.setCreateDate(new Date());
        goods.setUpdateDate(new Date());
        return goodsMapper.insert(goods);
    }

    public Page<GoodsForPageQueryResp> queryGoodsForPage(GoodsForPageQueryReq req) {
        Page<GoodsForPageQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        String sortType = req.getSortType();
        if(StringUtils.isNotEmpty(sortType)){
            for(GoodsConst.SortTypeEnum m:GoodsConst.SortTypeEnum.values()){
                if(m.getValue().equals(req.getSortType())){
                    req.setSortType(m.getCode().toString());
                }
            }
        }
        Page<GoodsForPageQueryResp> prodGoodsPage = goodsMapper.queryGoodsForPage(page, req);
        return prodGoodsPage;
    }

    public List<Goods> listGoods(List<String> goods){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.in("goods_id",goods);
        return goodsMapper.selectList(queryWrapper);
    }

    public Page<GoodsPageResp> queryPageByConditionAdmin(GoodsPageReq req){
        Page<GoodsPageResp> page = new Page<>(req.getPageNo(),req.getPageSize());
        return goodsMapper.queryPageByConditionAdmin(page,req);
    }

    public int deleteGoodsByGoodsId(String goodsId) {
        Goods prodGoods = new Goods();
        prodGoods.setGoodsId(goodsId);
        prodGoods.setIsDeleted(GoodsConst.DELETE);
        return goodsMapper.updateById(prodGoods);
    }

    public int updateMarketEnableGoodsId(String goodsId, Integer marketEnable) {
        Goods record = new Goods();
        record.setGoodsId(goodsId);
        record.setMarketEnable(marketEnable);
        return goodsMapper.updateById(record);
    }

    public int updateAuditStateByGoodsId(String goodsId, String auditState) {
        Goods record = new Goods();
        record.setGoodsId(goodsId);
        record.setAuditState(auditState);
        return goodsMapper.updateById(record);
    }

    public Goods queryGoods(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        queryWrapper.eq("goods_id",goodsId);
        return goodsMapper.selectOne(queryWrapper);
    }

    public int updateByPrimaryKey(Goods record) {
        record.setUpdateDate(new Date());
        return goodsMapper.updateById(record);
    }

    public int updateBuyCountById(String goodsId, Long buyCount) {
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("GOODS_ID",goodsId);
        Goods goods = new Goods();
        goods.setBuyCount(buyCount);
        return goodsMapper.update(goods, updateWrapper);
    }

    public List<SupplierGroundGoodsDTO> listSupplierGroundRelative(String productBaseId) {
        return goodsMapper.listSupplierGroundRelative(productBaseId);
    }

    public Double listSupplierGroundSupplyNum(String productBaseId) {
        return goodsMapper.listSupplierGroundSupplyNum(productBaseId);
    }
}
