package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsQueryByTagsReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdTagDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagGetByIdReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class TagsServiceImplTest {

    @Autowired
    private TagsService tagsService;

    @Test
    public void getTagsByGoodsId() {
    }

//    @Test
//    public void listProdTags() {
//        ResultVO resultVO = tagsService.listProdTags();
//        System.out.println(resultVO.getResultData());
//    }

    @Test
    public void addProdTags() {
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setTagName("Test");
        tagsDTO.setTagType("Test");
        tagsDTO.setCreateStaff("Test");
        tagsDTO.setUpdateStaff("Test");
        ResultVO resultVO = tagsService.addProdTags(tagsDTO);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void editProdTags() {
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setTagId("1076032399677849602");
        tagsDTO.setTagName("TestEdit");
        tagsDTO.setTagType("TestEdit");
        tagsDTO.setCreateStaff("TestEdit");
        tagsDTO.setUpdateStaff("TestEdit");
        tagsService.editProdTags(tagsDTO);
    }

    @Test
    public void deleteProdTags() {
        String tagId = "1076032399677849602";
        ProdTagDeleteReq req = new ProdTagDeleteReq();
        req.setTagId(tagId);
        tagsService.deleteProdTags(req);
    }

    @Test
    public void getTagById() {
        String tagId = "1076032399677849602";
        TagGetByIdReq req = new TagGetByIdReq();
        req.setTagId(tagId);
        ResultVO<TagsDTO> resultVO = tagsService.getTagById(req);
        System.out.println(resultVO.getResultData().getTagName());
    }

    @Test
    public void queryGoodsListByTag(){
        GoodsQueryByTagsReq req = new GoodsQueryByTagsReq();
        req.setTagId("1100928688222396417");
        ResultVO<List<GoodsDetailDTO>> resultVO = tagsService.queryGoodsListByTag(req);
        System.out.println("resultVO=" + JSON.toJSON(resultVO));
    }
}