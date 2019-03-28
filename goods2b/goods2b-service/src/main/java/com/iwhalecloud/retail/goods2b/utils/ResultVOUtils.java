package com.iwhalecloud.retail.goods2b.utils;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import org.springframework.util.StringUtils;

public class ResultVOUtils {

    /**
     * 生成增删改操作ResultVO
     * @param effRow
     * @return
     */
    public static ResultVO genAduResultVO(int effRow){
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData("effectRownum,"+effRow);
        if (effRow > 0){
            resultVO.setResultCode(GoodsResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultMsg("success");
        } else{
            resultVO.setResultCode(GoodsResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("haven't operated records");
        }
        return resultVO;
    }

    /**
     * 生成查询ResultVO
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultVO<T> genQueryResultVO(T t){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setResultData(t);
        resultVO.setResultMsg("success");
        resultVO.setResultCode(GoodsResultCodeEnum.SUCCESS.getCode());
        return resultVO;
    }

    public static ResultVO<String> genInsertResultVO(String id){
        ResultVO resultVO = new ResultVO();
        resultVO.setResultData(id);
        if (!StringUtils.isEmpty(id)){
            resultVO.setResultCode(GoodsResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultMsg("success");
        } else{
            resultVO.setResultCode(GoodsResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("haven't operated records");
        }
        return resultVO;
    }
}
