package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.PromoServiceApplication;
import com.iwhalecloud.retail.promo.dto.req.AddActSupReq;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;
import com.iwhalecloud.retail.promo.service.ActSupRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhou.zc
 * @date 2019年03月21日
 * @Description:前置补贴补录测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromoServiceApplication.class)
public class ActSupServiceTest {

    @Reference
    ActSupRecordService actSupRecordService;

    @Test
    public void addActSupRecord(){
        AddActSupReq addActSupReq = new AddActSupReq();
        addActSupReq.setMarketingActivityId("100002743");
        String test = "20190315421477|6477573774445\n2019031542147|6477573774445\n20190315421477|647773774445\n2015421477|6477573774445\n20190315421477|64s77573774445\n20190315421477|647757377s4445\n2019031542s1477|6477573774445\n2019031477|6477573774445\n20190315421477|64775737445\n20190315421477|6477573774445|sss\n20190315421477|647757883774445\n20190315421477|6477573sss774445\n20190315421477|6477573774ss445\n20190315421477|64775737sss74445\n20190315421477|477573774445\n20190315421477|64775774445\n20190315421477|64775553774445\n2019031421477|677573774445\n201905421477|6477573s74445\n20190315421477|647757355774445\n20190315428881477|6477573774445\n20190315421477|6477555573774445\n20190315421477|647sss7573774445\n201903154s21477|647757s3774445\n";
        addActSupReq.setRecords(test);
        actSupRecordService.addActSup(addActSupReq);
    }

    @Test
    public void queryActSupRecord(){
        QueryActSupRecordReq queryActSupRecordReq = new QueryActSupRecordReq();
        queryActSupRecordReq.setPageNo(1);
        queryActSupRecordReq.setPageSize(10);
        ResultVO<Page<ActSupRecodeListResp>> pageResultVO = actSupRecordService.queryActSupRecord(queryActSupRecordReq);
        System.out.println(pageResultVO.getResultData().getRecords());
    }

    @Test
    public void deleteActSupRecord(){
        System.out.println(actSupRecordService.deleteActSupRecord("100002862"));
    }

}
