package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.OmsServiceApplication;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdLIstResp;
import com.iwhalecloud.retail.oms.service.ContentBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OmsServiceApplication.class)
public class ContentBaseServiceImplTest {
    @Autowired
    private ContentBaseService contentBaseService;

    @Test
    public void queryContentIdList() {
        List<String> contentIdList = new ArrayList<String>();
        contentIdList.add("181110248800574955");
        contentIdList.add("181112776600575340");
        ContentIdLIstResp contentIdLIstResp = contentBaseService.queryContentIdList(contentIdList);
        System.out.println(contentIdLIstResp);
    }
}
