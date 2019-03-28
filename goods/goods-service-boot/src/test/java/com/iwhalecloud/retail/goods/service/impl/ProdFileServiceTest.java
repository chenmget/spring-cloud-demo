package com.iwhalecloud.retail.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.common.FileConst;
import com.iwhalecloud.retail.goods.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods.manager.ProdFileManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Z
 * @date 2018/11/28
 */

@SpringBootTest(classes = GoodsServiceApplication.class)
@RunWith(SpringRunner.class)
public class ProdFileServiceTest {

    @Resource
    private ProdFileManager prodFileManager;

    @Test
    public void addGoodsImage(){
        prodFileManager.addGoodsImage("4", FileConst.SubType.DETAIL_SUB,"group1/M00/00/05/Ci0vWVv-RvWAHhfOAAAdSa8v6Lk551.png");
        int size = prodFileManager.addGoodsImage("4", FileConst.SubType.ROLL_VIDIO_SUB,"group1/M00/00/05/Ci0vWVv-RvWAHhfOAAAdSa8v6Lk551.mp3");
        Assert.assertTrue(size==1);
    }

    @Test
    public void queryGoodsImageByGoodsId() {
        List<ProdFileDTO> dtos =  prodFileManager.queryGoodsImage("4");
        System.out.println(JSON.toJSONString(dtos));
    }

    @Test
    public void queryGoodsImageBySubType() {
        List<ProdFileDTO> dtos =  prodFileManager.queryGoodsImage("4", FileConst.SubType.ROLL_VIDIO_SUB);
        System.out.println(JSON.toJSONString(dtos));
    }

    @Test
    public void deleteById() {
        int size = prodFileManager.deleteById("1067750914191314945");
        Assert.assertTrue(size==1);
    }

    @Test
    public void deleteByGoodsSubType() {
        int size = prodFileManager.deleteByGoodsSubType("1", FileConst.SubType.DEFAULT_SUB);
        Assert.assertTrue(size>=1);
    }

    @Test
    public void deleteByGoodsId() {
        int size = prodFileManager.deleteByGoodsId("1");
        Assert.assertTrue(size>=1);
    }
}
