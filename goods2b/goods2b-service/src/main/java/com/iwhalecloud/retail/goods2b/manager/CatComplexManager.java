package com.iwhalecloud.retail.goods2b.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.dto.CatComplexDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatComplexQueryReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatComplexQueryResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.entity.CatComplex;
import com.iwhalecloud.retail.goods2b.mapper.CatComplexMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CatComplexManager{
    @Resource
    private CatComplexMapper catComplexMapper;

    /**
     * 添加分类关联
     * @return
     */
    public int addCatComplex(List<CatComplex> catComplexList) {
        int rspNum = 0;
        if (CollectionUtils.isNotEmpty(catComplexList)) {
            for (CatComplex catComplex : catComplexList) {
                rspNum += catComplexMapper.insert(catComplex);
            }
        }
        return rspNum;
    }

    /**
     * 删除分类关联
     * @return
     */
    public int delCatComplexByTargetId(String catId, String targetType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(CatComplex.FieldNames.catId.getTableFieldName(), catId);
        wrapper.eq(CatComplex.FieldNames.targetType.getTableFieldName(), targetType);
        return catComplexMapper.delete(wrapper);
    }

    /**
     * 查询分类关联
     * @return
     */
    public List<CatComplexDTO> queryCatComplexbyCatId(String catId, String targetType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(CatComplex.FieldNames.catId.getTableFieldName(), catId);
        wrapper.eq(CatComplex.FieldNames.targetType.getTableFieldName(), targetType);
        List<CatComplex> catComplexList = catComplexMapper.selectList(wrapper);
        List<CatComplexDTO> catComplexDTOList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(catComplexList)) {
            for (CatComplex catComplex : catComplexList) {
                CatComplexDTO catComplexDTO = new CatComplexDTO();
                BeanUtils.copyProperties(catComplex, catComplexDTO);
                catComplexDTOList.add(catComplexDTO);
            }
        }
        return catComplexDTOList;
    }

    /**
     * 批量查询分类关联
     * @return
     */
    public List<CatComplexDTO> queryCatComplexbyCatIds(List<String> catIdList, String targetType) {
        List<CatComplexDTO> catComplexDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(catIdList)) {
            for (String catId : catIdList) {
                List<CatComplexDTO> catComplexDTOS = queryCatComplexbyCatId(catId,targetType );
                catComplexDTOList.addAll(catComplexDTOS);
            }
        }
        return catComplexDTOList;
    }

    /**
     * 通过ID删除分类关联
     * @return
     */
    public int deleteCatComplexById(List<String> ids) {
        return catComplexMapper.deleteBatchIds(ids);
    }

    /**
     * 根据类别ID和对象类型分页查询商品分类推荐
     * @param req
     * @return
     */
    public Page<CatComplexQueryResp> queryCatComplexForPage(CatComplexQueryReq req) {
        Page<GoodsForPageQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<CatComplexQueryResp> catComplexQueryRespPage = catComplexMapper.queryCatComplexForPage(page, req);
        return catComplexQueryRespPage;
    }
    
}
