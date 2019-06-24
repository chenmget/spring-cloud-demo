package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.CatAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatListResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CatResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/21
 **/
@RestController
@RequestMapping("/api/b2b/cat")
@Slf4j
public class CatB2BController {

    @Reference
    private CatService catService;

    /**
     * 新增产品类别
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addProdCat", method = RequestMethod.POST)
    public ResultVO<Boolean> addProdCat(@RequestBody @Valid CatAddReq req) {
        log.info("CartController updateCartNum req={} ", req);
        return catService.addProdCat(req);
    }

    /**
     * 新增产品类别--中台
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addProdCatByZT", method = RequestMethod.POST)
    public ResultVO<String> addProdCatByZT(@RequestBody @Valid CatAddReq req) {
        log.info("CartController addProdCatByZT req={} ", req);
        return catService.addProdCatByZT(req);
    }

    /**
     * 修改产品类别
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProdCat", method = RequestMethod.POST)
    public ResultVO<Boolean> updateProdCat(@RequestBody CatUpdateReq req) {
        log.info("CartController updateCartNum req={} ", req);
        return catService.updateProdCat(req);
    }

    /**
     * 修改产品类别-中台
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProdCatByZT", method = RequestMethod.POST)
    public ResultVO<Boolean> updateProdCatByZT(@RequestBody CatUpdateReq req) {
        log.info("CartController updateCartNum req={} ", req);
        return catService.updateProdCatByZT(req);
    }

    /**
     * 查询产品类别列表
     *
     * @return
     */
    @RequestMapping(value = "/batchUpdateProdCat", method = RequestMethod.POST)
    public ResultVO<Boolean> batchUpdateProdCat(@RequestBody List<CatUpdateReq> reqs) {
        if (CollectionUtils.isEmpty(reqs)) {
            return ResultVO.error("参数为空");
        }
        CatQueryReq catQueryReq = new CatQueryReq();
        catQueryReq.setUpdateReqList(reqs);
        ResultVO<Boolean> resultVO = catService.batchUpdateProdCat(catQueryReq);
        return resultVO;
    }

    /**
     * 删除产品类别
     *
     * @param catId
     * @return
     */
    @RequestMapping(value = "/deleteProdCat", method = RequestMethod.GET)
    public ResultVO<Boolean> deleteProdCat(@RequestParam String catId) {
        if (StringUtils.isEmpty(catId)) {
            return ResultVO.error("catId is not must be null");
        }
        CatQueryReq catQueryReq = new CatQueryReq();
        catQueryReq.setCatId(catId);
        return catService.deleteProdCat(catQueryReq);
    }

    @RequestMapping(value = "/queryCat", method = RequestMethod.GET)
    public ResultVO<CatResp> queryCat(@RequestParam String catId) {
        if (StringUtils.isEmpty(catId)) {
            return ResultVO.error("catId is not must be null");
        }
        CatQueryReq catQueryReq = new CatQueryReq();
        catQueryReq.setCatId(catId);
        return catService.queryProdCat(catQueryReq);
    }

    /**
     * 查询产品类别分页
     *
     * @param pageNum
     * @param pageSize
     * @param catName
     * @return
     */
    @RequestMapping(value = "/queryProdCatForPage", method = RequestMethod.GET)
    public ResultVO<IPage> queryProdCatForPage(@RequestParam Long pageNum, @RequestParam Long pageSize,
                                               @RequestParam String catName) {
        if (StringUtils.isEmpty(catName)) {
            return ResultVO.error("类别名称为空");
        }
        CatQueryReq catQueryReq = new CatQueryReq();
        catQueryReq.setCatName(catName);
        catQueryReq.setPageSize(pageSize);
        catQueryReq.setPageNum(pageNum);
        return catService.listProdCatByCatName(catQueryReq);
    }

    /**
     * 查询产品类别列表
     *
     * @return
     */
    @RequestMapping(value = "/listCat", method = RequestMethod.GET)
    public ResultVO<CatListResp> listCat(@RequestParam(value = "parentCatId", required = false) String parentCatId,
                                         @RequestParam(value = "typeId", required = false) String typeId) {
        CatQueryReq catQueryReq = new CatQueryReq();
        catQueryReq.setParentCatId(parentCatId);
        catQueryReq.setTypeId(typeId);
        ResultVO<CatListResp> resultVO = catService.queryCatList(catQueryReq);
        return resultVO;
    }

}
