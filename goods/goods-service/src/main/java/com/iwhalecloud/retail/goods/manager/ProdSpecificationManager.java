package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecificationGetResp;
import com.iwhalecloud.retail.goods.entity.ProdSpecification;
import com.iwhalecloud.retail.goods.mapper.ProdSpecificationMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProdSpecificationManager{
    @Resource
    private ProdSpecificationMapper prodSpecificationMapper;

    public List<ProdSpecification> selectSpecBySpecId(String specId) {
        QueryWrapper<ProdSpecification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SPEC_ID", specId);
        List<ProdSpecification> prodGoodsSpecList = prodSpecificationMapper.selectList(queryWrapper);
        return prodGoodsSpecList;
    }
    

    /**
     * 获取所有规格
     * @param req
     * @return
     */
    public List<ProdSpecificationGetResp> listSpec(){
    	List<ProdSpecification> listSpec = prodSpecificationMapper.listSpec();
    	if (null == listSpec || listSpec.isEmpty()) {
			return null;
		}
    	
    	List<ProdSpecificationGetResp> specs = new ArrayList<>(listSpec.size());
    	for (ProdSpecification spec : listSpec) {
    		ProdSpecificationGetResp dto = new ProdSpecificationGetResp();
    		BeanUtils.copyProperties(spec, dto);
    		specs.add(dto);
		}
    	return specs;
    }

    /**
     * 添加规格
     * @param req
     * @return
     */
    public Integer addSpec(ProdSpecificationAddReq req){
        ProdSpecification t = new ProdSpecification();
        BeanUtils.copyProperties(req, t);
        return prodSpecificationMapper.insert(t);
    }

    /**
     * 修改规格
     * @param req
     * @return
     */
    public Integer updateSpec(ProdSpecificationUpdateReq req){
    	ProdSpecification t = new ProdSpecification();
        BeanUtils.copyProperties(req, t);
        return prodSpecificationMapper.updateSpec(t);
    }

    /**
     * 删除规格(删除prod_sepcification、prod_spec_value、prod_goods_spec)
     * @param req
     * @return
     */
    public Integer deleteSpec(ProdSpecificationDeleteReq req){
    	return prodSpecificationMapper.deleteSpec(req.getIdArray());
    }
}
