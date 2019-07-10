package com.iwhalecloud.retail.web.controller.b2b.goods.service;

import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesProductDTO;
import com.iwhalecloud.retail.web.office.base.ReadExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

/****
 * @author gs_10010
 * @date 2019/7/9 15:27
 */
@Component
@Slf4j
public class ReadGoodsRelByGGService extends ReadExcel<GoodsRulesDTO> {
    @Override
    public GoodsRulesDTO builderObject2007(HSSFRow cell) {
        return null;
    }

    @Override
    public GoodsRulesDTO builderObject2010(XSSFRow cell) {

        GoodsRulesProductDTO goodsRelModel=new GoodsRulesProductDTO();

        goodsRelModel.setTargetName(getValue(cell.getCell(0)));
        goodsRelModel.setTargetCode(getValue(cell.getCell(1)));
        goodsRelModel.setMarketNum(new Double(Double.parseDouble(getValue(cell.getCell(2)))).longValue());
        goodsRelModel.setProductCode(getValue(cell.getCell(3)));
        return goodsRelModel;

    }

}
