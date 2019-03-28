package com.iwhalecloud.retail.warehouse.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.iwhalecloud.retail.warehouse")
@PropertySource(value= {"classpath:const/constant.properties"},ignoreResourceNotFound=false,encoding="UTF-8",name="constant.properties")
@Data
public class Constant {

    /**
     * 缺少参数：商家
     */
    private String noMerchantMsg;

    /**
     * 获取不到商家
     */
    private String cannotGetMerchantMsg;

    /**
     * 缺少参数：产品
     */
    private String noProductIdMsg;

    /**
     * 获取不到商品
     */
    private String cannotGetProductMsg;

    /**
     * 没有仓库
     */
    private String noStoreMsg;

    /**
     * 获取不到仓库
     */
    private String cannotGetStoreMsg;

    /**
     *  获取调拨权限
     */
    private String cannotGetTransferPermission;

    /**
     * 串码校验:串码不存在
     */
    private String noResInst;

    /**
     * 串码校验:串码与产品不匹配
     */
    private String pesInstMismatch;

    /**
     * 串码校验:校验成功
     */
    private String pesInstCheckSuc;

    /**
     * 串码校验:串码状态错误,非在库可用
     */
    private String pesInstInvalid;

    /**
     * 串码校验:错误信息明细前缀
     */
    private String pesInstErrorsPre;

    /**
     * 请求不合法
     */
    private String errorRequest;
    /**
     * 添加申请单失败
     */
    private String addRequestItemError;
    /**
     * 调拨申请单
     */
    private String allocateRequestItem;
    /**
     * 串码id不正确
     */
    private String mktResInstIdError;
    /**
     * 发起工作流异常
     */
    private String startWorkFlowError;
    /**
     * 申请单状态不正确
     */
    private String requestItemInvalid;
    /**
     * 没有查找到申请单
     */
    private String cannotGetRequestItemMsg;
    /**
     * 调拨审批流程
     */
    private String allocateWorkFlow;
    /**
     * 入库失败串码
     */
    private String failInNbrs;
    /**
     * 获取额度失败
     */
    private String merchantLimitError;
    /**
     * 绿色通道超过限额审批流程
     */
    private String greenChannelAboveLimitRequestTitle;
    /**
     * 时间转换错误
     */
    private String dateTranslateError;
    /**
     * 调拨数目超额度
     */
    private String allocateAboveLimit;
    /**
     * 请求异常,参数错误
     */
    private String commonParamError;
    /**
     * 不存在
     */
    private String commonNoExit;

    /**
     * 已经存在
     */
    private String commonExit;

    /**
     * 仓库ID为
     */
    private String storeTitle;

    /**
     * 营销资源同步成功
     */
    private String storeDealSuc;

    /**
     * 营销资源同步失败
     */
    private String storeDealError;



}
