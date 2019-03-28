package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodsRightAddReq;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsRightService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwl
 * 商品-权益关联
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("api/goodsRight")
public class GoodsRightController extends BaseController {

    @Reference
    private GoodsRightService goodsRightService;

    @ApiOperation(value = "新增商品的优惠券", notes = "传入GoodsRightAddReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/add")
    public ResultVO addGoodsRight(@RequestBody @ApiParam(value = "GoodsRightAddReq", required = true) GoodsRightAddReq req) {

        if (StringUtils.isEmpty(req.getGoodsId())) {
            return ResultVO.error("商品ID不能为空！");
        }
//        if (CollectionUtils.isEmpty(req.getRightsList())) {
//            return ResultVO.error("优惠券列表不能为空！");
//        }
        try {
            int result = goodsRightService.insertGoodsRight(req);
            if (result > 0) {
                return successResultVO("添加优惠券成功");
            }
            return failResultVO("添加优惠券失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }

    @ApiOperation(value = "删除商品的所有优惠券", notes = "传入GoodsRightDTO对象的goodsId，进行添加操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/deleteByGoodsId")
    public ResultVO deleteAllGoodsRightByGoodsId(@RequestBody @ApiParam(value = "GoodsRightDTO", required = true) GoodsRightDTO goodsRightDTO) {

        if (StringUtils.isEmpty(goodsRightDTO.getGoodsId())) {
            return ResultVO.error("商品ID不能为空！");
        }
        try {
            int result = goodsRightService.deleteGoodsRight(goodsRightDTO.getGoodsId());
            if (result > 0) {
                return successResultVO("删除商品关联优惠券成功");
            }
            return failResultVO("删除商品关联优惠券失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }



    @ApiOperation(value = "查询商品关联的优惠券列表", notes = "根据商品goodsId，查询GoodsCountRankDTO列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "partnerId", paramType = "path", required = false, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数未填对"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    // 查询搜索关键字统计列表
    @RequestMapping(value = "/listByGoodsId", method = RequestMethod.GET)
    public ResultVO listByGoodsId(
                        @RequestParam(value = "goodsId", required = true) String goodsId
    ) {
        if (StringUtils.isEmpty(goodsId)) {
            return failResultVO("商品ID不能为空");
        }
        List<GoodsRightDTO> list = goodsRightService.listByGoodsId(goodsId);
        return successResultVO(list);
    }
}
