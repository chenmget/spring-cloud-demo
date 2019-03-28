package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.dto.request.NoticeSaveReq;
import com.iwhalecloud.retail.system.service.MenuService;
import com.iwhalecloud.retail.system.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class MenuTest {
    @Autowired
    private MenuService menuService;

    @org.junit.Test
    public void save(){
        MenuDTO req = new MenuDTO();
        req.setMenuName("long");
        req.setMenuType("1");
        req.setPlatform("1");
        ResultVO result = menuService.saveMenu(req);
        System.out.print("结果：" + result.toString());
    }
}
