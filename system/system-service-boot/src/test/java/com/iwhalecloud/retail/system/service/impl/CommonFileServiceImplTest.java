package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.SystemServiceApplication;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;
import com.iwhalecloud.retail.system.service.CommonFileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class CommonFileServiceImplTest {

    @Reference
    private CommonFileService commonFileService;

    @Test
    public void saveCommonFile() {
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        commonFileDTO.setFileType(SystemConst.FileType.IMG_FILE.getCode());
        commonFileDTO.setFileClass(SystemConst.FileClass.BUSINESS_LICENSE.getCode());
        commonFileDTO.setObjId("10104");
        commonFileDTO.setFileUrl("https://gy.ztesoft.com/group1/xx.jpg");
        commonFileDTO.setCreateDate(new Date());
        commonFileDTO.setCreateStaff("1");
        commonFileDTO.setStatusCd(SystemConst.StatusCdEnum.STATUS_CD_VALD.getCode());
        commonFileService.saveCommonFile(commonFileDTO);
    }

    @Test
    public void getCommonFile() {
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        commonFileDTO.setFileType(SystemConst.FileType.IMG_FILE.getCode());
        commonFileDTO.setFileClass(SystemConst.FileClass.BUSINESS_LICENSE.getCode());
        commonFileDTO.setObjId("10104");
        ResultVO<CommonFileDTO> resultVO = commonFileService.getCommonFile(commonFileDTO);
        log.info("resultVO:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void getCommonFileById() {
        ResultVO<CommonFileDTO> resultVO = commonFileService.getCommonFileById("1132918208934498306");
        log.info("resultVO:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void getCommonFileByIds() {
        ResultVO<List<CommonFileDTO>>  resultVO = commonFileService.getCommonFileByIds(new String[]{"1132918208934498306"});
        log.info("resultVO:{}", JSON.toJSONString(resultVO));
    }

    @Test
    public void listCommonFileByIds() {
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        commonFileDTO.setFileType(SystemConst.FileType.IMG_FILE.getCode());
        commonFileDTO.setFileClass(SystemConst.FileClass.BUSINESS_LICENSE.getCode());
        commonFileDTO.setObjId("10101,10102,10104");
        ResultVO<List<CommonFileDTO>>  resultVO = commonFileService.listCommonFile(commonFileDTO);
        log.info("resultVO:{}", JSON.toJSONString(resultVO));
    }
}