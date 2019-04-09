package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.MerchantPageReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.WarehouseServiceApplication;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreObjRelDTO;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreObjRelManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WarehouseServiceApplication.class)
@Slf4j
public class ResouceStoreServiceImplTest {

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Autowired
    private ResouceStoreObjRelManager resouceStoreObjRelManager;

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Test
    public void getMerchantByStore() {
        ResultVO vo = resouceStoreService.getMerchantByStore("1");
        Assert.assertTrue(vo.isSuccess());
    }

    @Test
    public void pageMerchantAllocateStore() {
//        Page<ResouceStoreDTO> page = resouceStoreService.pageMerchantAllocateStore("111", "");
//        Assert.assertTrue(null != page && page.getRecords().size()>0);
    }

    @Test
    /**
     * 仓库类型：集采、备机、终端；
     * 1个厂商3个库：终端库、备机库、集采库；
     * 1个供应商2个库：终端库、集采库；
     * 1个零售商3个库：终端库、备机库、集采库
     */
    public void initStore(){
        for (int i= 0; i < 10000*100; i = i+100) {
            String remark = "20190327_init_data";
            MerchantPageReq pageReq = new MerchantPageReq();
            pageReq.setPageNo(i/100);
            pageReq.setPageSize(100);
            ResultVO<Page<MerchantPageResp>> pageResultVO = merchantService.pageMerchant(pageReq);
            for (MerchantPageResp dto : pageResultVO.getResultData().getRecords()) {
                String[] libs = new String[]{ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode()/*,ResourceConst.STORE_TYPE.STORE_TYPE_2102.getCode(),ResourceConst.STORE_TYPE.STORE_TYPE_2103.getCode()*/};
                List<String> mktResStoreIds = new ArrayList<>();
                for (String lib : libs) {
                    if (PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(dto.getMerchantType())) {
                        continue;
                    }
                    String mktResStoreId = resourceInstManager.getPrimaryKey();
                    mktResStoreIds.add(mktResStoreId);
                    ResouceStoreDTO resouceStoreDTO = new ResouceStoreDTO();
                    resouceStoreDTO.setMktResStoreId(mktResStoreId);
                    resouceStoreDTO.setMerchantCode(dto.getMerchantId());
                    resouceStoreDTO.setMerchantName(dto.getMerchantName());

                    resouceStoreDTO.setMktResStoreName(dto.getMerchantName() + "-" + ResourceConst.STORE_SUB_TYPE.getStoreSubTypeName(lib));
                    resouceStoreDTO.setMktResStoreNbr("NBR_" + mktResStoreId);

                    resouceStoreDTO.setMerchantId(dto.getMerchantId());
                    resouceStoreDTO.setMerchantName(dto.getMerchantName());
                    resouceStoreDTO.setMerchantCode(dto.getMerchantCode());
                    //                resouceStoreDTO.setBusinessEntityCode(dto.getBusinessEntityCode());
                    resouceStoreDTO.setBusinessEntityName(dto.getBusinessEntityName());
                    // PUB-C-0001 1000	有效 1100	无效 1200	未生效
                    resouceStoreDTO.setStatusCd("1000");
                    // RES-0001 2101  终端库 2102  集采库 2103  备机库
                    resouceStoreDTO.setStoreType(lib);
                    //                resouceStoreDTO.setStoreSubType("1101");
                    // 记录号码回收期限：必须输入（天数，默认90天）
                    resouceStoreDTO.setRecDay(90L);
                    // RES-C-0015 1000	本地网回收 1001	管理机构回收 1002	回收池回收 1003	回收池回收并回放
                    resouceStoreDTO.setRecType("1000");
                    // RES-C-0016 1000	省级库	全省仓库 1100	本地网库	本地网仓库 1200	本地网下级库	本地网以下级仓库
                    String storeGrade = "";
                    if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(dto.getMerchantType())) {
                        storeGrade = "1000";
                    } else if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(lib)) {
                        storeGrade = "1100";
                    } else {
                        storeGrade = "1200";
                    }
                    resouceStoreDTO.setStoreGrade(storeGrade);

                    resouceStoreDTO.setParStoreId("-1");
                    resouceStoreDTO.setRecStoreId("-1");

                    resouceStoreDTO.setRegionId(dto.getCity());
                    resouceStoreDTO.setLanId(dto.getLanId());
                    resouceStoreDTO.setRemark(remark);
                    resouceStoreDTO.setEffDate(new Date());
                    resouceStoreDTO.setExpDate(new Date());
                    resouceStoreDTO.setCreateStaff("1");
                    resouceStoreDTO.setCreateDate(new Date());
                    resouceStoreDTO.setUpdateDate(new Date());
                    resouceStoreDTO.setStatusDate(new Date());
                    resouceStoreManager.saveStore(resouceStoreDTO);
                }

                for (String mktResStoreId : mktResStoreIds) {
                    ResouceStoreObjRelDTO resouceStoreObjRelDTO = new ResouceStoreObjRelDTO();
                    resouceStoreObjRelDTO.setObjId(dto.getMerchantId());
                    resouceStoreObjRelDTO.setObjType(dto.getMerchantType());
                    resouceStoreObjRelDTO.setCreateDate(new Date());
                    resouceStoreObjRelDTO.setMktResStoreId(mktResStoreId);
                    // PUB-C-0001 1000	有效 1100	无效 1200	未生效
                    resouceStoreObjRelDTO.setStatusCd("1000");
                    resouceStoreObjRelDTO.setRegionId(dto.getCity());
                    resouceStoreObjRelDTO.setRemark(remark);
                    resouceStoreObjRelDTO.setCreateDate(new Date());
                    resouceStoreObjRelDTO.setCreateStaff("1");
                    // PUB-C-0006 1	是 0	否
                    resouceStoreObjRelDTO.setIsDefault("1");
                    resouceStoreObjRelManager.saveStoreRel(resouceStoreObjRelDTO);
                }

            }
        }
    }

    @Test
    public void initStoredata() {
        resouceStoreService.initStoredata();
    }
}