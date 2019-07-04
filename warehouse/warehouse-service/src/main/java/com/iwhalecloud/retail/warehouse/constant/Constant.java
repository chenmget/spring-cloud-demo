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
     * 获取不到产品归属厂商
     */
    private String cannotGetMuanfacturerMsg;

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
     * 串码校验:厂商库该机型串码不存在！
     */
    private String noResInstInMerchant;

    /**
     * 串码校验:该产品串码已在库，请不要重复录入！
     */
    private String mktResInstExists;

    /**
     * 串码校验:串码状态错误,非在库可用
     */
    private String pesInstInvalid;

    /**
     * 串码入库失败
     */
    private String addNbrFail;

    /**
     * 串码入库成功
     */
    private String addNbrSucess;

    /**
     * 更新串码失败
     */
    private String updateNbrFail;

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
     * 发货出库失败
     */
    private String deliveryOutFail;
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

    /**
     * 商家查询到多个仓库
     */
    private String getRepeatStoreMsg;

    /**
     * 管理员给厂商录入串码只能录厂商自己的商品
     */
    private String notMatchMerchant;

    /**
     * 调用能开接口异常
     */
    private String zopInterfaceError;

    /**
     * 串码已存在
     */
    private String zopNbrExists;

    /**
     * 串码申请单中已存在
     */
    private String reqDetailNbrExists;

    /**
     * 路由器及泛智能终端的终端串码只能包含大写字母、数字和-，且必须为12位
     */
    private String vaileNbr12;

    /**
     * 光猫的终端串码只能包含大写字母、数字和-，且必须为24位
     */
    private String vaileNbr24;

    /**
     * 融合终端的终端串码只能包含大写字母、数字和-，且必须为32位或39位
     */
    private String vaileNbr32Or39;

    /**
     * 机顶盒的终端串码只能包含大写字母、数字和-，且必须为32位
     */
    private String vaileNbr32;

    /**
     * MAC码库中已存在
     */
    private String macExists;

    /**
     * SN码库中已存在
     */
    private String snExists;

    /**
     * CTEI码库中已存在
     */
    private String ctExists;

    /**
     * 库存更新失败
     */
    private String updateInstStoreFail;

    /**
     * 串码入库申请单
     */
    private String addNbrRequestItem;

    /**
     * 串码入库审批流程
     */
    private String addNbrWorkFlow;

    /**
     * 串码入库审批流程
     */
    private String canNotAllocate;

    /**
     * 地市仓库中不存在输入串码
     */
    private String notExistsNbrInCity;

    /**
     * 失败串码数据
     */
    private String failNbr;

    /**
     * 产品类型不存在
     */
    private String typeNotExists;

    /**
     * 交易过来的串码不允许退库
     */
    private String tradeNbrCanNotReset;

    /**
     * 非供应商不允许退库
     */
    private String notSupplierCanNotReset;

    /**
     * 您本月绿色通道免审核额度已用完，本次串码已提交至串码管理员审核，审核通过后方可入库
     */
    private String greenChannelAudit;

    /**
     * 串码调拨审核中
     */
    private String allocateAudit;

    /**
     * 串码调拨已提交
     */
    private String allocateSubmit;

}
