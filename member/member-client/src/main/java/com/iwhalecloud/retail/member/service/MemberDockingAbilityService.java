package com.iwhalecloud.retail.member.service;

import java.util.Map;

/**
 * @author: wang.jiaxin
 * @date: 2019年04月02日
 * @description: 会员中心对接能开接口
 **/
public interface MemberDockingAbilityService {

    Map<String, Object> register(String params) throws Exception;

    Map<String, Object> getMember(String params) throws Exception;

    Map<String, Object> addAddress(String params) throws Exception;

    Map<String, Object> deleteAddressById(String params) throws Exception;

    Map<String, Object> updateAddress(String params) throws Exception;

    Map<String, Object> listMemberAddress(String params) throws Exception;

    Map<String, Object> addGroup(String params) throws Exception;

    Map<String, Object> updateGroupById(String params) throws Exception;

    Map<String, Object> queryGroupById(String params) throws Exception;

    Map<String, Object> deleteGroup(String params) throws Exception;

    Map<String, Object> queryGroupForPage(String params) throws Exception;

    Map<String, Object> addGroupMerchant(String params) throws Exception;

    Map<String, Object> updateGroupMerchant(String params) throws Exception;

    Map<String, Object> deleteGroupMerchant(String params) throws Exception;

    Map<String, Object> queryGroupMerchantForPage(String params) throws Exception;

    Map<String, Object> addMemberGroup(String params) throws Exception;

    Map<String, Object> updateMemberGroupById(String params) throws Exception;

    Map<String, Object> deleteMemberGroup(String params) throws Exception;

    Map<String, Object> queryGroupByMemberForPage(String params) throws Exception;

    Map<String, Object> queryMemberByGroupForPage(String params) throws Exception;

    Map<String, Object> addMemberLevel(String params) throws Exception;

    Map<String, Object> updateMemberLevel(String params) throws Exception;

    Map<String, Object> deleteMemberLevel(String params) throws Exception;

    Map<String, Object> queryGroupLevelForPage(String params) throws Exception;

    Map<String, Object> addMemberMerchant(String params) throws Exception;

    Map<String, Object> updateMemberMerchant(String params) throws Exception;

    Map<String, Object> deleteMemberMerchant(String params) throws Exception;

    Map<String, Object> queryMemberMerchantForPage(String params) throws Exception;

}
