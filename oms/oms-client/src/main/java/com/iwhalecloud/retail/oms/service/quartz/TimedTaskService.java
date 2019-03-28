package com.iwhalecloud.retail.oms.service.quartz;

import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.resquest.quartz.QuartzJobDTO;

public interface TimedTaskService {
    //    	 * 查询列表
    CommonResultResp queryList(QuartzJobDTO quartzJobDTO);

    //	 * 查询实例列表
    CommonResultResp jobSchedulerList(QuartzJobDTO quartzJobDTO);

    //跳转编辑页面
    CommonResultResp edit(QuartzJobDTO quartzJobDTO);

    //新增
    CommonResultResp saveAdd(QuartzJobDTO quartzJobDTO);

    //保存更新
    CommonResultResp saveEdit(QuartzJobDTO quartzJobDTO);

    //暂停定时任务
    CommonResultResp pauseJob(QuartzJobDTO quartzJobDTO);

    //恢复定时任务
    CommonResultResp resumeJob(QuartzJobDTO quartzJobDTO);

    //立即执行定时任务
    CommonResultResp triggerJob(QuartzJobDTO quartzJobDTO);

    //删除定时任务
    CommonResultResp delete(QuartzJobDTO quartzJobDTO);
}
