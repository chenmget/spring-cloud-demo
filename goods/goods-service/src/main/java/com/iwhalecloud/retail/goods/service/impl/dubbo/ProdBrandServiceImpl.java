package com.iwhalecloud.retail.goods.service.impl.dubbo;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdBrandGetResp;
import com.iwhalecloud.retail.goods.entity.ProdBrand;
import com.iwhalecloud.retail.goods.manager.ProdBrandManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdBrandService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("prodBrandService")
@Service
public class ProdBrandServiceImpl implements ProdBrandService {

    @Autowired
    private ProdBrandManager prodBrandManager;

    
    @Override
    public ResultVO<ProdBrandGetResp> getBrand(ProdBrandGetReq req) {
        log.info("GoodsBrandServiceImpl.getBrand req={}", req);
        
        ResultVO<ProdBrandGetResp> resultVo = new ResultVO<ProdBrandGetResp>();
        resultVo.setResultMsg("查询失败");
        resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
        
        if (StringUtils.isNotBlank(req.getBrandId())) {
        	ProdBrandGetResp dto = prodBrandManager.selectByPrimaryKey(req.getBrandId());
        	resultVo.setResultMsg("查询成功");
        	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        	resultVo.setResultData(dto);
		}
        return resultVo;
    }

    @Override
    public ResultVO<List<ProdBrandGetResp>> listBrand() {
    	ResultVO<List<ProdBrandGetResp>> resultVo = new ResultVO<List<ProdBrandGetResp>>();
         
     	List<ProdBrandGetResp> listAll = prodBrandManager.listAll();
     	resultVo.setResultMsg("查询成功");
     	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
     	resultVo.setResultData(listAll);
        return resultVo;
    }

    @Override
    @Transactional
    public ResultVO<String> addBrand(ProdBrandAddReq req) {
    	log.info("GoodsBrandServiceImpl.addBrand req={}", req);
    	ResultVO<String> resultVo = new ResultVO<String>();
    	resultVo.setResultMsg("添加成功");
    	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
    	
    	ProdBrand t = new ProdBrand();
    	BeanUtils.copyProperties(req, t);
    	
     	Integer num = prodBrandManager.insertSelective(t);
     	if (num <= 0) {
    		resultVo.setResultMsg("添加失败");
        	resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
     	resultVo.setResultData(t.getBrandId());
        return resultVo;
    }

    @Override
    @Transactional
    public ResultVO<Integer> updateBrand(ProdBrandUpdateReq req) {
        log.info("GoodsBrandServiceImpl.updateBrand req={}", req);
        ResultVO<Integer> resultVo = new ResultVO<Integer>();
    	resultVo.setResultMsg("更新成功");
    	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        
    	Integer num = prodBrandManager.updateByPrimaryKeySelective(req);
    	if (num <= 0) {
    		resultVo.setResultMsg("更新失败");
        	resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
    	resultVo.setResultData(num);
        return resultVo;
    }

    @Override
    public ResultVO<Integer> deleteBrand(ProdBrandDeleteReq req) {
        log.info("GoodsBrandServiceImpl.deleteBrand req={}", req);
        ResultVO<Integer> resultVo = new ResultVO<Integer>();
    	resultVo.setResultMsg("删除成功");
    	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        
    	Integer num = prodBrandManager.deleteByPrimaryKey(req.getBrandId());
    	if (num <= 0) {
    		resultVo.setResultMsg("删除失败");
        	resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
    	resultVo.setResultData(num);
        return resultVo;
    }
    
    
}