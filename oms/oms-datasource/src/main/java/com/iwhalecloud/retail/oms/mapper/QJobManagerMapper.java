package com.iwhalecloud.retail.oms.mapper;

import java.util.List;
import java.util.Map;

public interface QJobManagerMapper {

    List<Map> queryList(Map map);

    int update(Map map);

    String queryTriggerState(Map map);

    List<Map> validateSingle(Map map);

    List<Map> jobSchedulerList(Map map);

    int delete(Map map);

    int save(Map map);

}
