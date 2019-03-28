package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.*;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.entity.BusinessEntity;
import com.iwhalecloud.retail.partner.entity.BusinessEntityTemp;
import com.iwhalecloud.retail.partner.entity.Merchant;
import com.iwhalecloud.retail.partner.entity.MerchantTemp;
import com.iwhalecloud.retail.partner.manager.BusinessEntityManager;
import com.iwhalecloud.retail.partner.manager.BusinessEntityTempManager;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.manager.MerchantTempManager;
import com.iwhalecloud.retail.partner.model.FtpDTO;
import com.iwhalecloud.retail.partner.service.ChannelViewSyncService;
import com.iwhalecloud.retail.partner.utils.FtpUtils;
import com.iwhalecloud.retail.system.common.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 渠道视图商家信息同步实现类
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月07日
 */
@Service
@Slf4j
public class ChannelViewSyncServiceImpl implements ChannelViewSyncService {

    private static final String PRE_BUSINESS_ENTITY_FILENAME = "BUSINESS_ENTITY_";
    private static final String PRE_MERCHANT_FILENAME = "MERCHANT_";
    private static final String UN_DEAL = "0";
    private static final String DEAL_SUCCESS = "1";
    private static final String DEAL_ERROR = "2";

    @Value("${ftp.channel.ip}")
    private String ip;
    @Value("${ftp.channel.username}")
    private String username;
    @Value("${ftp.channel.password}")
    private String password;
    @Value("${ftp.channel.port}")
    private Integer port;
    @Value("${ftp.channel.filePath}")
    private String filePath;

    private BusinessEntityManager businessEntityManager;
    private MerchantManager merchantManager;
    private BusinessEntityTempManager businessEntityTempManager;
    private MerchantTempManager merchantTempManager;

    @Autowired
    public ChannelViewSyncServiceImpl(BusinessEntityManager businessEntityManager, MerchantManager merchantManager, BusinessEntityTempManager businessEntityTempManager, MerchantTempManager merchantTempManager) {
        this.businessEntityManager = businessEntityManager;
        this.merchantManager = merchantManager;
        this.businessEntityTempManager = businessEntityTempManager;
        this.merchantTempManager = merchantTempManager;
    }

    @Override
    public ResultVO deleteBusinessEntityTempData() {
        log.info("---->> ChannelViewSyncServiceImpl.deleteBusinessEntityTempData start......");
        businessEntityTempManager.deleteBusinessEntityTempData();
        log.info("---->> ChannelViewSyncServiceImpl.deleteBusinessEntityTempData end......");
        return ResultVO.success();
    }

    @Override
    public ResultVO deleteMerchantTempData() {
        log.info("---->> ChannelViewSyncServiceImpl.deleteMerchantTempData start......");
        merchantTempManager.deleteMerchantTempData();
        log.info("---->> ChannelViewSyncServiceImpl.deleteMerchantTempData end......");
        return ResultVO.success();
    }

    @Override
    public ResultVO syncBusinessEntity() {
        log.info("----->> ChannelViewSyncServiceImpl.syncBusinessEntity start......");
        FtpDTO ftpDTO = this.generatorFtpDTO(PRE_BUSINESS_ENTITY_FILENAME);
        log.info("=====》开始获取经营主体ftp文件: {}", ftpDTO.getFileName());
        try {
            ResultVO<List<String>> resultVO = FtpUtils.readFile(ftpDTO);
            if (!resultVO.isSuccess()) {
                log.error("读取文件失败---" + resultVO.getResultMsg());
                return resultVO;
            }
            log.info("====》获取经营主体ftp文件结束");
            List<String> resultData = resultVO.getResultData();
            // 总共几个批次
            int total = (int) Math.ceil(resultData.size() / 1000.0);
            List<String> subList;
            for (int i = 0; i < total; i++) {
                if (i == total - 1) {
                    subList = resultData.subList(i * 1000, resultData.size());
                } else {
                    subList = resultData.subList(i * 1000, (i + 1) * 1000);
                }
                List<BusinessEntityTemp> list = new ArrayList<>();
                for (String txtStr : subList) {
                    BusinessEntityTemp businessEntityTemp = new BusinessEntityTemp();
                    businessEntityTemp.setTxtStr(txtStr);
                    businessEntityTemp.setPatch(String.valueOf(i + 1));
                    businessEntityTemp.setStatus(UN_DEAL);
                    businessEntityTemp.setCreateDate(new Date());
                    list.add(businessEntityTemp);
                }
                // 插入临时表
                businessEntityTempManager.saveBatch(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取经营主体ftp文件出错或插入临时表有错");
            log.error(e.getMessage());
        }
        log.info("----->> ChannelViewSyncServiceImpl.syncBusinessEntity end......");
        return ResultVO.success();
    }

    @Override
    public ResultVO syncMerchant() {
        log.info("----->> ChannelViewSyncServiceImpl.syncMerchant start......");
        FtpDTO ftpDTO = this.generatorFtpDTO(PRE_MERCHANT_FILENAME);
        log.info("=====》开始获取商家ftp文件: {}", ftpDTO.getFileName());
        try {
            ResultVO<List<String>> resultVO = FtpUtils.readFile(ftpDTO);
            if (!resultVO.isSuccess()) {
                return resultVO;
            }
            log.info("====》获取商家ftp文件结束");
            List<String> resultData = resultVO.getResultData();
            // 总共几个批次
            int total = (int) Math.ceil(resultData.size() / 1000.0);
            List<String> subList;
            for (int i = 0; i < total; i++) {
                if (i == total - 1) {
                    subList = resultData.subList(i * 1000, resultData.size());
                } else {
                    subList = resultData.subList(i * 1000, (i + 1) * 1000);
                }
                List<MerchantTemp> list = new ArrayList<>();
                for (String txtStr : subList) {
                    MerchantTemp merchantTemp = new MerchantTemp();
                    merchantTemp.setTxtStr(txtStr);
                    merchantTemp.setPatch(String.valueOf(i + 1));
                    merchantTemp.setStatus(UN_DEAL);
                    merchantTemp.setCreateDate(new Date());
                    list.add(merchantTemp);
                }
                // 插入临时表
                merchantTempManager.saveBatch(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取商家ftp文件出错或插入临时表有错");
            log.error(e.getMessage());
        }
        log.info("----->> ChannelViewSyncServiceImpl.syncMerchant end......");
        return ResultVO.success();
    }

    @Override
    public void dealBusinessEntityData() {
        log.info("----->> ChannelViewSyncServiceImpl.dealBusinessEntityData start......");
        // 批次
        int patch = 1;
        while (patch > 0) {
            log.info("------>> 经营主体处理批次：" + patch);
            // 根据批次查询临时表中未处理的数据
            BusinessEntityTempDTO dto = new BusinessEntityTempDTO();
            dto.setStatus(UN_DEAL);
            dto.setPatch(String.valueOf(patch));
            List<BusinessEntityTemp> businessEntityTemps = businessEntityTempManager.queryBusinessEntityTemp(dto);

            if (!CollectionUtils.isEmpty(businessEntityTemps)) {
                // 批次递增
                patch++;
                for (BusinessEntityTemp entityTemp : businessEntityTemps) {
                    String txtStr = entityTemp.getTxtStr();
                    String[] strings = txtStr.split("\\$#\\$", -1);
                    BusinessEntitySyncDTO entitySyncDTO = new BusinessEntitySyncDTO();
                    try {
                        // 根据行数据获取经营主体对象
                        entitySyncDTO = this.setParam(BusinessEntitySyncDTO.class, strings);
                        // 查询正式表是否有数据
                        BusinessEntityGetReq req = new BusinessEntityGetReq();
                        req.setBusinessEntityCode(entitySyncDTO.getBusinessEntityCode());
                        BusinessEntity businessEntity = businessEntityManager.getBusinessEntity(req);
                        int uptFlag = 0;
                        if (Objects.isNull(businessEntity)) {
                            // 无数据，新增
                            BusinessEntity entity = new BusinessEntity();
                            BeanUtils.copyProperties(entitySyncDTO, entity);
                            uptFlag = businessEntityManager.insertBusinessEntity(entity);
                        } else {
                            // 有数据，更新
                            BusinessEntityDTO entityDTO = new BusinessEntityDTO();
                            BeanUtils.copyProperties(entitySyncDTO, entityDTO);
                            uptFlag = businessEntityManager.updateBusinessEntityByCode(entityDTO);
                        }
                        if (uptFlag > 0) {
                            // 更新临时表为处理成功
                            this.updateEntityTemp(entityTemp.getId(), DEAL_SUCCESS, "success");
                        } else {
                            // 更新临时表为处理失败
                            this.updateEntityTemp(entityTemp.getId(), DEAL_ERROR, "updateFailure");
                        }
                    } catch (Exception e) {
                        log.error("经营主体同步失败: {}", entitySyncDTO.getBusinessEntityCode());
                        // 更新临时表为处理失败
                        log.error(e.getMessage());
                        this.updateEntityTemp(entityTemp.getId(), DEAL_ERROR, e.getMessage());
                    }
                }
            } else {
                // 批次归零，终止下一个批次执行
                patch = 0;
            }
        }
        log.info("----->> ChannelViewSyncServiceImpl.dealBusinessEntityData end......");
    }

    @Override
    public void dealMerchantData() {
        log.info("----->> ChannelViewSyncServiceImpl.dealMerchantData start......");
        // 批次
        int patch = 1;
        while (patch > 0) {
            log.info("------>> 商家信息处理批次：" + patch);
            //  3.1. 根据批次查询临时表中未处理的数据
            MerchantTempDTO dto = new MerchantTempDTO();
            dto.setStatus(UN_DEAL);
            dto.setPatch(String.valueOf(patch));
            List<MerchantTemp> merchantTempDTOS = merchantTempManager.queryMerchantTemp(dto);

            if (!CollectionUtils.isEmpty(merchantTempDTOS)) {
                // 批次递增
                patch++;
                for (MerchantTemp merchantTemp : merchantTempDTOS) {
                    String txtStr = merchantTemp.getTxtStr();
                    String[] strings = txtStr.split("\\$#\\$", -1);
                    MerchantSyncDTO merchantSyncDTO = new MerchantSyncDTO();
                    try {
                        // 根据行数据获取经营主体对象
                        merchantSyncDTO = this.setParam(MerchantSyncDTO.class, strings);
                        // 查询正式表是否有数据
                        MerchantGetReq req = new MerchantGetReq();
                        req.setMerchantCode(merchantSyncDTO.getMerchantCode());
                        Merchant merchant = merchantManager.getMerchant(req);
                        int uptFlag = 0;
                        if (Objects.isNull(merchant)) {
                            // 无数据，新增
                            Merchant entity = new Merchant();
                            BeanUtils.copyProperties(merchantSyncDTO, entity);
                            uptFlag = merchantManager.insertMerchant(entity);
                        } else {
                            // 有数据，更新
                            MerchantDetailDTO merchantDetailDTO = new MerchantDetailDTO();
                            BeanUtils.copyProperties(merchantSyncDTO, merchantDetailDTO);
                            uptFlag = merchantManager.updateMerchantByCode(merchantDetailDTO);
                        }
                        if (uptFlag > 0) {
                            // 更新临时表为处理成功
                            this.updateMerchantTemp(merchantTemp.getId(), DEAL_SUCCESS ,"success");
                        } else {
                            // 更新临时表为处理失败
                            this.updateMerchantTemp(merchantTemp.getId(), DEAL_ERROR, "updateFailure");
                        }
                    } catch (Exception e) {
                        log.error("商家同步失败: {}", merchantSyncDTO.getMerchantCode());
                        // 更新临时表为处理失败
                        log.error(e.getMessage());
                        this.updateMerchantTemp(merchantTemp.getId(), DEAL_ERROR, e.getCause().getMessage());
                    }
                }
            } else {
                // 批次归零，终止下一个批次执行
                patch = 0;
            }
        }
        log.info("----->> ChannelViewSyncServiceImpl.dealMerchantData end......");
    }

    /**
     * 获取ftp实体对象
     *
     * @return FtpDTO
     */
    private FtpDTO generatorFtpDTO(String preFileName) {
        FtpDTO ftpDTO = new FtpDTO();
        ftpDTO.setIp(ip);
        ftpDTO.setUserName(username);
        ftpDTO.setPassword(password);
        ftpDTO.setPort(port);
        ftpDTO.setFilePath(filePath);
        String fixName = DateUtils.getYesterdayTimeForStr();
        String fileName = preFileName + fixName + ".txt";
        ftpDTO.setFileName(fileName);
        return ftpDTO;
    }


    /**
     * 更新经营主体临时表处理结果
     *
     * @param id     临时表的id
     * @param status 状态
     * @param note   备注
     */
    private void updateEntityTemp(String id, String status, String note) {
        BusinessEntityTemp entityTemp = new BusinessEntityTemp();
        entityTemp.setId(id);
        entityTemp.setStatus(status);
        entityTemp.setHandleDate(DateUtils.currentSysTimeForStr());
        entityTemp.setNote(note);
        businessEntityTempManager.update(entityTemp);
    }

    /**
     * 更新商家临时表处理结果
     *
     * @param id     临时表的id
     * @param status 状态
     * @param note   备注
     */
    private void updateMerchantTemp(String id, String status, String note) {
        MerchantTemp merchantTemp = new MerchantTemp();
        merchantTemp.setId(id);
        merchantTemp.setStatus(status);
        merchantTemp.setHandleDate(DateUtils.currentSysTimeForStr());
        merchantTemp.setNote(note);
        merchantTempManager.update(merchantTemp);
    }

    /**
     * 将数组对象赋值给指定对象
     *
     * @param clazz clazz
     * @param args  数组对象
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T setParam(Class<T> clazz, Object[] args) throws Exception {
        if (clazz == null || args == null) {
            throw new IllegalArgumentException();
        }
        T t = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length > args.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(t, args[i]);
        }
        return t;
    }
}
