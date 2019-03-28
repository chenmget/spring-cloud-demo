package com.iwhalecloud.retail.system.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.request.NoticeUpdateReq;
import com.iwhalecloud.retail.system.service.NoticeService;
import com.iwhalecloud.retail.system.service.workflow.NoticeAuditNotPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: lin.wenhui@iwhalecloud.com
 * @Date: 2019/3/18
 * @Description:
 */

@Slf4j
@Service
public class NoticeAuditNotPassServiceImpl implements NoticeAuditNotPassService {

    @Reference
    private NoticeService noticeService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("NoticeAuditNotPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null||StringUtils.isEmpty(params.getBusinessId())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }

        NoticeUpdateReq req = new NoticeUpdateReq();
        req.setNoticeId(params.getBusinessId());
        //审核不通过
        req.setStatus(SystemConst.NoticeStatusEnum.AUDIT_FAILED.getCode());

        return noticeService.updateNotice(req);
    }
}
