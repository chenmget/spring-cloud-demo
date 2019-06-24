package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.entity.VisitDataEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CameraMapper extends BaseMapper<VisitDataEntity> {
	public int addVisitData(VisitDataEntity entity);

	public List<VisitDataEntity> queryNewVisitData();
	
	public int sumAllNum();

    public int sumNum();
    
    public int vipNum();
    
    public int repeatNum();

}
