package com.iwhalecloud.retail.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.entity.RandomLog;

@Mapper
public interface RandomLogMapper extends BaseMapper<RandomLog> {

	/**
	 * 查询
	 * @param req
	 * @return
	 */
    public RandomLog selectLogIdByRandomCode(RandomLog req);

    /**
     * 增加
     * @param req
     * @return
     */
    public Integer insertSelective(RandomLog req);

    /**
     * 更新
     * @param req
     * @return
     */
    public Integer updateByPrimaryKey(RandomLog req);
}
