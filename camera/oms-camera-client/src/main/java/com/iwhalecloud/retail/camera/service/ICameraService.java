package com.iwhalecloud.retail.camera.service;

import java.util.List;

import com.iwhalecloud.retail.entity.VisitDataEntity;
import com.iwhalecloud.retail.param.resp.CameraPersonCountResp;
import com.iwhalecloud.retail.param.resp.CameraVipInfoResp;
import com.iwhalecloud.retail.param.resp.RevaDailyReportResp;
import com.iwhalecloud.retail.param.resp.RevaRepeatDetailsResp;
import com.iwhalecloud.retail.param.resp.VisitHistoryResp;

public interface ICameraService {

    /**
     * 接收推送访客消息
     */
    public int addVisitData(VisitDataEntity  entity);
    /**
     * 查询最新的15条识别记录，倒序
     * @return
     */
    public List<VisitDataEntity> queryNewVisitData();
    /**
     * 客流数量汇总
     * @return
     */
    public CameraPersonCountResp queryCountData();
    /**
     * 根据vip id 查询客户详细信息
     * @return
     */
    public CameraVipInfoResp queryVipInfo(String personId);
    /**
     * 根据回头客ID 查询回头客详细信息
     * @return
     */
    public RevaRepeatDetailsResp queryRepeatsInfo(String repeatsId);
    /**
     * 年龄-数量分布图  性别-数量分布图
     * @return
     */
    public RevaDailyReportResp queryDayReport();



    public VisitHistoryResp visitHistory(String personCode) throws Exception ;
}
