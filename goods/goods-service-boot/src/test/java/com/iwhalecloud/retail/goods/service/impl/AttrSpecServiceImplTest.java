package com.iwhalecloud.retail.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods.manager.AttrSpecManager;
import com.iwhalecloud.retail.goods.manager.ProdGoodsManager;
import com.iwhalecloud.retail.goods.service.AttrSpecService;
import com.iwhalecloud.retail.goods.utils.SpringContextUtils;
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
@SpringBootTest(classes = GoodsServiceApplication.class)
public class AttrSpecServiceImplTest {

    @Resource
    private AttrSpecService attrSpecService;

    @Resource
    private AttrSpecManager attrSpecManager;

    @Test
    public void testQueryAttrSpecList() {
        List<AttrSpecDTO> attrSpecDTOs = attrSpecService.queryAttrSpecList("1");
        System.out.println("属性规格查询结果-->" + JSON.toJSON(attrSpecDTOs));
    }

    @Test
    public void testGetBean() {
        ProdGoodsManager manager = (ProdGoodsManager)SpringContextUtils.getBean("prodGoodsManager");

        manager.deleteGoodsById("111");
    }

    @Test
    public void testQueryAttrSpecWithInstValue() {
        List<AttrSpecDTO> attrSpecDTOs = attrSpecManager.queryAttrSpecWithInstValue("1", "1067695247890182146");
        System.out.println(JSON.toJSON(attrSpecDTOs));
    }

}
