package com.iwhalecloud.retail.oms.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.entity.UserCopyTest;
import com.iwhalecloud.retail.oms.dto.TestUserDTO;
import com.iwhalecloud.retail.oms.mapper.TestUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class TestUserManger {

    @Resource
    TestUserMapper testUserMapper;

    public List<TestUserDTO> testFindUser() {
        log.info("测试查询开始");
        List<TestUserDTO> userDTO = testUserMapper.find();
        log.info("查询结束 userDTO{}", JSON.toJSONString(userDTO));

        return userDTO;

    }


    public List<UserCopyTest> testSelect() {

        log.info("测试查询开始");
        Page<UserCopyTest> page=new Page<>();
        page.setSize(5);
        IPage<UserCopyTest> userList = testUserMapper.selectPage(page,null);
        log.info("查询结束 userDTO{}", JSON.toJSONString(userList.getRecords()));

        return userList.getRecords();

    }
}
