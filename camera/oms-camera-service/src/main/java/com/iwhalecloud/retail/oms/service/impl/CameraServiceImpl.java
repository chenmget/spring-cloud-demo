package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.camera.service.ICameraService;
import com.iwhalecloud.retail.camera.service.IInfService;
import com.iwhalecloud.retail.entity.*;
import com.iwhalecloud.retail.oms.mapper.*;
import com.iwhalecloud.retail.param.req.RevaDailyReportReq;
import com.iwhalecloud.retail.param.resp.*;
import com.iwhalecloud.retail.param.resp.dto.VipFaceInfoDto;
import com.iwhalecloud.retail.util.DateUtil;
import com.iwhalecloud.retail.web.common.InfCameraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("cameraService")
@EnableConfigurationProperties({ InfCameraProperties.class})
public class CameraServiceImpl implements ICameraService {
	
	@Resource
	private CameraMapper cameraMapper;
	@Resource
	private CustInfoMapper custInfoMapper;
	@Resource
	private CustPackegeBuyMapper custPackegeBuyMapper;
	@Resource
	private CustPackegeFitMapper custPackegeFitMapper;
	@Resource
	private CustTagMapper custTagMapper;

	@Autowired
	IInfService infService;
	
	@Autowired
	InfCameraProperties infCameraProperties;
	
	@Override
	public int addVisitData(VisitDataEntity entity) {
		return cameraMapper.addVisitData(entity);
	}

	@Override
	public List<VisitDataEntity> queryNewVisitData() {
		List<VisitDataEntity> list = cameraMapper.queryNewVisitData();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			if (list != null) {
				for (VisitDataEntity visitDataEntity : list) {
					visitDataEntity.setFaceFrame(infCameraProperties.getServerIP() + visitDataEntity.getFaceFrame());
 
					String format2 = format.format(format.parse(visitDataEntity.getImageTime()));
					visitDataEntity.setImageTime(format2);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public CameraPersonCountResp queryCountData() {
		CameraPersonCountResp resp=new CameraPersonCountResp();
		 int sumNum=0;//日总客流量
		 int repeatNum=0;//回头客
		 int newNum=0;//新客
		 int vipNum=0;//会员
		 
		 sumNum=cameraMapper.sumNum();
		 vipNum= cameraMapper.vipNum();
		 repeatNum =cameraMapper.repeatNum();
		 newNum=(sumNum-vipNum)-repeatNum;//新客=总的-减去会员-回头客
		 
		/*try {
			 RevaDailyReportReq req=new RevaDailyReportReq ();
			 //查询日报
			 RevaDailyReportResp reportResp= infService.customersDailyReport(req);
			 
			 List<CustomerDto> list=reportResp.getCustomer();
			 if(list!=null) {
				 for (CustomerDto customerDto : list) {
					 vipNum=vipNum+customerDto.getVips();
					 
					 //女
					 sumNum=sumNum+customerDto.getFemalesage1();
					 sumNum=sumNum+customerDto.getFemalesage2();
					 sumNum=sumNum+customerDto.getFemalesage3();
					 sumNum=sumNum+customerDto.getFemalesage4();
					 sumNum=sumNum+customerDto.getFemalesage5();
					 //男
					 sumNum=sumNum+customerDto.getMalesage1();
					 sumNum=sumNum+customerDto.getMalesage2();
					 sumNum=sumNum+customerDto.getMalesage3();
					 sumNum=sumNum+customerDto.getMalesage4();
					 sumNum=sumNum+customerDto.getMalesage5();
					 
				}
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

		 resp.setVipNum(vipNum);
		 resp.setSumNum(sumNum);
		 resp.setRepeatNum(repeatNum);
		 resp.setNewNum(newNum);
		return resp;
	}

	@Override
	public CameraVipInfoResp queryVipInfo(String personId) {
		CameraVipInfoResp resp=new CameraVipInfoResp();
		try {
			if(!StringUtils.isEmpty(personId)) {
				RevaVipInfoResp revaVipInfoResp=infService.vipInfo(personId);
				String custPhone=revaVipInfoResp.getPhone();
				
				List<VipFaceInfoDto> list=revaVipInfoResp.getList();
				String custImage="";
				if(custImage!=null&&list.size()>0) {
					custImage=list.get(0).getFaceinfoUrl();
					//custImage=infCameraProperties.getServerIP()+custImage;
					
				}
				
				//String custPhone="10086";
				//
				CustInfoEntity custInfoEntity=custInfoMapper.queryCustInfo(custPhone);
				List<CustPackegeBuyEntity> buyList=custPackegeBuyMapper.queryCustPackegeBuy(custPhone);
				List<CustPackegeFitEntity> fitList=custPackegeFitMapper.queryCustPackegeFit(custPhone);
				List<CustTagEntity> tagList=custTagMapper.queryCustTag(custPhone);
				
				custInfoEntity.setCustImage(custImage);
				
				resp.setBuyList(buyList);
				resp.setCustInfoEntity(custInfoEntity);
				resp.setFitList(fitList);
				resp.setTagList(tagList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public RevaRepeatDetailsResp queryRepeatsInfo(String repeatsId) {
		RevaRepeatDetailsResp resp=new RevaRepeatDetailsResp();
		try {
			if(!StringUtils.isEmpty(repeatsId)) {
				 resp=infService.repeatCustomerDetails(repeatsId,null,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public RevaDailyReportResp queryDayReport() {
		RevaDailyReportResp resp=new RevaDailyReportResp();
		try {
			RevaDailyReportReq req=new RevaDailyReportReq ();
			req.setDate(DateUtil.getTime1());
			
			String lastDate= DateUtil.formatDate(  DateUtil.addDay(new Date(),-1),  DateUtil.DATE_FORMAT_1);
			req.setDate(DateUtil.getTime1());
			req.setLastDate(lastDate);
			resp=infService.customersDailyReport(req);
			
			if(resp==null) {
				resp=new RevaDailyReportResp();
			}
			resp.setWeekCount(cameraMapper.sumAllNum());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public VisitHistoryResp visitHistory(String personCode) throws Exception {
		VisitHistoryResp resp=new VisitHistoryResp();
		try {
			if(!StringUtils.isEmpty(personCode)) {
				 resp=infService.visitHistory(personCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
