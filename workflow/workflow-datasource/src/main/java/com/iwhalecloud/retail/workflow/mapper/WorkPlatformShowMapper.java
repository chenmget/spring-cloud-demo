package com.iwhalecloud.retail.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkPlatformShowMapper extends BaseMapper {

    /**
     * 获取待处理事项数量
     * @param userId
     * @param statusList
     * @return
     */
    int getUnhandledItemCount(@Param("userId") String userId, @Param("statusList") List<String> statusList);

    /**
     * 获取所申请流程数量
     * @param userId
     * @param statusList
     * @return
     */
    int getAppliedItemCount(@Param("userId") String userId, @Param("statusList") List<String> statusList);
}
