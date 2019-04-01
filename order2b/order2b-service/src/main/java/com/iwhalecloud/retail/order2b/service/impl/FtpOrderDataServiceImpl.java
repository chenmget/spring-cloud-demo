package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.FtpOrderDataConsts;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.service.FtpOrderDataService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴良勇
 * @date 2019/3/29 23:35
 */
@Service
@Slf4j
@Component("ftpOrderDataService")
public class FtpOrderDataServiceImpl implements FtpOrderDataService {
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private Environment env;

    @Reference
    private MerchantService merchantService;
   // @Value("${ftp.order.ip}")
    private String ip;
    //@Value("${ftp.order.username}")
    private String username;
    //@Value("${ftp.order.password}")
    private String password;
   // @Value("${ftp.order.port}")
    private Integer port;
    //@Value("${ftp.order.filePath}")
    private String filePath;

    //@Value("${ftp.order.onePath}")
    private String onePath;
    //@Value("${ftp.order.fifteenPath}")
    private String fifteenPath;


    @Override
    public List<FtpOrderDataResp> queryFtpOrderDataRespList(FtpOrderDataReq req) {

        Page<FtpOrderDataResp>  orderListPage = orderManager.queryFtpOrderDataRespList(req);
        if(orderListPage!=null&&orderListPage.getRecords()!=null&&!orderListPage.getRecords().isEmpty()){
            List<FtpOrderDataResp> orderList = orderListPage.getRecords();
            //获取首单时间(月份)
            String fstTransDate = orderManager.getFstTransDate();
            fstTransDate = DateUtils.dateToStr(DateUtils.strToUtilDate(fstTransDate),"yyyyMM");
            for (FtpOrderDataResp orderDataResp : orderList) {

                String custId = orderDataResp.getCustId();
            }
        }

        return null;
    }
    @Override
    public ResultVO uploadFtpForTask(){
        Date date = DateUtils.strToUtilDate("2019-03-01 12:12:12");

        String day = DateUtils.dateToStr(date,FtpOrderDataConsts.DAY_FOR_DAY);
        String startDate = "";
        String endDate = "";
        String uploadPath = "";
        if(FtpOrderDataConsts.FIFTEEN.equals(day)){
            uploadPath = this.fifteenPath;
            //查询1-15的数据
            startDate = DateUtils.dateToStr(date,FtpOrderDataConsts.DAY_FOR_YEAR_MONTH)+"-"+FtpOrderDataConsts.ONE;
            endDate = DateUtils.dateToStr(date,FtpOrderDataConsts.DAY_FOR_YEAR_DAY);
        }else if(FtpOrderDataConsts.ONE.equals(day)){
            uploadPath = this.onePath;
            //查询上个月16-月末的数据
            startDate = DateUtils.dateToStr(DateUtils.getLastMonth(date),FtpOrderDataConsts.DAY_FOR_YEAR_MONTH)+"-"+FtpOrderDataConsts.SIXTEEN;
            endDate =  DateUtils.getMonthLastDay(DateUtils.getLastMonth(date));
        }else{
            return ResultVO.error("日期错误,不进行上传");
        }
        FtpOrderDataReq req = new FtpOrderDataReq();
        req.setEndDate(endDate);
        req.setStartDate(startDate);

        return this.uploadFtp(req);
    }
    @Override
    public ResultVO uploadFtp(FtpOrderDataReq req){
        //获取需要导入的总记录数
        int length = orderManager.queryFtpOrderDataRespListCount(req);

        StringBuffer content = new StringBuffer();


        if(length>0){
            int pageCount = (length + FtpOrderDataConsts.BATCH_COUNT - 1) / FtpOrderDataConsts.BATCH_COUNT;
            for (int page = 1; page <= pageCount; page++) {
                int start = 1 + (FtpOrderDataConsts.BATCH_COUNT * (page - 1));
                int end = FtpOrderDataConsts.BATCH_COUNT * page > length ? length : FtpOrderDataConsts.BATCH_COUNT * page;
                try {


                } catch (RuntimeException e) {

                } catch (Exception e) {


                }
            }
        }
        List<FtpOrderDataResp> dataList = this.queryFtpOrderDataRespList(req);

        return null;
    }
    private Map<String, MerchantDTO> getMerchantDTOMap(List<String> parCrmOrgIdList) {
        Map<String, MerchantDTO> marchantMap = new HashMap<String, MerchantDTO>();
        if (null == parCrmOrgIdList || parCrmOrgIdList.isEmpty()) {
            return marchantMap;
        }
        MerchantListReq req = new MerchantListReq();
        req.setParCrmOrgIdList(parCrmOrgIdList);

        ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(req);
        if (null != listResultVO && listResultVO.isSuccess() && null != listResultVO.getResultData()) {
            List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
            for (MerchantDTO merchantDTO : merchantDTOList) {
                String parCrmOrgId = merchantDTO.getParCrmOrgId();
                if (!org.springframework.util.StringUtils.isEmpty(parCrmOrgId)) {
                    marchantMap.put(parCrmOrgId, merchantDTO);
                }
            }
        }
        return marchantMap;

    }
    private void getFtpInfo(){
        env.getProperty(MarketingResConst.ZOP_APPSECRET);
    }
}