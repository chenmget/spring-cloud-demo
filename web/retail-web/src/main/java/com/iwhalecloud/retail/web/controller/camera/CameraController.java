package com.iwhalecloud.retail.web.controller.camera;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.camera.service.ICameraService;
import com.iwhalecloud.retail.consts.RetailConst;
import com.iwhalecloud.retail.entity.VisitDataEntity;
import com.iwhalecloud.retail.param.req.QueryVipInfoReq;
import com.iwhalecloud.retail.param.resp.*;
import com.iwhalecloud.retail.param.resp.dto.RepeatDetailsDto;
import com.iwhalecloud.retail.param.resp.dto.VisitHistoryDto;
import com.iwhalecloud.retail.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/camera")
@Slf4j
public class CameraController extends BaseController {

    @Reference
    private ICameraService cameraService;

    /**
     * 接收推送访客消息
     */
    @RequestMapping(value = "/visitData", method = RequestMethod.POST)
    public VisitDataEntity visitData(@RequestBody VisitDataEntity entity) {
        try {
            List<Integer> personIds=entity.getPersonIds();
            String personIdsStr=null;
            if(personIds!=null&&personIds.size()>0) {
                personIdsStr=personIds.get(0)+"";
				/*for (int i = 0; i < personIds.size(); i++) {
					personIdsStr=personIdsStr+","+personIds.get(i);
				}
				if(!personIdsStr.equals("")){
					personIdsStr=personIdsStr.substring(1, personIdsStr.length());
				}*/
            }


            List<Integer> repeats=entity.getRepeats();

            String repeatsStr=null;
            if(repeats!=null&&repeats.size()>0) {
                repeatsStr=repeats.get(0)+"";
				/*for (int i = 0; i < repeats.size(); i++) {
					repeatsStr=repeatsStr+","+repeats.get(i);
				}
				if(!repeatsStr.equals("")){
					repeatsStr=repeatsStr.substring(1, repeatsStr.length());
				}*/
            }



            entity.setRepeatsStr(repeatsStr);
            entity.setPersonIdsStr(personIdsStr);
            if(entity.getType()==null||entity.getType().equals("")) {
                entity.setType("0");
            }
            cameraService.addVisitData(entity);
            entity.setState(RetailConst.HTTP_SUCC_CODE);
        } catch (Exception e) {
            entity.setState(RetailConst.HTTP_SUCC_ERROR);
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 查询最近访客列表
     */
    @RequestMapping(value = "/queryNewVisitData", method = RequestMethod.POST)
    public List<VisitDataEntity> visitData() {
        List<VisitDataEntity>  resp=new ArrayList<VisitDataEntity>();
        try {
            resp= cameraService.queryNewVisitData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 查询总客流、新客、回头客、会员
     */
    @GetMapping(value = "/queryCountData")
    public CameraPersonCountResp queryCountData() {
        CameraPersonCountResp  resp=new CameraPersonCountResp  ();
        try {
            resp= cameraService.queryCountData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 查询客户视图信息
     */
    @RequestMapping(value = "/queryVipInfo" ,method = RequestMethod.POST)
    public CameraVipInfoResp queryVipInfo(@RequestBody QueryVipInfoReq req) {
        CameraVipInfoResp  resp=new CameraVipInfoResp  ();
        try {
            resp= cameraService.queryVipInfo(req.getPersonId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 查询回头客详细信息
     */
    @GetMapping(value = "/queryRepeatsInfo")
    public RevaRepeatDetailsResp queryRepeatsInfo(String repeatsId, String personId) {
        RevaRepeatDetailsResp  resp=new RevaRepeatDetailsResp  ();
        try {
            if(repeatsId!=null && !repeatsId.equals("")) {
                resp=cameraService.queryRepeatsInfo(repeatsId);
            }else if(personId!=null && !personId.equals("")) {
                VisitHistoryResp visitHistoryResp= cameraService.visitHistory(personId);
                if(visitHistoryResp!=null&&visitHistoryResp.getList()!=null) {
                    List<VisitHistoryDto> hitList=visitHistoryResp.getList();
                    List<RepeatDetailsDto> listNew=new ArrayList<RepeatDetailsDto>();

                    for (VisitHistoryDto dto : hitList) {
                        RepeatDetailsDto newDto=new RepeatDetailsDto();
                        newDto.setImg_time(dto.getVisitingTime());
                        newDto.setMallName(dto.getAreaName());
                        listNew.add(newDto);
                    }
                    resp.setCount(listNew.size());
                    resp.setList(listNew);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 查询日报表
     */
    @GetMapping(value = "/queryDayReport")
    public RevaDailyReportResp queryDayReport() {
        RevaDailyReportResp  resp=new RevaDailyReportResp  ();
        try {
            resp= cameraService.queryDayReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

}
