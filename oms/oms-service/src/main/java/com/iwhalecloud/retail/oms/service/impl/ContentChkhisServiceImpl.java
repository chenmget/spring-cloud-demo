package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentChkhisDTO;
import com.iwhalecloud.retail.oms.entity.ContentChkhis;
import com.iwhalecloud.retail.oms.manager.ContentBaseManager;
import com.iwhalecloud.retail.oms.manager.ContentChkhisManager;
import com.iwhalecloud.retail.oms.service.ContentChkhisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class ContentChkhisServiceImpl implements ContentChkhisService {

    @Autowired
    private ContentChkhisManager contentChkhisManager;

    @Autowired
    private ContentBaseManager contentBaseManager;

    @Override
    @Transactional
    public int insertContentChkhis(ContentChkhisDTO contentChkhisDto) {
        ContentChkhis contentChkhis = new ContentChkhis();
        BeanUtils.copyProperties(contentChkhisDto,contentChkhis);

        return  contentChkhisManager.insertContentChkhis(contentChkhis);
    }

    /**
     * 审核服务
     * 入参：传内容ID、审核人员工号、审核结果、审核意见
     * 逻辑应该要改下：
     * 先判断内容状态是不是待审核，如果是待审核，再去更新审核历史。同时内容表中那条内容记录要加锁。更新完审核历史后，再更新内容状态。
     * @param contentChkhisDTO
     * @return
     */
    @Override
    @Transactional
    public ResultVO contentCheck(ContentChkhisDTO contentChkhisDTO) {
        ResultVO resultVO = new ResultVO();
        Long contentId = contentChkhisDTO.getContentid();
        ContentBaseDTO contentBase = contentBaseManager.queryContentBaseById(contentId);
        if(contentBase!=null){
            Boolean auditStatus = String.valueOf(OmsConst.ContentStatusEnum.FOR_AUDIT.getCode()).equals(String.valueOf(contentBase.getStatus()));
            if(auditStatus){
                synchronized (this) {
                    try {
                        ContentChkhis contentChkhis = new ContentChkhis();
                        BeanUtils.copyProperties(contentChkhisDTO,contentChkhis);
                        contentChkhisManager.updateContentChkhis(contentChkhis);
                        Boolean result = String.valueOf(OmsConst.ValidStatusEnum.NOT_VALID.getCode()).equals(String.valueOf(contentChkhis.getResult()));
                        if(result){
                            contentBase.setStatus(OmsConst.ContentStatusEnum.NOT_PASS_AUDIT.getCode());
                        }else{
                            contentBase.setStatus(OmsConst.ContentStatusEnum.PASS_AUDIT.getCode());

                        }
                        contentBaseManager.updateContentBase(contentBase);
                        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                        resultVO.setResultMsg("提交审核成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        resultVO.setResultMsg(e.getMessage());
                    }
                }
            }
        }
        return resultVO;
    }


}
