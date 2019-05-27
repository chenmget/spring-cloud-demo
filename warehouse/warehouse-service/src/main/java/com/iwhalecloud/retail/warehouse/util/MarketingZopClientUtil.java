package com.iwhalecloud.retail.warehouse.util;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.dto.response.markres.base.*;
import com.ztesoft.zop.common.message.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 调用营销资源接口
 */
@Component
@Slf4j
public class MarketingZopClientUtil {

    @Autowired
    private Environment env;

    /**
     * 调用营销资源查询接口
     *
     * @param method  方法
     * @param version 版本
     * @param params  参数
     * @return 返回 结果信息ResultVO，ResultVO数据格式：QueryMarkResQueryResultsResp
     */
    public ResultVO callQueryRest(String method, String version, Object params,Class itemCl) {
        ResultVO resultVO = this.dealZopHead(getZoSecret(), method, version, params);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        //能开返回成功时
        Map<String, Object> temp = (Map<String, Object>) resultVO.getResultData();

        QueryMarkResResp<QueryMarkResQueryResultsResp> queryMarkResResp =
                JSON.parseObject(JSON.toJSONString(temp), QueryMarkResResp.class);
        //营销返回为空
        if (queryMarkResResp == null || queryMarkResResp.getCode() == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        //营销返回错误
        if (!MarketingResConst.MK_RS_SUC.equals(queryMarkResResp.getCode())) {
            String error = queryMarkResResp.getMessage() == null ? "接口返回为空" : queryMarkResResp.getMessage();
            return ResultVO.error(error);
        }
        //数据，包括分页信息与数据
        QueryMarkResQueryResultsResp queryResults = queryMarkResResp.getQueryResults();
        List<Object> listTemp = queryResults.getQueryInfo();
        if(listTemp!=null&&!listTemp.isEmpty()){
            queryResults.setQueryInfo(JSON.parseArray(JSON.toJSONString(listTemp),itemCl));
        }

        return ResultVO.success(queryResults);
    }

    /**
     * 将执行的结果改成返回ResultVO形式(只有成功失败)，将ExcuteMarkResResultItemResp的错误主键组织成字符串
     *
     * @param resultVO 对应的List<ExcuteMarkResResultItemResp>必须只有一个值
     * @return
     */
    public ResultVO swapCallExcuteRestResultVO(ResultVO<List<ExcuteMarkResResultItemResp>> resultVO) {
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        List<ExcuteMarkResResultItemResp> resultList = resultVO.getResultData();
        if (resultList == null || resultList.isEmpty()) {
            return ResultVO.error("接口返回为空");
        }

        for (ExcuteMarkResResultItemResp itemResp : resultList) {
            if (!MarketingResConst.MK_EXCUTE_RS_SUC.equals(itemResp.getResultCode())) {
                return ResultVO.error(itemResp.getResultDesc());
            }
        }


        return ResultVO.success(true);


    }



    /**
     * 调用营销资源非查询接口，返回结果包括处理集合
     *
     * @param method  调用的方法
     * @param version 版本
     * @param params  参数
     * @return 返回ResultVO, 包括处理逻辑：List<ExcuteMarkResResultItemResp>
     */
    public ResultVO callExcuteRest(String method, String version, Object params) {
        ResultVO resultVO = this.dealZopHead(getZoSecret(), method, version, params);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        //能开返回成功时
        Map<String, Object> temp = (Map<String, Object>) resultVO.getResultData();
        if (temp == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        ExcuteMarkResResp excuteMarkResResp = JSON.parseObject(JSON.toJSONString(temp), ExcuteMarkResResp.class);
        //营销返回为空
        if (excuteMarkResResp == null || excuteMarkResResp.getCode() == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        //营销返回错误
        if (!MarketingResConst.MK_RS_SUC.equals(excuteMarkResResp.getCode())) {
            String error = excuteMarkResResp.getMessage() == null ? "接口返回为空" : excuteMarkResResp.getMessage();
            return ResultVO.error(error);
        }

        ExcuteMarkResResultResp excuteMarkResResultResp = excuteMarkResResp.getResultInfo();
        if (excuteMarkResResultResp == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        List<ExcuteMarkResResultItemResp> excuteMarkResResultItemResp = excuteMarkResResultResp.getResultInfo_Item();

        return ResultVO.success(excuteMarkResResultItemResp);
    }

    /**
     * 调用营销资源非查询接口
     *
     * @param method  调用的方法
     * @param version 版本
     * @param params  参数
     * @return 返回ResultVO, 成功与失败
     */
    public ResultVO callExcuteSimplRest(String method, String version, Object params) {
        ResultVO resultVO = this.dealZopHead(getZoSecret(), method, version, params);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        //能开返回成功时
        Map<String, Object> temp = (Map<String, Object>) resultVO.getResultData();
        if (temp == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        ExcuteMarkResExcuteSimplResp excuteMarkResResp = JSON.parseObject(JSON.toJSONString(temp), ExcuteMarkResExcuteSimplResp.class);
        //营销返回为空
        if (excuteMarkResResp == null || excuteMarkResResp.getCode() == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        //营销返回错误
        if (!MarketingResConst.MK_RS_SUC.equals(excuteMarkResResp.getCode())) {
            String error = excuteMarkResResp.getMessage() == null ? "接口返回为空" : excuteMarkResResp.getMessage();
            return ResultVO.error(error);
        }

        ExcuteMarkResResultSimplResp excuteMarkResResultResp = excuteMarkResResp.getResult();
        if (excuteMarkResResultResp == null || excuteMarkResResultResp.getResultCode() == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        if (!MarketingResConst.MK_EXCUTE_RS_SUC.equals(excuteMarkResResultResp.getResultCode())) {
            String error = excuteMarkResResultResp.getResultMsg() == null ? "接口返回为空" : excuteMarkResResultResp.getResultMsg();
            return ResultVO.error(error);
        }
        ResultVO vo = ResultVO.success();
        vo.setResultMsg(StringUtils.isEmpty(excuteMarkResResultResp.getResultMsg() == null) ? "操作成功" : excuteMarkResResultResp.getResultMsg());

        return vo;
    }

    private ResultVO dealZopHead(String token, String method, String version, Object params) {
        log.info(MarketingZopClientUtil.class.getName() + ", req={}, token={}, method={}, version={}", params == null ? "" : JSON.toJSON(params), token, method, version);
        ResponseResult result = ZopClientUtil.callRest(token, getZopUrl(), method, version, getTimeout(), params);
        log.info(MarketingZopClientUtil.class.getName() + ",resp={}", result == null ? "" : JSON.toJSON(result));
        //返回为空，能开返回为空
        if (result == null || !MarketingResConst.ZOP_SUC.equals(result.getRes_code())) {
            String error = result == null ? "能开返回接口返回为空" : result.getRes_message();
            return ResultVO.error(error);
        }
        if (result.getResult() == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        //再调试过程，result.getResult()可能直接返回错误信息
        if (result.getResult() instanceof String) {
            return ResultVO.error((String) result.getResult());
        }
        //能开返回成功时
        Map<String, Object> temp = (Map<String, Object>) result.getResult();
        if (temp == null) {
            String error = "接口返回为空";
            return ResultVO.error(error);
        }
        return ResultVO.success(temp);
    }



    public String getZoSecret() {
        return env.getProperty(MarketingResConst.ZOP_APPSECRET);
    }


    public String getZopUrl() {
        return env.getProperty(MarketingResConst.ZOP_URL);
    }

    public String getTimeout() {
        return env.getProperty(MarketingResConst.ZOP_TIMEOUT);
    }


}
