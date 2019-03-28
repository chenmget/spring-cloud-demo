package com.iwhalecloud.retail.promo.constant;

/**
 * @author lhr 2019-03-20 20:10:30
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.iwhalecloud.retail.promo")
@PropertySource(value= {"classpath:const/constant.properties"},ignoreResourceNotFound=false,encoding="UTF-8",name="constant.properties")
@Data
public class Constant {

    /**
     * 活动对象不存在
     */
    private String noMarketingActivity;

    /**
     * 更新成功
     */
    private String updateSuccess;

    /**
     * 更新失败
     */
    private String updateFaile;

    /**
     * 缺少活动Id
     */
    private String noMarketingActivityId;

    /**
     * 缺少订单Id
     */
    private String noOrderId;


}
