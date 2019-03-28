package com.iwhalecloud.retail.web.controller;

import com.iwhalecloud.retail.dto.ResultVO;

/**
 * @author Z
 * @date 2018/10/8
 */
public class BaseController<T> {

    /**
     * 返回处理成功对象
     * @param t 结果数据对象
     * @return
     */
    public ResultVO<T> successResultVO(T t) {
        return ResultVO.success(t);
    }

    /**
     * 返回处理失败对象
     * @return
     */
    public ResultVO<T> failResultVO(){
        return ResultVO.error();
    }

    /**
     * 返回处理失败对象
     * @return
     */
    public ResultVO<T> failResultVO(String errorMsg){
        return ResultVO.error(errorMsg);
    }


    /**
     *
     * @param resultCode 结果编码
     * @param resultMsg 结果消息
     * @param t 结果数据对象
     * @return
     */
    public ResultVO<T> resultVO(String resultCode,String resultMsg,T t) {
        ResultVO resultVO = new ResultVO();

        resultVO.setResultCode(resultCode);
        resultVO.setResultMsg(resultMsg);
        resultVO.setResultData(t);
        return resultVO;
    }
}
