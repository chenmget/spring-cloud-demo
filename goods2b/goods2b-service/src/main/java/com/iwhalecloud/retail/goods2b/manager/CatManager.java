package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.CatAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatListResp;
import com.iwhalecloud.retail.goods2b.entity.Cat;
import com.iwhalecloud.retail.goods2b.mapper.CatMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Component
public class CatManager {
    @Resource
    private CatMapper catMapper;
    
    /**
     * 获取商品分类（顶级）
     * @param dto
     * @return
     */
    public List<CatListResp> listCats(ProdGoodsCatsListByIdReq dto){
    	List<Cat> listCats = catMapper.listCats(dto);
    	if (null == listCats || listCats.isEmpty()) {
			return null;
		}
    	List<CatListResp> list = new ArrayList<CatListResp>();
    	for (Cat cat : listCats) {
    		CatListResp t = new CatListResp();
			BeanUtils.copyProperties(cat, t);
			list.add(t);
		}
    	
    	return list;
    }

	/**
	 * 新增产品类别
	 * @param req
	 * @return
	 */
    public String addProdcat(CatAddReq req){
		Cat cat = new Cat();
		BeanUtils.copyProperties(req, cat);
		cat.setCreateDate(Calendar.getInstance().getTime());
		cat.setIsDeleted(GoodsConst.NO_DELETE.toString());
		catMapper.insert(cat);
		return cat.getCatId();
	}

	/**
	 * 修改产品类别
	 * @param req
	 * @return
	 */
	public int updateProdcat(CatUpdateReq req){
		Cat cat = new Cat();
		BeanUtils.copyProperties(req, cat);
		cat.setUpdateDate(Calendar.getInstance().getTime());
		return catMapper.updateById(cat);
	}

	/**
	 * 逻辑删除
	 * 删除产品类别
	 * @param catId
	 * @return
	 */
	public int deleteProdcat(String catId){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("cat_id",catId);
		Cat p = new Cat();
		p.setIsDeleted(GoodsConst.DELETE.toString());
		return catMapper.update(p,queryWrapper);
	}

	public Cat queryProdCat(String catId){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("cat_id",catId);
		queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
		return catMapper.selectOne(queryWrapper);
	}

	/**
	 * 查询产品类别(根据产品名称查询 模糊查询)
	 * @return
	 */
	public IPage<Cat> listProdcat(Long pageNum, Long pageSize, String catName){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
		queryWrapper.like("cat_name",catName);
		IPage page = new Page();
		page.setSize(pageSize);
		page.setCurrent(pageNum);
		return catMapper.selectPage(page,queryWrapper);
	}

	/**
	 * 查询产品类别列表
	 * @return
	 */
	public List<Cat> catList(String partnerCatId,String typeId){
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
		if(!StringUtils.isEmpty(partnerCatId)){
			queryWrapper.eq("parent_cat_id",partnerCatId);
		}
		if(!StringUtils.isEmpty(typeId)){
			queryWrapper.eq("type_id",typeId);
		}
		queryWrapper.orderByDesc("cat_order");
		return catMapper.selectList(queryWrapper);
	}

}
