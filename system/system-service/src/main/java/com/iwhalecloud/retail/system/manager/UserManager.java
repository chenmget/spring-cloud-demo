package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.dto.request.UserPageReq;
import com.iwhalecloud.retail.system.dto.request.UserUpdateWithShopStaffReq;
import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserManager {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据账号查用户信息User对象
     * @param loginName
     * @return
     */
    public User login(String loginName) {
        UserGetReq req = new UserGetReq();
        req.setLoginName(loginName);
        return getUser(req);
    }

     //根据分销商ids 获取可用的系统用户（店员关联工号时 调用）
    public List<UserDTO> getEnableUserWithPartnerIds(List<String> partnerIds){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.FieldNames.statusCd.getTableFieldName(), SystemConst.USER_STATUS_VALID); // 有效的
//        queryWrapper.isNull(User.FieldNames.relNo.getTableFieldName());
        // 等于null  或 ""
        queryWrapper.and(i -> i.isNull(User.FieldNames.relNo.getTableFieldName())
                .or()
                .eq(User.FieldNames.relNo.getTableFieldName(), ""));
        queryWrapper.in(User.FieldNames.userFounder.getTableFieldName(), SystemConst.USER_FOUNDER_3, SystemConst.USER_FOUNDER_6); // 3：分销商  6：二级分销商（店员）
        queryWrapper.in(User.FieldNames.relCode.getTableFieldName(), partnerIds);

        List<User> resultList =  userMapper.selectList(queryWrapper);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : resultList){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    // 获取系统用户列表
    public List<UserDTO> listUser(UserListReq req){
        return userMapper.listUser(req);
    }

    //更新系统用户 关联店员信息
    public int updateUserWithShopStaff(UserUpdateWithShopStaffReq req){

        User user = userMapper.selectById(req.getUserId());
        if(user == null){
            return 0;
        }
        user.setRelNo(req.getStaffId());
        int result = userMapper.updateById(user);
        return result;
    }

    // 添加系统用户
    public UserDTO addAdminUser(User user){
        int result = userMapper.insert(user);
        if(result == 0){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    /*
     * 更新系统用户
     */
    @CachePut(value = SystemConst.CACHE_NAME_SYS_USER, key = "#user.userId", condition = "#user.userId != null")
    public int updateUser(User user){
        return userMapper.updateById(user);
    }

    public User getUserByUserId(String userId) {
        UserGetReq req = new UserGetReq();
        req.setUserId(userId);
        return getUser(req);
    }

    /**
     * 根据userId查找用户详情(跨表）
     * @param userId
     * @return
     */
    public UserDetailDTO getUserDetail(String userId) {
        return userMapper.getUserDetail(userId);
    }

    public Page<UserDTO> pageUser(UserPageReq req){
        Page<UserDTO> page =  new Page<UserDTO>(req.getPageNo(), req.getPageSize());
        Page<UserDTO> pageUserDTO = userMapper.pageUser(page, req);
        List<UserDTO> listUserDTO = pageUserDTO.getRecords();
        for(int i=0;i<listUserDTO.size();i++) {
        	UserDTO userDTO = listUserDTO.get(i);
        	if(userDTO != null) {
        		String regionName = userMapper.getRegionNameByRegionId(userDTO.getRegionId());
        		userDTO.setRegionId(regionName);
        	}
        }
        
        
        return pageUserDTO;
    }

    /**
     * 根据条件（精确）查找单个用户
     * @param req
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_USER, key = "#req.userId", condition = "#req.userId != null")
    public User getUser(UserGetReq req) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        if(!StringUtils.isEmpty(req.getLoginName())){
            queryWrapper.eq(User.FieldNames.loginName.getTableFieldName(), req.getLoginName());
        }
        if(!StringUtils.isEmpty(req.getUserId())){
            queryWrapper.eq(User.FieldNames.userId.getTableFieldName(), req.getUserId());
        }
        if(!StringUtils.isEmpty(req.getRelNo())){
            queryWrapper.eq(User.FieldNames.relNo.getTableFieldName(), req.getRelNo());
        }
        if(!StringUtils.isEmpty(req.getRelCode())){
            queryWrapper.eq(User.FieldNames.relCode.getTableFieldName(), req.getRelCode());
        }
        queryWrapper.last(" limit 1 "); // 限定查询条数(避免没参数的查出整表）

        List<User> userList = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return userList.get(0);
    }
}
