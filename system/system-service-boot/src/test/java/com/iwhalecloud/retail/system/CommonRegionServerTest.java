package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.CommonRegionPageReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.utils.MessageSourceHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class CommonRegionServerTest {

    @Autowired
    private CommonRegionService commonRegionService;

    @Autowired
    private MessageSourceHandler errorMessageSourceHandler;

    @org.junit.Test
    public void listCommonRegion(){
        CommonRegionListReq req = new CommonRegionListReq();
        req.setParRegionId(null);

        ResultVO resultVO = commonRegionService.listCommonRegion(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @org.junit.Test
    public void page(){
        CommonRegionPageReq req = new CommonRegionPageReq();
//        req.setRegionIdList(Lists.newArrayList("731","732","733","734","735"));
        req.setPageNo(1);
        req.setPageSize(3);
        ResultVO resultVO = commonRegionService.pageCommonRegion(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @org.junit.Test
    public void commonRegion(){
        ResultVO resultVO = commonRegionService.getCommonRegionById("731");
        System.err.println("结果：" + resultVO.toString());
    }

    @Test
    public void i18nTest() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String errorMsg = errorMessageSourceHandler.getMessage("error");
        System.out.println("errorMsg=" + errorMsg);
    }

}
