package com.iwhalecloud.retail.web.controller.b2b.goods.service;

import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.web.office.base.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;

/****
 * @author gs_10010
 * @date 2019/7/9 15:27
 */
@Component
public class ReadGoodsRelByGGService extends ReadExcel<GoodsRulesDTO> {
    @Override
    public GoodsRulesDTO builderObject2007(HSSFRow cell) {
        return null;
    }

    @Override
    public GoodsRulesDTO builderObject2010(XSSFRow cell) {

        boolean isContinue = true;
        GoodsRulesDTO goodsRelModel=new GoodsRulesDTO();
        int i = 0;
        while (isContinue) {
            XSSFCell cell1 = cell.getCell(i);
            if (cell1 == null) {
                continue;
            }
            switch (i) {
                case 0:
                    goodsRelModel.setTargetName(getValue(cell1));
                    break;
                case 1:
                    goodsRelModel.setTargetCode(getValue(cell1));
                    break;
                case 2:
                    goodsRelModel.setMarketNum(Long.parseLong(getValue(cell1)));
                    break;
                case 3:
                    goodsRelModel.setProductCode(getValue(cell1));
                    break;
                default:
                    isContinue = false;
                    break;
            }
            i++;
        }
        return goodsRelModel;

    }

}
