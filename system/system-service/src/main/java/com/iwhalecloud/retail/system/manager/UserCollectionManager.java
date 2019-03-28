package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.dto.request.UserCollectionCancelReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionJudgeReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionListReq;
import com.iwhalecloud.retail.system.entity.UserCollection;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.system.mapper.UserCollectionMapper;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


/**
 *  用户收藏操作类
 * @author z
 */
@Component
public class UserCollectionManager {
    @Resource
    private UserCollectionMapper userCollectionMapper;


    /**
     * 添加收藏
     * @param userCollection1Entity
     * @return
     */
    public String addUserCollection(UserCollection userCollection1Entity) {

        userCollection1Entity.setCreateDate(new Date());
        userCollectionMapper.insert(userCollection1Entity);
        return userCollection1Entity.getId();
    }

    /**
     * 取消收藏
     * @param id 记录ID
     * @return
     */
    public boolean deleteUserCollection(String id) {
        return userCollectionMapper.deleteById(id) >= 1;
    }


    /**
     * 判断单个对象用户是否有收藏
     * @param req
     * @return true:已收藏    false:未收藏
     */
    public boolean  booCollection(UserCollectionJudgeReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(UserCollection.FieldNames.userId.getTableFieldName(),req.getUserId());
        queryWrapper.eq(UserCollection.FieldNames.objType.getTableFieldName(),req.getObjType());
        queryWrapper.eq(UserCollection.FieldNames.objId.getTableFieldName(),req.getObjId());
        return userCollectionMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 查询用户的收藏
     * @param req 用户收藏请求对象
     * @return
     */
    public List<UserCollection> queryUserCollection(UserCollectionListReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(UserCollection.FieldNames.userId.getTableFieldName(),req.getUserId());

        if (!StringUtils.isEmpty(req.getObjType())) {
            queryWrapper.eq(UserCollection.FieldNames.objType.getTableFieldName(), req.getObjType());
        }
        if (req.getObjIds()!=null && req.getObjIds().size()>1) {
            queryWrapper.in(UserCollection.FieldNames.objId.getTableFieldName(), req.getObjIds());
        }
        return userCollectionMapper.selectList (queryWrapper);
    }


    /**
     * 取消收藏
     * @param userCollectionCancelReq
     * @return
     */
    public boolean deleteUserCollection(UserCollectionCancelReq userCollectionCancelReq) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(UserCollection.FieldNames.userId.getTableFieldName(),userCollectionCancelReq.getUserId());
        queryWrapper.eq(UserCollection.FieldNames.objType.getTableFieldName(),userCollectionCancelReq.getObjType());
        queryWrapper.eq(UserCollection.FieldNames.objId.getTableFieldName(),userCollectionCancelReq.getObjId());
        return userCollectionMapper.delete(queryWrapper) > 0;
    }
}
