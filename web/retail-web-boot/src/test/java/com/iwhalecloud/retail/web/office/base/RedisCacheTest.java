package com.iwhalecloud.retail.web.office.base;

import com.iwhalecloud.retail.RetailWebApplication;
import com.iwhalecloud.retail.web.controller.cache.RedisCacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenlong.zhong
 * @date 2019/4/25
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailWebApplication.class)
public class RedisCacheTest {

    @Autowired
    private RedisCacheUtils redisCacheUtils;


    @Test
    public void set1(){
//        System.out.println("JWTTokenUtil.updateTokenExpireTime result:" + JWTTokenUtil.updateTokenExpireTime("sessionid1"));
    }

    @Test
    public void get1(){
//        if (JWTTokenUtil.isTokenEffect("sessionid1")) {
//
//            System.out.println( "redisCacheUtils.hasKey is true");
//            System.out.println( "redisCacheUtils.getCacheObject result:" + redisCacheUtils.getCacheObject("sessionid1")) ;
//            System.out.println( "redisCacheUtils.getExpire result: " + redisCacheUtils.getExpire("sessionid1")) ;
//        } else {
//            System.out.println( "redisCacheUtils.hasKey is false");
//        }
    }


    @Test
    public void set(){
        if (redisCacheUtils.hasKey("sessionid1")) {
            System.out.println( "redisCacheUtils.hasKey is true");
        } else {
            System.out.println( "redisCacheUtils.hasKey  is false");
            System.out.println( "redisCacheUtils.setCacheObject result:" + redisCacheUtils.setCacheObject("sessionid1", "test"));
        }
        System.out.println("redisCacheUtils.expire  result:" + redisCacheUtils.expire("sessionid1", 400));
    }

    @Test
    public void get(){
        if (redisCacheUtils.hasKey("sessionid1")) {
            System.out.println( "redisCacheUtils.hasKey is true");
        } else {
            System.out.println( "redisCacheUtils.hasKey  is false");
            System.out.println( "redisCacheUtils.setCacheObject result:" + redisCacheUtils.setCacheObject("sessionid1", "test"));
        }
        System.out.println("redisCacheUtils.expire 200s result:" + redisCacheUtils.expire("sessionid1", 400));
    }


}
