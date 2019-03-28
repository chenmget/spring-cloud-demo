package com.iwhalecloud.retail.order.handler;

public class OrderApiDom {

    /**
     * 下单过程
     */
    private String a="zte.orderService.order.fj.addOrder";
    private String  aa="zte.orderService.order.addCreate"; //订单下单
    private String ab="out_service_code"; // 报文解析服务
    private String ac="select a.* from ES_OPEN_SERVICE_CFG a where a.source_from='MM'";//获取服务

    private String ad="数据拼装及拆单处理";//数据拼装及拆单处理
    private String bb="zte.orderService.orderAssemble.wssmall_order_create"; //互联网下单数据组装
    private String bbb="zte.orderService.orderSplit"; //拆单

    private String bc="订单归集正式工作表";
    private String bcc="zte.service.orderqueue.orderCreate"; //订单创建，创建工作流
    private String bd="zte.orderService.afterOrderCollectOperation.wssmall_order_create"; //互联网订单归集完成以后相关操作
    private String bdd="ZteJsOrderBusiExecTraceRule.initOrderStatus"; //订单初始化状态


    /**
     * 支付
     */

    private String a1="zte.orderService.user.ordercenter.deal"; //支付入口，中间有判断环节




    /**
     * 订单下单： 报文解析，获取服务（工作流） 调用服务（zte.service.orderqueue.orderCreate）
     */

    //日志
    private String log_a="调用getInfMap===>订单号:";

    /**
     *订单下单：zte.orderService.order.addCreate
     * //Map订单数据拼装并入库
     * //订单归集到队列表 method:orderCollectionToQueue
     *
     * 模板：zte.net.iservice.template.tplAccessConvert
     *
     *
     *
     *
     *
     *
     *
     */
    /**
     * 优惠券
     */
    private String Y_a="com.ztesoft.net.service.getCountGoodsAmountByMember";

}
