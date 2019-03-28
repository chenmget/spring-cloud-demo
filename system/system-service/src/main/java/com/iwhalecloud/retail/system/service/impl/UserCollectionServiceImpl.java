package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserCollectionDTO;
import com.iwhalecloud.retail.system.dto.request.UserCollectionCancelReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionJudgeReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionListReq;
import com.iwhalecloud.retail.system.entity.UserCollection;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.manager.UserCollectionManager;
import com.iwhalecloud.retail.system.service.UserCollectionService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@Api(value="收藏、取消收藏")
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionManager userCollectionManager;


    /**
     * 添加收藏
     * @param userCollectionDTO
     * @return 主键ID
     */
    public ResultVO<String> addUserCollection(UserCollectionDTO userCollectionDTO) {

        if (userCollectionDTO == null) {
            log.info("UserCollectionServiceImpl.addUserCollection  userCollectionDTO is null");
            return ResultVO.error("添加收藏对象不能为空");
        }
        log.info("UserCollectionServiceImpl.addUserCollection  userCollectionDTO={}", JSON.toJSONString(userCollectionDTO));

        UserCollectionJudgeReq req = new UserCollectionJudgeReq();
        BeanUtils.copyProperties(userCollectionDTO,req);
        if (userCollectionManager.booCollection(req)) {
            log.info("UserCollectionServiceImpl.addUserCollection repeat collection,req={}",JSON.toJSONString(req));
            return ResultVO.error("已经收藏，请不要重复收藏");
        }

        UserCollection userCollection1Entity = new UserCollection();
        BeanUtils.copyProperties(userCollectionDTO,userCollection1Entity);
        String id = userCollectionManager.addUserCollection(userCollection1Entity);
        log.info("UserCollectionServiceImpl.addUserCollection  id={}", id);
        return ResultVO.success(id);
    }

    /**
     * 取消收藏
     * @param id 记录ID
     * @return
     */
    @Override
    public ResultVO<Boolean> deleteUserCollection(String id) {

        log.info("UserCollectionServiceImpl.deleteUserCollection  id={}", id);
        boolean booCollection = userCollectionManager.deleteUserCollection(id);
        log.info("UserCollectionServiceImpl.deleteUserCollection  booCollection={}", booCollection);

        if (booCollection) {
            return ResultVO.success(booCollection);
        }
        return ResultVO.error("记录不存在");
    }


    /**
     * 判断单个对象用户是否有收藏
     * @param req 用户ID
     * @return true:已收藏    false:未收藏
     */
    @Override
    public ResultVO<Boolean> booCollection(UserCollectionJudgeReq req) {
        if (req == null) {
            log.info("UserCollectionServiceImpl.booCollection  req is null");
            return ResultVO.error("查询请求对象不能为空");
        }
        if (StringUtils.isEmpty(req.getUserId()) || StringUtils.isEmpty(req.getObjType()) || StringUtils.isEmpty(req.getObjId())) {
            log.info("UserCollectionServiceImpl.booCollection missing required parameters,userId={},objType={},objId={}",req.getUserId(),req.getObjType(),req.getObjId());
            return ResultVO.error("缺少必须的查询条件");
        }
        log.info("UserCollectionServiceImpl.booCollection  req={}", JSON.toJSONString(req));
        boolean booCollection = userCollectionManager.booCollection(req);
        log.info("UserCollectionServiceImpl.booCollection  booCollection={}", booCollection);
        return ResultVO.success(booCollection);
    }

    /**
     * 查询用户的收藏
     * @param req 用户收藏请求对象
     * @return
     */
    @Override
    public ResultVO<List<UserCollectionDTO>> queryUserCollection(UserCollectionListReq req) {
        if (req == null) {
            log.info("UserCollectionServiceImpl.queryUserCollection  queryUserCollection is null");
            return ResultVO.error("查询用户的收藏对象不能为空");
        }
        if (StringUtils.isEmpty(req.getUserId())) {
            log.info("UserCollectionServiceImpl.queryUserCollection userId is null");
            return ResultVO.error("用户ID不能为空");
        }
        log.info("UserCollectionServiceImpl.queryUserCollection  req={}", JSON.toJSONString(req));
        List<UserCollection> userCollections = userCollectionManager.queryUserCollection(req);
        List<UserCollectionDTO> userCollectionDTOs = new ArrayList<UserCollectionDTO>();
        if (userCollections != null) {
            for (UserCollection userCollection : userCollections) {
                UserCollectionDTO dto = new UserCollectionDTO();
                BeanUtils.copyProperties(userCollection,dto);
                userCollectionDTOs.add(dto);
            }
        }
        log.info("UserCollectionServiceImpl.queryUserCollection  userCollectionDTOs={}", JSON.toJSONString(userCollectionDTOs));
        return ResultVO.success(userCollectionDTOs);
    }

    @Override
    public ResultVO<Boolean> deleteUserCollection(UserCollectionCancelReq userCollectionCancelReq) {
        if (userCollectionCancelReq == null) {
            log.info("UserCollectionServiceImpl.deleteUserCollection  deleteUserCollection is null");
            return ResultVO.error("取消收藏请求对象不能为空");
        }
        if (StringUtils.isEmpty(userCollectionCancelReq.getUserId())
                || StringUtils.isEmpty(userCollectionCancelReq.getObjType())
                || StringUtils.isEmpty(userCollectionCancelReq.getObjId())) {
            log.info("UserCollectionServiceImpl.deleteUserCollection userId={},objType={},objId={}"
                    ,userCollectionCancelReq.getUserId(),userCollectionCancelReq.getObjType(),userCollectionCancelReq.getObjId());
            return ResultVO.error("取消收藏请求对象缺少必要条件");
        }

        log.info("UserCollectionServiceImpl.booCollection  req={}", JSON.toJSONString(userCollectionCancelReq));
        boolean booCollection = userCollectionManager.deleteUserCollection(userCollectionCancelReq);
        log.info("UserCollectionServiceImpl.booCollection  booCollection={}", booCollection);
        return ResultVO.success(booCollection);
    }
}