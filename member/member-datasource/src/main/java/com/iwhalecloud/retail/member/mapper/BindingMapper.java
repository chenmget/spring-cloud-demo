package com.iwhalecloud.retail.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.member.entity.Binding;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: smsSendMapper
 * @author autoCreate
 */
@Mapper
public interface BindingMapper extends BaseMapper<Binding>{

	/**
	 * 添加
	 * @param req
	 * @return
	 */
	public Integer insertBinding(Binding req);
	
	/**
	 * 删除
	 * @param req
	 * @return
	 */
    public Integer deleteBindingCondition(Binding req);
    
    /**
	 * 查询
	 * @param req
	 * @return
	 */
    public List<Binding> queryBindingCondition(Binding req);
    
    /**
	 * 修改
	 * @param req
	 * @return
	 */
    public Integer updateBindingCodition(Binding req);
}