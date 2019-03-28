package com.iwhalecloud.retail.oms.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QJobTaskManagerMapper {

    List<Map> queryJobTask1(Map map);
    List<Map> queryJobTask2(Map map);

    int updateJobTask(Map map);

    int insertJobRunLog(Map map);

    List<Map> listJobTask(Map map);

}
