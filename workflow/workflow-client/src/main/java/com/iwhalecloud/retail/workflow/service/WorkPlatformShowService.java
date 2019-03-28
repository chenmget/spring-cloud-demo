package com.iwhalecloud.retail.workflow.service;

import com.iwhalecloud.retail.dto.ResultVO;

public interface WorkPlatformShowService {
    /**
     * 通过用户id查询待处理事项数量
     * @param userId
     * @return
     */
    ResultVO<Integer> getUnhandledItemCount(String userId);

    /**
     * 通过用户id查询已申请数量
     * @param userId
     * @return
     */
    ResultVO<Integer> getAppliedItemCount(String userId);

    /**
     * 通过用户id查询待处理事项列表
     * @param userId
     * @return
     */
    //List<ProcessItemDTO> listUnhandledItems(String userId);

}
