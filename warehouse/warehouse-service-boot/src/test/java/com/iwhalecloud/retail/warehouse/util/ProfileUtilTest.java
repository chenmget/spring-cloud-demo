package com.iwhalecloud.retail.warehouse.util;

import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ProfileUtilTest {

    @Value("${spring.profiles.active}")
    private String profiles;

    private boolean isLocal(){
        String [] profileArray = profiles.split(",");
        String profile = "";
        if (ArrayUtils.isNotEmpty(profileArray)) {
            profile = profileArray[0];
        }
        if (profile.equals("warehouse")) {
            return true;
        }
        return false;
    }

    @Test
    public void getProfile(){
        boolean b = isLocal();
        System.out.println(b);
    }
}