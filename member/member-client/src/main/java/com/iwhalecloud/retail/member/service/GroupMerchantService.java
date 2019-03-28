package com.iwhalecloud.retail.member.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantAddReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantDeleteReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantQueryReq;
import com.iwhalecloud.retail.member.dto.request.GroupMerchantUpdateReq;
import com.iwhalecloud.retail.member.dto.response.GroupMerchantQueryResp;

public interface GroupMerchantService{
    /**
     * 新增商户会员群
     * @param req
     * @return
     */
    ResultVO<Boolean> addGroupMerchant(GroupMerchantAddReq req);

    /**
     * 更新商户会员群
     * @param req
     * @return
     */
    ResultVO<Boolean> updateGroupMerchant(GroupMerchantUpdateReq req);

    /**
     * 删除商户会员群
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteGroupMerchant(GroupMerchantDeleteReq req);


    /**
     * 分页查询商户会员群
     * @param req
     * @return
     */
    ResultVO<Page<GroupMerchantQueryResp>> queryGroupMerchantForPage(GroupMerchantQueryReq req);
}