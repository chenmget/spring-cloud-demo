package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.utils.SysUserCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wzy
 * @date 2019/7/1
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class SysUserCacheUtilsTest {
    @Autowired
    private SysUserCacheUtils userCacheUtils;
   @Test
    public void test(){
       User user = new User();
       user.setLoginName("admin");
       user.setUserId("1");
       user.setStatusCd(1);
       user.setFailLoginCnt(0);
       userCacheUtils.get("1");
       userCacheUtils.put("1",user);
       User newUser = userCacheUtils.get(user.getUserId());
    }
}
