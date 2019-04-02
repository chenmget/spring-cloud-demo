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
import com.iwhalecloud.retail.order2b.util.FtpUtils;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.QueryInvoiceByMerchantIdsReq;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;
import com.iwhalecloud.retail.partner.service.InvoiceService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.ArithUtil;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.dto.PublicDictDTO;
import com.iwhalecloud.retail.system.service.PublicDictService;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    @Reference
    private InvoiceService invoiceService;
    @Reference
    private UserService userService;


    @Reference
    private MerchantService merchantService;
    @Reference
    private PublicDictService publicDictService;

    @Autowired
    private Environment env;


    private List<FtpOrderDataResp> queryFtpOrderDataRespList(FtpOrderDataReq req,
                                                             Map<String, MerchantDTO> merchantDTOMap,
                                                             Map<String, InvoicePageResp> invoiceMap, Map<String, InvoicePageResp> userMap) {

        Page<FtpOrderDataResp> orderListPage = orderManager.queryFtpOrderDataRespList(req);
        if (orderListPage != null && orderListPage.getRecords() != null && !orderListPage.getRecords().isEmpty()) {
            List<String> merchantIdList = new ArrayList<String>();
            List<FtpOrderDataResp> orderList = orderListPage.getRecords();
            for (FtpOrderDataResp orderDataResp : orderList) {
                String custId = orderDataResp.getCustId();
                if (!merchantDTOMap.containsKey(custId)) {
                    merchantIdList.add(custId);
                }
            }
            //获取商家
            this.getMerchantDTOMap(merchantDTOMap, userMap, merchantIdList);
            //获取发票
            this.getInvoiceMap(invoiceMap, merchantIdList);
            //获取首单时间(月份)
            String fstTransDate = orderManager.getFstTransDate();
            fstTransDate = DateUtils.dateToStr(DateUtils.strToUtilDate(fstTransDate), FtpOrderDataConsts.DAY_FOR_YEAR_MONTH2);
            for (FtpOrderDataResp orderDataResp : orderList) {
                //商家ID
                String custId = orderDataResp.getCustId();
                InvoicePageResp invoice = invoiceMap.get(custId);
                MerchantDTO merchant = merchantDTOMap.get(custId);
                //客户营业执照号:根据custId从PAR_INVOICE表获取
                String cerId = invoice != null ? invoice.getBusiLicenceCode() : "";
                orderDataResp.setCerId(StringUtils.defaultString(cerId));
                //经营省份区域编码provinceNo--写死湖南省
                orderDataResp.setProvinceNo(FtpOrderDataConsts.PROVINCE_NO);
                //营业执照失效日期:根据custId从PAR_INVOICE表获取
                String bizLicExprYrMon = null;
                if (invoice != null && invoice.getBusiLicenceExpDate() != null) {
                    bizLicExprYrMon = DateUtils.dateToStr(invoice.getBusiLicenceExpDate(), FtpOrderDataConsts.DAY_FOR_YEAR_MONTH2);
                }
                orderDataResp.setBizLicExprYrMon(StringUtils.defaultString(bizLicExprYrMon));

                //首笔订单发生日期:首单
                orderDataResp.setFstTransYrMon(StringUtils.defaultString(fstTransDate));

                //平台注册日期:商家表的创建时间,取不到，目前填空
                String regYrMon = "";
                orderDataResp.setRegYrMon(StringUtils.defaultString(regYrMon));

                //交易月份:订单表：创建时间:数据库获取

                //订单金额（ORDER_AMOUNT）
                orderDataResp.setTransAmt(ArithUtil.fenToYuan(orderDataResp.getTransAmt()));

                //GoodsCnt--订单项表的数量num字段：数据库获取
                //旧平台客户编号:oldCustId--商家表的
                String oldCustId = merchant==null?"":merchant.getOldCustId();
                orderDataResp.setOldCustId(StringUtils.defaultString(oldCustId));

            }
            return orderList;
        }

        return null;
    }

    @Override
    public ResultVO uploadFtpForTask() {
        //当前时间
        Date date = new Date();
        String day = DateUtils.dateToStr(date, FtpOrderDataConsts.DAY_FOR_DAY);
        String startDate = "";
        String endDate = "";
        String uploadPath = "";
        if (FtpOrderDataConsts.FIFTEEN.equals(day)) {
            uploadPath = getDataPathMid();
            //查询1-15的数据
            startDate = DateUtils.dateToStr(date, FtpOrderDataConsts.DAY_FOR_YEAR_MONTH) + "-" + FtpOrderDataConsts.ONE;
            endDate = DateUtils.dateToStr(date, FtpOrderDataConsts.DAY_FOR_YEAR_DAY);
        } else if (FtpOrderDataConsts.ONE.equals(day)) {
            uploadPath = this.getDataPathAgo();
            //查询上个月16-月末的数据
            startDate = DateUtils.dateToStr(DateUtils.getLastMonth(date), FtpOrderDataConsts.DAY_FOR_YEAR_MONTH) + "-" + FtpOrderDataConsts.SIXTEEN;
            endDate = DateUtils.getMonthLastDay(DateUtils.getLastMonth(date));
        } else {
            return ResultVO.error("日期错误,不进行上传");
        }
        if(StringUtils.isEmpty(uploadPath)){
            return ResultVO.error("配置路径未配置");
        }
        FtpOrderDataReq req = new FtpOrderDataReq();
        req.setEndDate(endDate);
        req.setStartDate(startDate);
        req.setFtpPath(uploadPath);
        return this.uploadFtp(req);
    }

    @Override
    public ResultVO uploadFtp(FtpOrderDataReq req) {
        FtpUtils.Ftp ftpInfo = getFtp();
        if (ftpInfo == null) {
            return ResultVO.error("获取ftp配置错误");
        }
        ftpInfo.setPath(req.getFtpPath());
        //获取需要导入的总记录数
        int length = orderManager.queryFtpOrderDataRespListCount(req);

        StringBuffer content = new StringBuffer();

        if (length > 0) {
            Map<String, MerchantDTO> merchantDTOMap = new HashMap<String, MerchantDTO>();
            Map<String, InvoicePageResp> invoiceMap = new HashMap<String, InvoicePageResp>();
            Map<String, InvoicePageResp> userMap = new HashMap<String, InvoicePageResp>();
            int pageCount = (length + FtpOrderDataConsts.BATCH_COUNT - 1) / FtpOrderDataConsts.BATCH_COUNT;
            for (int page = 1; page <= pageCount; page++) {

                FtpOrderDataReq orderDataReq = new FtpOrderDataReq();
                orderDataReq.setEndDate(req.getEndDate());
                orderDataReq.setStartDate(req.getStartDate());
                orderDataReq.setPageNo(page);
                orderDataReq.setPageSize(FtpOrderDataConsts.BATCH_COUNT);
                List<FtpOrderDataResp> dataList = this.queryFtpOrderDataRespList(orderDataReq, merchantDTOMap, userMap, invoiceMap);

                if(dataList!=null){
                    for (FtpOrderDataResp orderDataResp : dataList) {
                        content.append(orderDataResp.getCustId()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getCerId()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getProvinceNo()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getBizLicExprYrMon()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getFstTransYrMon()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getRegYrMon()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getYrMon()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getTransAmt()).append(FtpOrderDataConsts.SPLIT);
                        content.append(length).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getGoodsCnt()).append(FtpOrderDataConsts.SPLIT);
                        content.append(orderDataResp.getOldCustId());
                        if(page!=pageCount){
                            content.append(FtpOrderDataConsts.END);
                        }
                    }
                }


            }
        }
        String startDate = req.getStartDate();

        String month = startDate.substring(0, 8).replaceAll("-", "");

        String fileName = FtpOrderDataConsts.FILE_NAME_PRE + month + FtpOrderDataConsts.FILE_TYPE;
        try {
            FtpUtils.upload(ftpInfo,content.toString(),fileName);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("文件上传ftp失败:",e);
            return ResultVO.error("文件上传ftp失败");
        }


    }

    private FtpUtils.Ftp getFtp() {
        FtpUtils.Ftp ftpInfo = null;
        List<PublicDictDTO> publicDictDTOS = publicDictService.queryPublicDictListByType(FtpOrderDataConsts.DICT_TYPE);
        if (CollectionUtils.isEmpty(publicDictDTOS)) {
            return ftpInfo;
        }
        ftpInfo = new FtpUtils.Ftp();
        for (PublicDictDTO publicDictDTO : publicDictDTOS) {
            if (FtpOrderDataConsts.IP.equals(publicDictDTO.getPkey())) {
                ftpInfo.setIpAddr(publicDictDTO.getPname());
            } else if (FtpOrderDataConsts.PORT.equals(publicDictDTO.getPkey())) {
                ftpInfo.setPort(Integer.valueOf(publicDictDTO.getPname()));
            } else if (FtpOrderDataConsts.USERNAME.equals(publicDictDTO.getPkey())) {
                ftpInfo.setUserName(publicDictDTO.getPname());
            } else if (FtpOrderDataConsts.PASSWORD.equals(publicDictDTO.getPkey())) {
                ftpInfo.setPwd(publicDictDTO.getPname());
            } else if (FtpOrderDataConsts.FILEPATH.equals(publicDictDTO.getPkey())) {
                ftpInfo.setPath(publicDictDTO.getPname());
            }
        }

        return ftpInfo;
    }

    private void getMerchantDTOMap(Map<String, MerchantDTO> merchantDTOMap, Map<String, InvoicePageResp> userMap, List<String> idList) {
        if (null == idList || idList.isEmpty()) {
            return;
        }

        MerchantListReq req = new MerchantListReq();
        req.setMerchantIdList(idList);
        req.setNeedOtherTableFields(false);
        ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(req);
        if (null != listResultVO && listResultVO.isSuccess() && null != listResultVO.getResultData()) {
            List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
            for (MerchantDTO merchantDTO : merchantDTOList) {
                String merchantId = merchantDTO.getMerchantId();
                if (!org.springframework.util.StringUtils.isEmpty(merchantId)) {
                    merchantDTOMap.put(merchantId, merchantDTO);

                }
            }
        }


    }

    private void getInvoiceMap(Map<String, InvoicePageResp> invoiceMap, List<String> merchantIdList) {
        if (null == merchantIdList || merchantIdList.isEmpty()) {
            return;
        }

        QueryInvoiceByMerchantIdsReq req = new QueryInvoiceByMerchantIdsReq();
        req.setMerchantIdList(merchantIdList);
        req.setInvoiceType(ParInvoiceConst.InvoiceType.SPECIAL_VAT_INVOICE.getCode());
        ResultVO<List<InvoicePageResp>> listResultVO = invoiceService.queryInvoiceByMerchantIds(req);
        if (null != listResultVO && listResultVO.isSuccess() && null != listResultVO.getResultData()) {
            List<InvoicePageResp> invoiceList = listResultVO.getResultData();
            for (InvoicePageResp invoice : invoiceList) {
                invoiceMap.put(invoice.getMerchantId(), invoice);

            }
        }


    }

    public static void main(String[] args) throws Exception{
        FtpUtils.Ftp ftpInfo = new FtpUtils.Ftp();
        ftpInfo.setIpAddr("134.176.97.50");
        ftpInfo.setPort(21);
        ftpInfo.setUserName("fdfs");
        ftpInfo.setPwd("cl3AO&UXBdip");
        ftpInfo.setPath("/tmp/order/ago");

        String content = "dddddd中文";
        String fileName = "test.txt";
        FtpUtils.upload(ftpInfo,content.toString(),fileName);
    }
    public String getDataPathAgo() {
        return env.getProperty(FtpOrderDataConsts.DATA_PATH_AGO);
    }


    public String getDataPathMid() {
        return env.getProperty(FtpOrderDataConsts.DATA_PATH_MID);
    }
}
