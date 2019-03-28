package com.iwhalecloud.retail.warehouse.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileUtil {

    @Value("${spring.profiles.active}")
    private String profiles;

    private final String PROFILE  = "warehouse";

    /**
     * true: 调用bss3.0接口 false: 走本地代码
     * @return
     */
    public boolean isLocal(){
        String [] profileArray = profiles.split(",");
        String profile = "";
        if (ArrayUtils.isNotEmpty(profileArray)) {
            profile = profileArray[0];
        }
        log.info("profiles={}", profiles);
        if (PROFILE.equals(profile)) {
            return true;
        }
        return false;
    }
}
