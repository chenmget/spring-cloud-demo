package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ProdSpecValuesDTO;
import com.iwhalecloud.retail.goods.dto.ProdSpecificationDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsSpecListResp;
import com.iwhalecloud.retail.goods.entity.ProdGoodsSpec;
import com.iwhalecloud.retail.goods.entity.ProdSpecValues;
import com.iwhalecloud.retail.goods.entity.ProdSpecification;
import com.iwhalecloud.retail.goods.manager.ProdGoodsSpecManager;
import com.iwhalecloud.retail.goods.manager.ProdProductManager;
import com.iwhalecloud.retail.goods.manager.ProdSpecValuesManager;
import com.iwhalecloud.retail.goods.manager.ProdSpecificationManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("prodGoodsSpecService")
@Service
public class ProdGoodsSpecServiceImpl implements ProdGoodsSpecService {

    @Autowired
    private ProdGoodsSpecManager prodGoodsSpecManager;

    @Autowired
    private ProdSpecificationManager prodSpecificationManager;

    @Autowired
    private ProdSpecValuesManager prodSpecValuesManager;

    @Autowired
    private ProdProductManager prodProductManager;


    @Override
    public ResultVO<ProdGoodsSpecListResp> listProdGoodsSpec(String goodId) {
        if (StringUtils.isEmpty(goodId)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProdGoodsSpecListResp resp = new ProdGoodsSpecListResp();
        List<ProdSpecificationDTO> specificationDTOList = Lists.newArrayList();
        resp.setSpecificationList(specificationDTOList);
        // 根据商品ID查询商品规格
        List<ProdGoodsSpec> prodGoodsSpecList = prodGoodsSpecManager.selectGoodsSpecByGoodsId(goodId);
        if (CollectionUtils.isEmpty(prodGoodsSpecList)) {
            return ResultVO.success(resp);
        }
        Map<String,List<ProdGoodsSpec>> specValueMap = prodGoodsSpecList.stream().collect(Collectors.groupingBy(ProdGoodsSpec::getSpecId));
        for (Map.Entry<String,List<ProdGoodsSpec>> entry : specValueMap.entrySet()) {
            String specId = entry.getKey();
            ProdSpecificationDTO specificationDTO = new ProdSpecificationDTO();
            // 根据规格ID查询规格
            List<ProdSpecification> specificationList = prodSpecificationManager.selectSpecBySpecId(specId);
            if (CollectionUtils.isEmpty(specificationList)) {
                return ResultVO.success(resp);
            }
            for (ProdSpecification specification : specificationList) {
                BeanUtils.copyProperties(specification,specificationDTO);
                List<ProdSpecValuesDTO> prodSpecValuesDTOS = Lists.newArrayList();
                specificationDTO.setValueList(prodSpecValuesDTOS);
                // 根据规格值ID查询规格值
                List<ProdGoodsSpec> goodsSpecList = entry.getValue();
                Map<String,List<ProdGoodsSpec>> goodsSpecMap = goodsSpecList.stream().collect(Collectors.groupingBy(ProdGoodsSpec :: getSpecValueId));
                for (Map.Entry<String,List<ProdGoodsSpec>> goodsSpecEntry : goodsSpecMap.entrySet()) {
                    String specValueId = goodsSpecEntry.getKey();
                    List<ProdGoodsSpec> goodsSpecs = goodsSpecEntry.getValue();
                    String products = null;
                    String prices = null;
                    String stores = null;
                    for (ProdGoodsSpec spec : goodsSpecs) {
                        if (products == null) {
                            products = spec.getProductId();
                        } else {
                            products += "," + spec.getProductId();
                        }
                        ProdProductDTO productDTO = prodProductManager.getProduct(spec.getProductId());
                        if (prices == null) {
                            prices = productDTO.getPrice().toString();
                        } else {
                            prices += "," + productDTO.getPrice().toString();
                        }
                        if (stores == null) {
                            stores = productDTO.getStore().toString();
                        } else {
                            stores += "," + productDTO.getStore().toString();
                        }
                    }
                    ProdSpecValues prodSpecValues = prodSpecValuesManager.selectSpecValueBySpecId(specValueId);
                    if (prodSpecValues != null) {
                        ProdSpecValuesDTO prodSpecValuesDTO = new ProdSpecValuesDTO();
                        BeanUtils.copyProperties(prodSpecValues, prodSpecValuesDTO);
                        prodSpecValuesDTO.setProductId(products);
                        prodSpecValuesDTO.setPrice(prices);
                        prodSpecValuesDTO.setStore(stores);
                        prodSpecValuesDTOS.add(prodSpecValuesDTO);
                    }
                }
            }
            specificationDTOList.add(specificationDTO);
        }
        return ResultVO.success(resp);
    }
}