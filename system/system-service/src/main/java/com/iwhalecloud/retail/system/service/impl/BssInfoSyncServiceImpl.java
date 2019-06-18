package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.entity.CommonRegion;
import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.manager.CommonRegionManager;
import com.iwhalecloud.retail.system.manager.OrganizationManager;
import com.iwhalecloud.retail.system.manager.UserManager;
import com.iwhalecloud.retail.system.model.BssOrgRequestModel;
import com.iwhalecloud.retail.system.model.BssUserInfoRequestModel;
import com.iwhalecloud.retail.system.service.BssInfoSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/3/6 17:44
 * @description
 */

@Slf4j
@Service
public class BssInfoSyncServiceImpl implements BssInfoSyncService {



    @Autowired
    private OrganizationManager organizationManager;
    @Autowired
    private CommonRegionManager commonRegionManager;

    @Autowired
    private UserManager userManager;

    /**
     * 统一门户用户信息同步
     *
     * @param jsonString
     * @return
     */
    @Override
    public String userInfoSync(String jsonString) {
        log.info("BssInfoSyncServiceImpl.userInfoSync jsonString:{}", JSON.toJSONString(jsonString));
        String result = "";
        Map map = JSON.parseObject(jsonString, Map.class);
        Map<String, Object> systemUserMap = MapUtils.getMap(map, "systemUser");
        String actType = (String) map.get("actType");
        BssUserInfoRequestModel bssUserInfoRequestModel = JSONObject.parseObject(JSON.toJSONString(systemUserMap), BssUserInfoRequestModel.class);
        User user = new User();
        BeanUtils.copyProperties(bssUserInfoRequestModel, user);
        user.setRemark((String) systemUserMap.get("sysUserDesc"));
        user.setLoginName((String) systemUserMap.get("sysUserCode"));
        user.setStatusCd(Integer.valueOf(bssUserInfoRequestModel.getStatusCd()));
        user.setLoginName((String) systemUserMap.get("sysUserCode"));
        user.setLoginPwd((String) systemUserMap.get("password"));
        user.setUserName(bssUserInfoRequestModel.getUserName());
        user.setRegionId(bssUserInfoRequestModel.getRegionId());
        user.setOrgId((String) systemUserMap.get("userOrgId"));
        user.setPhoneNo((String) systemUserMap.get("pwdSmsTel"));
        user.setUserId((String) systemUserMap.get("sysUserId"));
        if (SystemConst.ADD_TYPE.equals(actType)) {
            user.setCreateStaff((String) systemUserMap.get("createStaff"));
            user.setUserSource(SystemConst.USER_SOURCE_PORTAL);
            log.info("BssInfoSyncServiceImpl.userInfoSync addAdminUser user:{}", JSON.toJSONString(user));
            //新增用户
            UserDTO userDTO = userManager.addAdminUser(user);
            log.info("BssInfoSyncServiceImpl.userInfoSync addAdminUser userDTO:{}", JSON.toJSONString(userDTO));
            if (userDTO != null) {
                result = assembleResult("新增成功", "0", "处理成功");
            } else {
                result = assembleResult("新增失败", "1", "处理失败");
            }
        } else {
            user.setUpdateStaff(bssUserInfoRequestModel.getUpdateStaff());
            log.info("BssInfoSyncServiceImpl.userInfoSync updateUser user:{}", JSON.toJSONString(user));
            //编辑用户
            int num = 0;
            num = userManager.updateUser(user);
            log.info("BssInfoSyncServiceImpl.userInfoSync updateUser num:{}", num);
            if (num > 0) {
                result = assembleResult("修改成功", "0", "处理成功");
            } else {
                result = assembleResult("修改失败", "1", "处理失败");
            }
        }
        log.info("BssInfoSyncServiceImpl.userInfoSync result:{}", result);
        return result;
    }

    @Override
    public String syncOrg(String jsonString) {
        Map map = JSON.parseObject(jsonString, Map.class);
        String result = "";
        Map contractRoot = MapUtils.getMap(map, "contractRoot");
        Map svcCont = MapUtils.getMap(contractRoot, "svcCont");
        Map tcpCont = MapUtils.getMap(contractRoot, "tcpCont");
        Map requestObject = MapUtils.getMap(svcCont, "requestObject");
        BssOrgRequestModel bssOrgRequestModel = JSONObject.parseObject(JSON.toJSONString(requestObject), BssOrgRequestModel.class);
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(bssOrgRequestModel, organizationDTO);
        // 商家编码
        organizationDTO.setMerchantCode(bssOrgRequestModel.getSaleBoxCode());
        // 上级组织id
        String parentOrgId = bssOrgRequestModel.getParentOrgId();
        organizationDTO.setParentOrgId(parentOrgId);
        OrganizationDTO organization = organizationManager.getOrganization(parentOrgId);
        if (!Objects.isNull(organization)) {
            String parentOrgName = organization.getOrgName();
            organizationDTO.setParentOrgName(parentOrgName);
        }
        // 本地网id
        String lanId = bssOrgRequestModel.getLanId();
        CommonRegion lan = commonRegionManager.getCommonRegionById(lanId);
        if (!Objects.isNull(lan)) {
            organizationDTO.setLan(lan.getRegionName());
        }
        // 区域id
        String regionId = bssOrgRequestModel.getRegionId();
        CommonRegion region = commonRegionManager.getCommonRegionById(regionId);
        if (!Objects.isNull(region)) {
            organizationDTO.setRegion(region.getRegionName());
        }
        int num = 0;
        if (SystemConst.ACT_TYPE_ADD.equals(bssOrgRequestModel.getActType())) {
            // 新增
            try {
                num = organizationManager.savaOrganization(organizationDTO);
                Integer resultCode = num > 0 ? SystemConst.SUCCESS_CODE : SystemConst.ERROR_CODE;
                result = this.getAddResultMsg(tcpCont, bssOrgRequestModel.getOrgId(), resultCode);
            } catch (Exception e) {
                result = this.getAddResultMsg(tcpCont, bssOrgRequestModel.getOrgId(), SystemConst.ERROR_CODE);
            }
        } else if (SystemConst.ACT_TYPE_MOD.equals(bssOrgRequestModel.getActType())) {
            // 编辑
            try {
                num = organizationManager.updateOrganization(organizationDTO);
                Integer resultCode = num > 0 ? SystemConst.SUCCESS_CODE : SystemConst.ERROR_CODE;
                result = this.getModResultMsg(tcpCont, resultCode);
            } catch (Exception e) {
                result = this.getModResultMsg(tcpCont, SystemConst.ERROR_CODE);
            }
        }
        return result;
    }

    /**
     * 拼凑返回值
     *
     * @param result
     * @param resultCode
     * @param resultMsg
     * @return
     */
    private String assembleResult(String result, String resultCode, String resultMsg) {
        String resultMsg1 = "{" +
                "\"key\": \"svcCont\"," +
                "\"svcContContext\": {" +
                "\"context\": \"" + result + "\"" +
                "}," +
                "\"resultCode\": \"" + resultCode + "\"," +
                "\"resultMsg\": \"" + resultMsg + "\"" +
                "}";
        log.info("BssInfoSyncServiceImpl.userInfoSync assembleResult resultMsg1:{}", resultMsg1);
        return resultMsg1;
    }

    /**
     * 获取新增返回值 add by xu.qinyuan
     *
     * @param tcpCont    请求体中的tcpCont
     * @param orgId      组织id
     * @param resultCode 结果编码 0：成功 1：失败
     * @return
     */
    private String getAddResultMsg(Map<String, Object> tcpCont, String orgId, Integer resultCode) {
        String resultMsg = "";
        if (SystemConst.SUCCESS_CODE.equals(resultCode)) {
            resultMsg = "处理成功";
        } else {
            resultMsg = "处理失败";
        }
        String result = "{\"contractRoot\":{\"svcCont\":{\"resultMsg\":\"" + resultMsg + "\",\"resultObject\":" +
            "{\"data\":{\"organization\":{\"orgId\":" + orgId + "}},\"key\":\"resultObject\",\"require_obj\":false},\"resultCode\":" + resultCode + "}," +
            "\"tcpCont\":{\"sign\":\"" + MapUtils.getString(tcpCont, "sign") + "\",\"rspTime\": \"" + DateUtils.getCurrentTime() + "\"," +
            "\"transactionID\":\"" + MapUtils.getString(tcpCont, "transactionId") + "\"}}}";
        return result;
    }

    /**
     * 获取编辑返回值 add by xu.qinyuan
     *
     * @param tcpCont    请求体中的tcpCont
     * @param resultCode 结果编码 0：成功 1：失败
     * @return
     */
    private String getModResultMsg(Map<String, Object> tcpCont, Integer resultCode) {
        String resultMsg = "";
        if (SystemConst.SUCCESS_CODE.equals(resultCode)) {
            resultMsg = "修改成功";
        } else {
            resultMsg = "修改失败";
        }
        String result = "{\"contractRoot\":{\"svcCont\":{\"resultMsg\":\"" + resultMsg + "\"," +
            "\"resultCode\":\"0\"},\"tcpCont\":{\"sign\":\"" + MapUtils.getString(tcpCont, "sign") + "\"," +
            "\"rspTime\":\"" + DateUtils.getCurrentTime() + "\"," +
            "\"transactionID\":\"" + MapUtils.getString(tcpCont, "transactionId") + "\"}}}";
        return result;
    }
}

