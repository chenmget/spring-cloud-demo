package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserCollectionDTO;
import com.iwhalecloud.retail.system.dto.request.UserCollectionCancelReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionJudgeReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionListReq;

import java.util.List;

public interface UserCollectionService {

    /**
     * 添加收藏
     * @param userCollectionDTO
     * @return 主键ID
     */
    ResultVO<String> addUserCollection(UserCollectionDTO userCollectionDTO);

    /**
     * 取消收藏
     * @param id 记录ID
     * @return
     */
    ResultVO<Boolean> deleteUserCollection(String id);


    /**
     * 判断单个对象用户是否有收藏
     * @param req 用户ID
     * @return true:已收藏    false:未收藏
     */
    ResultVO<Boolean> booCollection(UserCollectionJudgeReq req);

    /**
     * 查询用户的收藏
     * @param req 用户收藏请求对象
     * @return
     */
    ResultVO<List<UserCollectionDTO>> queryUserCollection(UserCollectionListReq req);

    /**
     * 取消收藏
     * @param userCollectionCancelReq 取消收藏请求对象
     * @return
     */
    ResultVO<Boolean> deleteUserCollection(UserCollectionCancelReq userCollectionCancelReq);
}