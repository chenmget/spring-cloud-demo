package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.manager.AttrSpecManager;
import com.iwhalecloud.retail.goods2b.manager.GoodsManager;
import com.iwhalecloud.retail.goods2b.service.AttrSpecService;
import com.iwhalecloud.retail.goods2b.utils.SpringContextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Z
 * @date 2018/12/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class AttrSpecServiceImplTest {

    @Resource
    private AttrSpecService attrSpecService;

    @Resource
    private AttrSpecManager attrSpecManager;

    @Test
    public void testQueryAttrSpecList() {
        ResultVO<List<AttrSpecDTO>> attrSpecDTOs = attrSpecService.queryAttrSpecList("1");
        System.out.println("属性规格查询结果-->" + JSON.toJSON(attrSpecDTOs));
    }

    @Test
    public void testGetBean() {
        GoodsManager manager = (GoodsManager)SpringContextUtils.getBean("prodGoodsManager");

//        manager.deleteGoodsById("111");
    }

    @Test
    public void testQueryAttrSpecWithInstValue() {
        List<AttrSpecDTO> attrSpecDTOs = attrSpecManager.queryAttrSpecWithInstValue("1", "1067695247890182146");
        System.out.println(JSON.toJSON(attrSpecDTOs));
    }

    public void testAddAttrSpec(){

    }

}
