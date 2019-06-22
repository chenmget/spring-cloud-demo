package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import com.iwhalecloud.retail.goods.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupUpdateReq;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsGroupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/6
 **/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/goodsGroup")
public class GoodsGroupController {

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @Reference
    private GoodsGroupService goodsGroupService;

    @ApiOperation(value = "新增商品分组", notes = "新增商品分组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveGoodGroup", method = RequestMethod.POST)
    public ResultVO saveGoodGroup(@RequestBody GoodsGroupAddReq req){
        if(null == req){
            return ResultVO.error();
        }
        try {
            boolean flag = goodsGroupService.queryGoodsGroupNameIsContains(req.getGroupName());
            if(flag){
                return ResultVO.error("该商品组名称已存在");
            }
            int resp = goodsGroupService.insertGoodsGroup(req);
            if(resp>0){
                return ResultVO.success(resp);
            }
           return ResultVO.error();
        }catch (Exception e){
            log.error("GoodsGroupController saveGoodGroup Exception={} ",e);
            return ResultVO.error();
        }
    }

    @ApiOperation(value = "根据商品组ID查询商品分组", notes = "根据商品ID查询商品分组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listGoodsGroupByGroupId", method = RequestMethod.GET)
    public ResultVO listGoodsGroupByGroupId(@RequestParam String groupId){
        if(StringUtils.isEmpty(groupId)){
            log.info("GoodsGroupController listGoodGroup groupId={} ",groupId);
            return ResultVO.error("goodsId is empty");
        }
        try {
            GoodsGroupDTO goodsGroupDTO= goodsGroupService.listGoodsGroupByGroupId(groupId);
            if(null == goodsGroupDTO){
                return ResultVO.error("goodsGroupDTO is null");
            }
            List<GoodsDTO> list = goodsGroupDTO.getGoods();
            for(GoodsDTO goodsDTO:list){
                if(StringUtils.isEmpty(goodsDTO.getImageFile())){
                    continue;
                }
                String newImageFile = fullImageUrl(goodsDTO.getImageFile(),dfsShowIp,true);
                goodsDTO.setImageFile(newImageFile);
            }
            return ResultVO.success(goodsGroupDTO);
        }catch (Exception e){
            log.error("GoodsGroupController saveGoodGroup Exception={} ",e);
            return ResultVO.error();
        }
    }

    @ApiOperation(value = "修改商品分组", notes = "修改商品分组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateGoodsGroup", method = RequestMethod.POST)
    public ResultVO updateGoodsGroup(@RequestBody GoodsGroupUpdateReq req){
        if(null == req){
            return ResultVO.error("req is null");
        }
        if(StringUtils.isEmpty(req.getGroupId())){
            return ResultVO.error("groupId is null");
        }
        try {
            int num= goodsGroupService.updateGoodsGroup(req);
            if(num > 0){
                return ResultVO.success(num);
            }
            return ResultVO.error();
        }catch (Exception e){
            log.error("GoodsGroupController updateGoodsGroup Exception={} ",e);
            return ResultVO.error();
        }
    }

    @ApiOperation(value = "删除商品分组", notes = "删除商品分组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteGoodsGroup", method = RequestMethod.GET)
    public ResultVO deleteGoodsGroup(@RequestParam String groupId){
        if(StringUtils.isEmpty(groupId)){
            return ResultVO.error("groupId is null");
        }
        try {
            int num= goodsGroupService.deleteGoodsGroup(groupId);
            if(num > 0){
                return ResultVO.success(num);
            }
            return ResultVO.error();
        }catch (Exception e){
            log.error("GoodsGroupController deleteGoodsGroup Exception={} ",e);
            return ResultVO.error();
        }
    }

    @ApiOperation(value = "查询商品分组", notes = "查询商品分组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listGoodGroup", method = RequestMethod.GET)
    public ResultVO listGoodGroup(@RequestParam String groupName,@RequestParam String groupCode,
                                  @RequestParam Integer pageNo,@RequestParam Integer pageSize){
        GoodGroupQueryReq req = new GoodGroupQueryReq();
        if(StringUtils.isNotEmpty(groupName)){
            req.setGroupName(groupName);
        }
        if(StringUtils.isNotEmpty(groupCode)){
            req.setGroupCode(groupCode);
        }
        if(null != pageNo){
            req.setPageNo(pageNo);
        }
        if(null != pageSize){
            req.setPageSize(pageSize);
        }
        try {
            Page<GoodsGroupDTO> goodsGroupList= goodsGroupService.listGoodsGroup(req);
            if(null == goodsGroupList){
                log.info("GoodsGroupController listGoodGroup list={} ",goodsGroupList);
                ResultVO.error("goodsGroupList is empty");
            }
            return ResultVO.success(goodsGroupList);
        }catch (Exception e){
            log.error("GoodsGroupController saveGoodGroup Exception={} ",e);
            return ResultVO.error();
        }
    }

    @RequestMapping(value = "/listGoodsByGoodsId", method = RequestMethod.GET)
    public ResultVO listGoodsByGoodsId(@RequestParam String goodsId){
        if(StringUtils.isEmpty(goodsId)){
            return ResultVO.error("goodsId is null");
        }
        try {
            List<GoodsDTO> list = goodsGroupService.listGoodsByGoodsId(goodsId);
            if(null == list){
                return ResultVO.error();
            }
            if(CollectionUtils.isEmpty(list)){
                return ResultVO.success(list);
            }
            for(GoodsDTO goodsDTO:list){
                if(StringUtils.isEmpty(goodsDTO.getImageFile())){
                    continue;
                }
                String newImageFile = fullImageUrl(goodsDTO.getImageFile(),dfsShowIp,true);
                goodsDTO.setImageFile(newImageFile);
            }
            return ResultVO.success(list);
        }catch (Exception e){
            log.error("GoodsGroupController listGoodsByGoodsId Exception={} ",e);
            return ResultVO.error();
        }
    }

    /**
     * 拼接完整的地址
     * @param imagePath
     * @param showUrl
     * @param flag 为true时，拼接完整地址，为false时，是截取地址
     * @return
     */
    private static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (org.springframework.util.StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }
}
