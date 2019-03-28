package com.iwhalecloud.retail.member.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.member.dto.request.BindingAddReq;
import com.iwhalecloud.retail.member.dto.request.BindingDeleteReq;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.BindingUpdateReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.entity.Binding;
import com.iwhalecloud.retail.member.mapper.BindingMapper;


@Component
public class BindingManager{
    @Resource
    private BindingMapper bindingMapper;
    
    /**
   	 * 添加
   	 * @param req
   	 * @return
   	 */
   	public Integer insertBinding(BindingAddReq req){
   		Binding binding = new Binding();
   		BeanUtils.copyProperties(req, binding);
   		return bindingMapper.insertBinding(binding);
   	}
   	
   	/**
   	 * 删除
   	 * @param req
   	 * @return
   	*/
    public Integer deleteBindingCondition(BindingDeleteReq req){
    	Binding binding = new Binding();
   		BeanUtils.copyProperties(req, binding);
   		return bindingMapper.deleteBindingCondition(binding);
    }
       
    /**
   	 * 查询
   	 * @param req
   	 * @return
   	 */
    public List<BindingQueryResp> queryeBindingCodition(BindingQueryReq req){
    	Binding binding = new Binding();
   		BeanUtils.copyProperties(req, binding);
   		List<Binding> list = bindingMapper.queryBindingCondition(binding);
   		if (list == null || list.isEmpty()) {
			return null;
		}
   		List<BindingQueryResp> rList = new ArrayList<BindingQueryResp>();
   		for (Binding t : list) {
   			BindingQueryResp rBinding = new BindingQueryResp();
   			BeanUtils.copyProperties(t, rBinding);
   			rList.add(rBinding);
		}
   		return rList;
    }
       
    /**
   	 * 修改
   	 * @param req
   	 * @return
   	*/
    public Integer updateBindingCodition(BindingUpdateReq req){
    	Binding binding = new Binding();
   		BeanUtils.copyProperties(req, binding);
   		return bindingMapper.updateBindingCodition(binding);
    }
    
}
