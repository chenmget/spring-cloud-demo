package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.req.FileGetReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class FileServiceImplTest {

    @Reference
    private FileService fileService;

    @Test
    public void getFile() {
        FileGetReq req  = new FileGetReq();
        req.setTargetId("1104786792535244801");
        req.setSubType(FileConst.SubType.THUMBNAILS_SUB.getType());
        req.setTargetType(FileConst.TargetType.GOODS_TARGET.getType());
        ResultVO<List<ProdFileDTO>> resultVO = fileService.getFile(req);
        System.out.println(resultVO);
    }
}