package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionSaveReq;
import com.iwhalecloud.retail.goods2b.entity.CatCondition;
import com.iwhalecloud.retail.goods2b.manager.CatConditionManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CatConditionServiceImpl implements CatConditionService {

    @Autowired
    private CatConditionManager catConditionManager;

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

}