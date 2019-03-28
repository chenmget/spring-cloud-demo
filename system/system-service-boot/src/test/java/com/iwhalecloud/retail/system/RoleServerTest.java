package com.iwhalecloud.retail.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.RoleDTO;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;
import com.iwhalecloud.retail.system.service.RoleService;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class RoleServerTest {

    @Autowired
    private RoleService roleService;

    @org.junit.Test
    public void login(){

        RolePageReq req = new RolePageReq();
        req.setPageNo(1);
        req.setPageSize(10);
//        req.setRoleId("test");
//        req.setRoleName("");
        ResultVO resultVO = roleService.queryRolePage(req);
        System.out.print("结果：" + resultVO.toString());
    }
}
