package com.iwhalecloud.retail.system;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.NoticePagePersonalReq;
import com.iwhalecloud.retail.system.dto.request.NoticePageReq;
import com.iwhalecloud.retail.system.dto.request.NoticeSaveReq;
import com.iwhalecloud.retail.system.dto.request.NoticeSelectUserPageReq;
import com.iwhalecloud.retail.system.service.NoticeService;
import com.iwhalecloud.retail.system.service.NoticeUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class NoticeTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeUserService noticeUserService;

    @org.junit.Test
    public void save(){
        System.out.println("-->" + IdWorker.getIdStr());
        NoticeSaveReq req = new NoticeSaveReq();
        req.setNoticeTitle("通知标题1"); // 账号
        req.setNoticeContent("通知内容1");
        req.setNoticeType("3");
        req.setPublishType("1");
        req.setCreateUserId("1");
        req.setStatus("1");

        ResultVO result = noticeService.saveNotice(req);
        System.out.print("结果：" + result.toString());
    }

    @org.junit.Test
    public void page(){
        NoticePageReq req = new NoticePageReq();
//        req.setPublishType("1");
        List<String> list =new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        req.setNoticeType(list);
//        req.setReadStatus("0");
        ResultVO result = noticeService.pageNotice(req);
        System.out.print("结果：" + result.toString());
    }

    @org.junit.Test
    public void pagePersonal(){
        NoticePagePersonalReq req = new NoticePagePersonalReq();
        req.setPublishType("1");
        req.setNoticeType("1");
        req.setUserId("1082191485979451394");
//        req.setReadStatus("0");
        ResultVO result = noticeService.pagePersonalNotice(req);
        System.out.print("结果：" + result.toString());
    }

    @org.junit.Test
    public void pageUser(){
        NoticeSelectUserPageReq req = new NoticeSelectUserPageReq();
        req.setPageNo(1);
        req.setPageSize(10);
        req.setLoginName("");
        ResultVO result = noticeUserService.pageSelectableUser(req);
        System.out.print("结果：" + result.toString());
    }
}
