package com.iwhalecloud.retail.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.manager.ReportCodeStateManager;
import com.iwhalecloud.retail.report.service.ReportCodeStateService;

@Service
public class ReportCodeStateServiceImpl implements ReportCodeStateService {

	@Autowired
	private ReportCodeStateManager reportCodeStateManager;

	
	@Override
	public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReport(ReportCodeStatementsReq req) {
		Page<ReportCodeStatementsResp> page = (Page<ReportCodeStatementsResp>) reportCodeStateManager.getCodeStatementsReport(req);
		List<ReportCodeStatementsResp> list = page.getRecords();
		for(ReportCodeStatementsResp dto : list) {
			String create_time = dto.getCreateTime();
			String out_time = dto.getOutTime();
			String receive_time = dto.getReceiveTime();
			if(create_time != null && create_time != "" ){
	    		String[] createTime = create_time.split("\\.");
	    		if(createTime.length>0){
	    			create_time = createTime[0];
	    			dto.setCreateTime(create_time);
	    		}
	    	}
			if(out_time != null && out_time != ""){
				String[] outTime = out_time.split("\\.");
	    		if(outTime.length>0){
	    			out_time = outTime[0];
	    			dto.setOutTime(out_time);
	    		}
	    	}
			if(receive_time != null && receive_time != ""){
				String[] receiveTime = receive_time.split("\\.");
	    		if(receiveTime.length>0){
	    			receive_time = receiveTime[0];
	    			dto.setReceiveTime(receive_time);
	    		}
	    	}
		}
		
        return ResultVO.success(page);	
	}
	
	@Override
	public ResultVO<List<ReportCodeStatementsResp>> getCodeStatementsReportdc(ReportCodeStatementsReq req) {
		List<ReportCodeStatementsResp> list = (List<ReportCodeStatementsResp>) reportCodeStateManager.getCodeStatementsReportdc(req);
		List<ReportCodeStatementsResp> list2 = new ArrayList<ReportCodeStatementsResp>();
		for (ReportCodeStatementsResp rr : list) {
			String storageType = rr.getStorageType();//在库状态
			String mktResInstType = rr.getMktResInstType();//串码类型
			String sourceType = rr.getSourceType();//串码来源
			String cityId = rr.getCityId();//店中商所属地市
			String day30 = rr.getDay30();
			String day60 = rr.getDay60();
			String day90 = rr.getDay90();
			String destCityId = rr.getDestCityId();//串码流向所属地市
			String selfRegStatus = rr.getSelfRegStatus();//自注册状态
			if (storageType != null) {
				if ("1000".equals(storageType)) {
					rr.setStorageType("在库可用");
				} else if ("1202".equals(storageType)) {
					rr.setStorageType("已领用可销售");
				} else if ("1203".equals(storageType)) {
					rr.setStorageType("已销售未补贴");
				} else if ("1204".equals(storageType)) {
					rr.setStorageType("已销售已补贴");
				} else if ("1205".equals(storageType)) {
					rr.setStorageType("退换货已冻结");
				} else if ("1210".equals(storageType)) {
					rr.setStorageType("终端调拨中");
				} else if ("1211".equals(storageType)) {
					rr.setStorageType("终端已调拨");
				} 
			}
			if( mktResInstType != null){
				if("1".equals(mktResInstType)){
					rr.setMktResInstType("交易");
				}else if("2".equals(mktResInstType)){
					rr.setMktResInstType("备机");
				}else if("3".equals(mktResInstType)){
					rr.setMktResInstType("采集");
				}
			}
			
			if( sourceType != null){
				if("1".equals(sourceType)){
					rr.setSourceType("厂商");
				}else if("2".equals(sourceType)){
					rr.setSourceType("供应商");
				}else if("3".equals(sourceType)){
					rr.setSourceType("零售商");
				}
			}
			if(cityId != null){
				if("730".equals(cityId)){
					rr.setCityId("岳阳市");
				}else if("731".equals(cityId)){
					rr.setCityId("长沙市");
				}else if("732".equals(cityId)){
					rr.setCityId("湘潭市");
				}else if("733".equals(cityId)){
					rr.setCityId("株洲市");
				}else if("734".equals(cityId)){
					rr.setCityId("衡阳市");
				}else if("735".equals(cityId)){
					rr.setCityId("郴州市");
				}else if("736".equals(cityId)){
					rr.setCityId("常德市");
				}else if("737".equals(cityId)){
					rr.setCityId("益阳市");
				}else if("738".equals(cityId)){
					rr.setCityId("娄底市");
				}else if("739".equals(cityId)){
					rr.setCityId("邵阳市");
				}else if("743".equals(cityId)){
					rr.setCityId("湘西本地网");
				}else if("744".equals(cityId)){
					rr.setCityId("张家界本地网");
				}
			}
			
			if(destCityId != null){
				if("730".equals(destCityId)){
					rr.setDestCityId("岳阳市");
				}else if("731".equals(destCityId)){
					rr.setDestCityId("长沙市");
				}else if("732".equals(destCityId)){
					rr.setDestCityId("湘潭市");
				}else if("733".equals(destCityId)){
					rr.setDestCityId("株洲市");
				}else if("734".equals(destCityId)){
					rr.setDestCityId("衡阳市");
				}else if("735".equals(destCityId)){
					rr.setDestCityId("郴州市");
				}else if("736".equals(destCityId)){
					rr.setDestCityId("常德市");
				}else if("737".equals(destCityId)){
					rr.setDestCityId("益阳市");
				}else if("738".equals(destCityId)){
					rr.setDestCityId("娄底市");
				}else if("739".equals(destCityId)){
					rr.setDestCityId("邵阳市");
				}else if("743".equals(destCityId)){
					rr.setDestCityId("湘西本地网");
				}else if("744".equals(destCityId)){
					rr.setDestCityId("张家界本地网");
				}
			}
			
			if(day30 != null){
				if("0".equals(day30)){
					rr.setDay30("否");
				}else if("1".equals(day30)){
					rr.setDay30("是");
				}
			}
			if(day60 != null){
				if("0".equals(day60)){
					rr.setDay60("否");
				}else if("1".equals(day60)){
					rr.setDay60("是");
				}
			}
			if(day90 != null){
				if("0".equals(day90)){
					rr.setDay90("否");
				}else if("1".equals(day90)){
					rr.setDay90("是");
				}
			}
			
			if(selfRegStatus != null){
				if("0".equals(selfRegStatus)){
					rr.setSelfRegStatus("未注册");
				}else if("1".equals(selfRegStatus)){
					rr.setSelfRegStatus("已注册");
				}
			}
			
			String typeId = rr.getTypeId();
			if("201903142030001".equals(typeId)){
				rr.setTypeId("手机");
			}
			
			list2.add(rr);
		}
		
		
		
        return ResultVO.success(list2);	
	}
}
