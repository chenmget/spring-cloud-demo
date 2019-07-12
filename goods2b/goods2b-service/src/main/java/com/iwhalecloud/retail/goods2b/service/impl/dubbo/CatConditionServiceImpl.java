package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.CatConditionConst;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatConditionDetailResp;
import com.iwhalecloud.retail.goods2b.entity.Cat;
import com.iwhalecloud.retail.goods2b.entity.CatCondition;
import com.iwhalecloud.retail.goods2b.manager.CatConditionManager;
import com.iwhalecloud.retail.goods2b.manager.CatManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CatConditionServiceImpl implements CatConditionService {

    @Autowired
    private CatConditionManager catConditionManager;

    @Autowired
    private CatManager catManager;

    @Reference
    private TypeService typeService;

    /**
     * 添加一个 商品类型条件
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> saveCatCondition(CatConditionSaveReq req) {
        log.info("CatConditionServiceImpl.saveCatCondition() input: {}", JSON.toJSONString(req));
        ResultVO<Integer> resultVO = new ResultVO<>();
        CatCondition entity = new CatCondition();
        BeanUtils.copyProperties(req, entity);
        // 其他信息设置
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        if (!StringUtils.isEmpty(req.getCreateStaff())) {
            entity.setUpdateStaff(req.getCreateStaff());
        }
        if (Objects.isNull(req.getOrderBy())) {
            entity.setOrderBy(0L);
        }
        Integer resultInt = catConditionManager.saveCatCondition(entity);
        if (resultInt > 0) {
            resultVO = ResultVO.success(resultInt);
        } else {
            resultVO = ResultVO.error();
        }
        log.info("CatConditionServiceImpl.saveCatCondition() output: {}", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 商品类型条件 列表查询
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<CatConditionDTO>> listCatCondition(CatConditionListReq req) {
        log.info("CatConditionServiceImpl.listCatCondition() input: {}", JSON.toJSONString(req));
        ResultVO<List<CatConditionDTO>> resultVO = ResultVO.success(catConditionManager.listCatCondition(req));
        log.info("CatConditionServiceImpl.listCatCondition() output: {}", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 删除 商品类型条件
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteCatCondition(CatConditionDeleteReq req) {
        log.info("CatConditionServiceImpl.deleteCatCondition() input: {}", JSON.toJSONString(req));
        int result = catConditionManager.deleteCatCondition(req);
        log.info("CatConditionServiceImpl.deleteCatCondition() output: {}", result);
        if (result < 0) {
            return ResultVO.error("删除 商品类型条件 信息失败");
        }
        return ResultVO.success("删除 商品类型条件 信息 条数：" + result, result);
    }

    /**
     * 根据商品分类ID获取 商品分类条件 详情 列表
     * @param catId
     * @return
     */
    @Override
    public ResultVO<CatConditionDetailResp> getCatConditionDetail(String catId) {
        log.info("CatConditionServiceImpl.getCatConditionDetail() input  catId: {}", JSON.toJSONString(catId));

        CatConditionDetailResp resp = new CatConditionDetailResp();

        CatConditionListReq req = new CatConditionListReq();
        req.setCatId(catId);
        List<CatConditionDTO> dtoList = catConditionManager.listCatCondition(req);
        if (CollectionUtils.isEmpty(dtoList)) {
            resp.setCatConditionList(Lists.newArrayList());
            return ResultVO.success(resp);
        }

        resp.setCatConditionList(dtoList);

        for (CatConditionDTO dto : dtoList) {
            if (StringUtils.equals(CatConditionConst.RelType.PRODUCT_TYPE.getCode(), dto.getRelType())) {
                // 类型是1的 要去获取类型详情
                resp.setTypeDetail(getTypeDetail(dto.getRelObjId()));
            }
        }

        ResultVO<CatConditionDetailResp> resultVO = ResultVO.success(resp);
        log.info("CatConditionServiceImpl.getCatConditionDetail() output: {}", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 根据catId获取 当前catId的所有父级 catId 并查询出所有的关联  分类条件 列表
     * @param catId
     * @return
     */
    private List<CatConditionDTO> getCatConditionList(String catId) {
        Cat cat = catManager.queryProdCat(catId);
        if (Objects.isNull(cat) || Objects.isNull(cat.getCatPath())) {
            log.info("CatConditionServiceImpl.getCatConditionList() catId：{}对应分类信息为空或者分类路径字段为空", JSON.toJSONString(catId));
            return Lists.newArrayList();
        }
        String catPath = cat.getCatPath();
        // 要记得转义
        String[] catIds = catPath.split("\\|");
        log.info("CatConditionServiceImpl.getCatConditionList() catIds：{}对应分类信息为空或者分类路径字段为空", JSON.toJSONString(catIds));

        // 先取路径上 所有catId 对应的 分类条件
        CatConditionListReq req = new CatConditionListReq();
        req.setCatIdList(Lists.newArrayList(catIds));
        List<CatConditionDTO> allList = catConditionManager.listCatCondition(req);
        // catId对应的分类条件（最低一级的）
        List<CatConditionDTO> lowestList = Lists.newArrayList();
        // 最终结果list
        List<CatConditionDTO> resultList = Lists.newArrayList();
        allList.forEach(dto -> {
            // 取catId对应的分类条件（最低一级的）
            if (StringUtils.equals(dto.getCatId(), catId)) {
                lowestList.add(dto);
                resultList.add(dto);
            }
        });

        // 循环所有的分类条件
        allList.forEach(dto -> {
            // 循环取catId对应的分类条件
            if (StringUtils.equals(dto.getCatId(), catId)) {
                lowestList.add(dto);
            }
        });

//        allList.stream().filter(catConditionDTO -> )
//        List<CatConditionDTO> resultList = Lists.newArrayList();
        return null;
    }

    /**
     * 获取类型详情
     * @param typeId
     * @return
     */
    private TypeDTO getTypeDetail(String typeId) {
        if (StringUtils.isEmpty(typeId)) {
            return null;
        }
        TypeSelectByIdReq req = new TypeSelectByIdReq();
        req.setTypeId(typeId);

        ResultVO resultVO = typeService.selectById(req);
        log.info("CatConditionServiceImpl.getTypeDetail() input:typeId:{}  output: {}", typeId, JSON.toJSONString(resultVO));
        if (resultVO.isSuccess() && Objects.nonNull(resultVO.getResultData())) {
            return (TypeDTO) resultVO.getResultData();
        }
        return null;
    }


}