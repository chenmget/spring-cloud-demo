package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.NoticeDTO;
import com.iwhalecloud.retail.system.dto.request.NoticePagePersonalReq;
import com.iwhalecloud.retail.system.dto.request.NoticePageReq;
import com.iwhalecloud.retail.system.dto.request.NoticeSaveReq;
import com.iwhalecloud.retail.system.dto.request.NoticeUpdateReq;

public interface NoticeService{

    /**
     * 添加一个 公告通知
     * @param req
     * @return
     */
    ResultVO<NoticeDTO> saveNotice(NoticeSaveReq req);

    /**
     * 获取一个 公告通知
     * @param noticeId
     * @return
     */
    ResultVO<NoticeDTO> getNoticeById(String noticeId);

    /**
     * 编辑 公告通知 信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateNotice(NoticeUpdateReq req);

    /**
     * 公告通知 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<NoticeDTO>> pageNotice(NoticePageReq pageReq);

    /**
     * 个人的  公告通知 信息列表分页
     * 主要查 publishType是系统类  +  个人的消息
     * @param pageReq
     * @return
     */
    ResultVO<Page<NoticeDTO>> pagePersonalNotice(NoticePagePersonalReq pageReq);

}