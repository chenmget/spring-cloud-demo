package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.NextProductFlowReq;
import com.iwhalecloud.retail.goods2b.dto.req.StartProductFlowReq;
import com.iwhalecloud.retail.goods2b.exception.BusinessException;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductFlowService;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Service
public class ProductFlowServiceImpl implements ProductFlowService {


    @Reference
    private MerchantService merchantService;

    @Reference
    private TaskService taskService;


    @Reference
    private UserService userService;

    @Reference
    private CommonRegionService commonRegionService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  ResultVO startProductFlow(StartProductFlowReq req) throws BusinessException {
        //新增流程
        ProcessStartReq processStartReq = this.initProcessStartReq(req);
        return  taskService.startProcess(processStartReq);


    }
    @Override
    public ResultVO reStartProductFlow(StartProductFlowReq req) throws BusinessException{
        NextProductFlowReq nextProductFlowReq = new NextProductFlowReq();
        nextProductFlowReq.setDealer(req.getDealer());
        nextProductFlowReq.setDealMsg(req.getDealMsg());
        nextProductFlowReq.setProductBaseId(req.getProductBaseId());
        nextProductFlowReq.setParamsType(req.getParamsType());
        nextProductFlowReq.setParamsValue(req.getParamsValue());
        return toProductFlowNext(nextProductFlowReq);
    }




    @Override
    public ResultVO toProductFlowNext(NextProductFlowReq req) {
        NextRouteAndReceiveTaskReq flowReq = new NextRouteAndReceiveTaskReq();

        flowReq.setFormId(req.getProductBaseId());
        flowReq.setHandlerUserId(req.getDealer());
        flowReq.setHandlerMsg(req.getDealMsg());
        flowReq.setParamsType(req.getParamsType());
        flowReq.setParamsValue(req.getParamsValue());
        //申请用户ID：创建流程的用户ID->创建人
        ResultVO<UserDetailDTO> userVO = userService.getUserDetailByUserId(req.getDealer());
;
        if (userVO!=null&&userVO.isSuccess()&&userVO.getResultData()!=null) {
            UserDetailDTO user = userVO.getResultData();
            //是否为商家
            boolean isMerchant = this.isMerchant(user.getUserFounder());

            flowReq.setHandlerUserName(isMerchant?user.getMerchantName():user.getUserName());
        }
        ResultVO resultVO = taskService.nextRouteAndReceiveTask(flowReq);
        return resultVO;
    }

    private boolean isMerchant(int userFounder) {
        if (SystemConst.UserFounderEnum.PARTNER.getCode() == userFounder
                || SystemConst.UserFounderEnum.SUPPLIER_PROVINCE.getCode() == userFounder
                || SystemConst.UserFounderEnum.SUPPLIER_GROUND.getCode() == userFounder
                || SystemConst.UserFounderEnum.MANUFACTURER.getCode() == userFounder) {
            return true;
        }

        return false;
    }

    private ProcessStartReq initProcessStartReq(StartProductFlowReq req) {

        ProcessStartReq processStartDTO = new ProcessStartReq();
         String  productName = req.getProductName();
        //申请用户ID：创建流程的用户ID->创建人
        ResultVO<UserDetailDTO> userVO = userService.getUserDetailByUserId(req.getDealer());

        //申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名
        String applyUserName = "";
        //如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息
        String extends1 = "";

        if (userVO!=null&&userVO.isSuccess()&&userVO.getResultData()!=null) {
            UserDetailDTO user = userVO.getResultData();
            //是否为商家
            boolean isMerchant = this.isMerchant(user.getUserFounder());
            if (isMerchant) {
                String lanIdName = "";
                String cityName = "";
                String relCode = user.getRelCode();
                if (StringUtils.isNotEmpty(relCode)) {
                    MerchantGetReq merchantGetReq = new MerchantGetReq();
                    merchantGetReq.setMerchantId(relCode);
                    //获取商家
                    ResultVO<MerchantDetailDTO> merchantDetailVO = merchantService.getMerchantDetail(merchantGetReq);
                    if (merchantDetailVO != null && merchantDetailVO.isSuccess() && merchantDetailVO.getResultData() != null) {
                        applyUserName = merchantDetailVO.getResultData().getMerchantName();
                        //获取地市+区县
                        List<String> regionIdList = new ArrayList<String>();
                        //地市
                        String lanId = merchantDetailVO.getResultData().getLanId();
                        //市县
                        String city = merchantDetailVO.getResultData().getCity();
                        if (StringUtils.isNotEmpty(lanId)) {
                            regionIdList.add(lanId);
                        }
                        if (StringUtils.isNotEmpty(city)) {
                            regionIdList.add(city);
                        }
                        if (!regionIdList.isEmpty()) {
                            CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
                            commonRegionListReq.setRegionIdList(regionIdList);
                            ResultVO<List<CommonRegionDTO>> regionListVO = commonRegionService.listCommonRegion(commonRegionListReq);
                            if (regionListVO != null && regionListVO.isSuccess() && regionListVO.getResultData() != null) {
                                List<CommonRegionDTO> regionDTOList = regionListVO.getResultData();
                                for (CommonRegionDTO commonRegionDTO : regionDTOList) {
                                    if (commonRegionDTO.getRegionId().equals(lanId)) {
                                        lanIdName = commonRegionDTO.getRegionName();
                                    }
                                    if (commonRegionDTO.getRegionId().equals(city)) {
                                        cityName = commonRegionDTO.getRegionName();
                                    }
                                }
                            }
                        }
                    }
                }
                extends1 = cityName + lanIdName;
            } else {//非商家
                applyUserName = user.getUserName();
                extends1 = StringUtils.defaultString(user.getSysPostName()) + StringUtils.defaultString(user.getOrgName());
            }

        }

        processStartDTO.setApplyUserId(req.getDealer());
        //申请人如果是商家，“申请人”就显示商家名称，如果是电信管理员则显示姓名
        processStartDTO.setApplyUserName(applyUserName);
        //业务ID->产品ID
        processStartDTO.setFormId(req.getProductBaseId());
        //如果申请人是商家，该字段信息显示“地市+区县”信息，如果是电信人员则显示“岗位+部门”信息
        processStartDTO.setExtends1(extends1);
        //标题->产品名称+产品管理审批流程
        processStartDTO.setTitle(productName + "产品管理审批流程");
        //流程ID->待提供
        processStartDTO.setProcessId(req.getProcessId());
//        processStartDTO.setProcessId(ProductConst.APP_PRODUCT_FLOW_PROCESS_ID);

        //下一环节处理用户,不填
//        processStartDTO.setNextHandlerUser(null);

        //流程类型->厂家产品管理流程
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1110.getTaskSubType());

        //流程参数类型
        processStartDTO.setParamsType(req.getParamsType());
        //流程参数值
        processStartDTO.setParamsValue(req.getParamsValue());

        return processStartDTO;

    }
}
