package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.*;
import com.iwhalecloud.retail.member.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wang.jiaxin
 * @date: 2019年04月02日
 * @description:
 **/
public class MemberDockingAbilityServiceImpl implements MemberDockingAbilityService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAddressService memberAddressService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMerchantService groupMerchantService;

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private MemberLevelService memberLevelService;

    @Autowired
    private MemberMerchantService memberMerchantService;

    @Override
    public Map<String, Object> register(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberAddReq req = JSON.parseObject(params, new TypeReference<MemberAddReq>() {});
            ResultVO resultVO = memberService.register(req);
            if (resultVO.isSuccess()) {
                resultMap.put("resultData", resultVO.getResultData());
                resultMap.put("resultCode", resultVO.getResultCode());
                resultMap.put("resultMsg", resultVO.getResultMsg());
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> getMember(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGetReq req = JSON.parseObject(params, new TypeReference<MemberGetReq>() {});
            ResultVO<MemberDTO> resultVO = memberService.getMember(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addAddress(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberAddressAddReq req = JSON.parseObject(params, new TypeReference<MemberAddressAddReq>() {});
            ResultVO<MemberAddressAddResp> resultVO = memberAddressService.addAddress(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteAddressById(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
//            MemberAddressAddReq req = JSON.parseObject(params, new TypeReference<MemberAddressAddReq>() {});
            ResultVO<Integer> resultVO = memberAddressService.deleteAddressById(params);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateAddress(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberAddressUpdateReq req = JSON.parseObject(params, new TypeReference<MemberAddressUpdateReq>() {});
            ResultVO<Integer> resultVO = memberAddressService.updateAddress(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> listMemberAddress(String params) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberAddressListReq req = JSON.parseObject(params, new TypeReference<MemberAddressListReq>() {});
            ResultVO<List<MemberAddressRespDTO>> resultVO = memberAddressService.listMemberAddress(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("resultCode", "9999");
        resultMap.put("resultMsg", "调用失败");
        return resultMap;
    }


    @Override
    public Map<String, Object> addGroup(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupAddReq req = JSON.parseObject(params, new TypeReference<GroupAddReq>() {
            });
            ResultVO<Boolean> resultVO = groupService.addGroup(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateGroupById(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupUpdateReq req = JSON.parseObject(params, new TypeReference<GroupUpdateReq>() {
            });
            ResultVO<Boolean> resultVO = groupService.updateGroupById(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryGroupById(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupQueryDetailReq req = JSON.parseObject(params, new TypeReference<GroupQueryDetailReq>() {
            });
            ResultVO<GroupQueryResp> resultVO = groupService.queryGroupById(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteGroup(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupDeleteReq req = JSON.parseObject(params, new TypeReference<GroupDeleteReq>() {
            });
            ResultVO<Boolean> resultVO = groupService.deleteGroup(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryGroupForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupQueryForPageReq req = JSON.parseObject(params, new TypeReference<GroupQueryForPageReq>() {
            });
            ResultVO<Page<GroupQueryResp>> resultVO = groupService.queryGroupForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addGroupMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupMerchantAddReq req = JSON.parseObject(params, new TypeReference<GroupMerchantAddReq>() {
            });
            ResultVO<Boolean> resultVO = groupMerchantService.addGroupMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateGroupMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupMerchantUpdateReq req = JSON.parseObject(params, new TypeReference<GroupMerchantUpdateReq>() {
            });
            ResultVO<Boolean> resultVO = groupMerchantService.updateGroupMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteGroupMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupMerchantDeleteReq req = JSON.parseObject(params, new TypeReference<GroupMerchantDeleteReq>() {
            });
            ResultVO<Boolean> resultVO = groupMerchantService.deleteGroupMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryGroupMerchantForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            GroupMerchantQueryReq req = JSON.parseObject(params, new TypeReference<GroupMerchantQueryReq>() {
            });
            ResultVO<Page<GroupMerchantQueryResp>> resultVO = groupMerchantService.queryGroupMerchantForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addMemberGroup(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGroupAddReq req = JSON.parseObject(params, new TypeReference<MemberGroupAddReq>() {
            });
            ResultVO<Boolean> resultVO = memberGroupService.addMemberGroup(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateMemberGroupById(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGroupUpdateReq req = JSON.parseObject(params, new TypeReference<MemberGroupUpdateReq>() {
            });
            ResultVO<Boolean> resultVO = memberGroupService.updateMemberGroupById(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteMemberGroup(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGroupDeleteReq req = JSON.parseObject(params, new TypeReference<MemberGroupDeleteReq>() {
            });
            ResultVO<Boolean> resultVO = memberGroupService.deleteMemberGroup(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryGroupByMemberForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGroupQueryGroupReq req = JSON.parseObject(params, new TypeReference<MemberGroupQueryGroupReq>() {
            });
            ResultVO<Page<MemberGroupQueryResp>> resultVO = memberGroupService.queryGroupByMemberForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryMemberByGroupForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberGroupQueryMemberReq req = JSON.parseObject(params, new TypeReference<MemberGroupQueryMemberReq>() {
            });
            ResultVO<Page<GroupQueryResp>> resultVO = memberGroupService.queryMemberByGroupForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addMemberLevel(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberLevelAddReq req = JSON.parseObject(params, new TypeReference<MemberLevelAddReq>() {
            });
            ResultVO<Boolean> resultVO = memberLevelService.addMemberLevel(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateMemberLevel(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberLevelUpdateReq req = JSON.parseObject(params, new TypeReference<MemberLevelUpdateReq>() {
            });
            ResultVO<Boolean> resultVO = memberLevelService.updateMemberLevel(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteMemberLevel(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberLevelDeleteReq req = JSON.parseObject(params, new TypeReference<MemberLevelDeleteReq>() {
            });
            ResultVO<Boolean> resultVO = memberLevelService.deleteMemberLevel(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryGroupLevelForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberLevelQueryReq req = JSON.parseObject(params, new TypeReference<MemberLevelQueryReq>() {
            });
            ResultVO<Page<MemberLevelQueryResp>> resultVO = memberLevelService.queryGroupLevelForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addMemberMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberMerchantAddReq req = JSON.parseObject(params, new TypeReference<MemberMerchantAddReq>() {
            });
            ResultVO<Boolean> resultVO = memberMerchantService.addMemberMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateMemberMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberMerchantUpdateReq req = JSON.parseObject(params, new TypeReference<MemberMerchantUpdateReq>() {
            });
            ResultVO<Boolean> resultVO = memberMerchantService.updateMemberMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteMemberMerchant(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberMerchantDeleteReq req = JSON.parseObject(params, new TypeReference<MemberMerchantDeleteReq>() {
            });
            ResultVO<Boolean> resultVO = memberMerchantService.deleteMemberMerchant(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> queryMemberMerchantForPage(String params) throws Exception {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        if (params instanceof String) {
            MemberMerchantQueryForPageReq req = JSON.parseObject(params, new TypeReference<MemberMerchantQueryForPageReq>() {
            });
            ResultVO<Page<MemberMerchantQueryForPageResp>> resultVO = memberMerchantService.queryMemberMerchantForPage(req);
            if (successResultMap(resultMap, resultVO)) {
                return resultMap;
            }
        }
        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    /**
     * 接口返回结果，若为成功，则定义resultMap
     *
     * @param resultMap resultMap
     * @param resultVO  结果
     * @return 接口返回结果
     */
    private boolean successResultMap(HashMap<String, Object> resultMap, ResultVO resultVO) {
        if (resultVO.isSuccess() && null != resultVO.getResultData()) {
            resultMap.put("resultData", resultVO.getResultData());
            resultMap.put("resultCode", resultVO.getResultCode());
            resultMap.put("resultMsg", resultVO.getResultMsg());
            return true;
        }
        return false;
    }
}
