package com.iwhalecloud.retail.goods.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods.entity.AttrSpec;
import com.iwhalecloud.retail.goods.helper.AttrSpecHelper;
import com.iwhalecloud.retail.goods.mapper.AttrSpecMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class AttrSpecManager{
    @Resource
    private AttrSpecMapper attrSpecMapper;


    /**
     * 根据类别获取属性规格配置
     * @param typeId 类别ID
     * @return 配置的属性规格集合
     */
    public List<AttrSpecDTO> queryAttrSpecList(String typeId) {
        List<AttrSpec> attrSpecs = getAttrSpecs(typeId);
        if (attrSpecs == null) {
            return null;
        }

        List<AttrSpecDTO> attrSpecDTOs = new ArrayList<AttrSpecDTO>();
        for (AttrSpec attrSpec : attrSpecs) {
            if (attrSpec != null) {
                AttrSpecDTO attrSpecDTO = new AttrSpecDTO();
                BeanUtils.copyProperties(attrSpec,attrSpecDTO);

                attrSpecDTOs.add(attrSpecDTO);
            }
        }
        return attrSpecDTOs;
    }


    /**
     * 获取属性规格配置
     * @param typeId 类别ID
     * @return
     */
    private List<AttrSpec> getAttrSpecs(String typeId) {
        QueryWrapper<AttrSpec> queryWrapper = Condition.create();
        queryWrapper.eq(AttrSpec.FieldNames.typeId.getTableFieldName(),typeId);
        //只查询有效数据
        queryWrapper.eq(AttrSpec.FieldNames.statusCd.getTableFieldName(), GoodsConst.StatusCdEnum.STATUS_CD_VALD.getCode());
        queryWrapper.orderByAsc(AttrSpec.FieldNames.specOrder.getTableFieldName());

        return attrSpecMapper.selectList(queryWrapper);
    }


    /**
     * 查询类别属性与设置的实例值
     * @param typeId 类别ID
     * @param goodsId 商品ID
     * @return 类别的属性与配置的属性值
     */
    public List<AttrSpecDTO> queryAttrSpecWithInstValue(String typeId,String goodsId) {
        if (StringUtils.isEmpty(typeId) || StringUtils.isEmpty(goodsId)) {
            return null;
        }
        List<AttrSpecDTO> attrSpecDTOs = new ArrayList<>();
        List<AttrSpec> attrSpecs = this.getAttrSpecs(typeId);
        for (AttrSpec attrSpec : attrSpecs) {
            if (attrSpec != null) {
                AttrSpecDTO attrSpecDTO = new AttrSpecDTO();
                BeanUtils.copyProperties(attrSpec,attrSpecDTO);

                final String instValue = getInstValue(attrSpec.getTableName(),attrSpec.getFiledName(),goodsId);
                attrSpecDTO.setInstValue(instValue);

                attrSpecDTOs.add(attrSpecDTO);
            }
        }
        return attrSpecDTOs;
    }

    /**
     * 获取实例的值
     * @param tableName 表名
     * @param filedName 字段名
     * @param goodsId 商品ID
     * @return
     */
    private String getInstValue(String tableName,String filedName,String goodsId) {
        try {
            Object objValue = dynamicQueryWithCache(tableName,goodsId);
            if (objValue == null) {
                log.info("AttrSpecManager.getInstValue 未查询到实例数据,tableName={},filedName={},goodsId={}",tableName,filedName,goodsId);
                return "";
            }
            log.info("AttrSpecManager.getInstValue objValue={}", JSON.toJSON(objValue));
            String filedObj = AttrSpecHelper.underlineToUpper(filedName);

            //将下划线转为驼峰，并将首字母大写
            String methodName = "get" + StringUtils.capitalize(AttrSpecHelper.underlineToUpper(filedObj));
            Method method = objValue.getClass().getMethod(methodName);
            log.info("AttrSpecManager.getInstValue,className={},methodName={}",objValue.getClass().getName(),methodName);
            Object invokeValue = method.invoke(objValue);
            if (invokeValue == null) {
                return "";
            }
            return String.valueOf(invokeValue);
        } catch (IllegalAccessException e) {
            log.error("AttrSpecManager.getInstValue IllegalAccessException",e);
            throw new RuntimeException("获取属性实例失败，非法访问");
        } catch (InvocationTargetException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，内部方法异常");
        } catch (NoSuchMethodException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，方法不存在");
        }
    }

    /**
     * 根据商品ID查询表数据，优先从缓存加载
     * @param tableName 表名称
     * @param goodsId 商品ID
     * @return 查询的对象
     */
    private Object dynamicQueryWithCache(String tableName,String goodsId) {
        Map<String,Object> cacheObj = new HashMap<String,Object>();
        final String cacheKey = tableName + "_" + goodsId;
        if (cacheObj.containsKey(cacheKey)) {
            return cacheObj.get(cacheKey);
        }

        Object dbObject = dynimcQuery(tableName,goodsId);
        cacheObj.put(cacheKey,dbObject);
        return dbObject;
    }

    /**
     * 动态查询数据
     * @param tableName 表名
     * @param goodsId 商品ID
     * @return
     */
    private Object dynimcQuery(String tableName,String goodsId) {

        BaseMapper mapper = AttrSpecHelper.getMapperBean(tableName);

        QueryWrapper queryWrapper = Condition.create();
        queryWrapper.eq("goods_id",goodsId);
        Object objValue = mapper.selectOne(queryWrapper);

        return objValue;
    }

}
