package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.system.dto.NoticeDTO;
import com.iwhalecloud.retail.system.dto.request.NoticePagePersonalReq;
import com.iwhalecloud.retail.system.dto.request.NoticePageReq;
import com.iwhalecloud.retail.system.entity.Notice;
import com.iwhalecloud.retail.system.mapper.NoticeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class NoticeManager{
    @Resource
    private NoticeMapper noticeMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;



    /**
     * 添加一个 公告通知
     * @param notice
     * @return
     */
    public NoticeDTO insert(Notice notice){
        int resultInt = noticeMapper.insert(notice);
        if(resultInt > 0){
            NoticeDTO noticeDTO = new NoticeDTO();
            BeanUtils.copyProperties(notice, noticeDTO);
            return noticeDTO;
        }
        return null;
    }

    /**
     * 根据id查找单个 公告通知
     * @param noticeId
     * @return
     */
    public NoticeDTO getNoticeById(String noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        if(notice == null) {
            return null;
        }
        NoticeDTO noticeDTO = new NoticeDTO();
        BeanUtils.copyProperties(notice, noticeDTO);
        return noticeDTO;
    }

    /**
     * 更新
     * @param notice
     * @return
     */
    public int updateNotice(Notice notice){
        return noticeMapper.updateById(notice);
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    public Page<NoticeDTO> pageNotice(NoticePageReq req) {
        Page<NoticeDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<NoticeDTO> noticeDTOPage = noticeMapper.pageNotice(page, req);
        return noticeDTOPage;
    }

    /**
     * 个人的  公告通知 信息列表分页
     * @param req
     * @return
     */
    public Page<NoticeDTO> pagePersonalNotice(NoticePagePersonalReq req) {
        Page<NoticeDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<NoticeDTO> noticeDTOPage = noticeMapper.pagePersonalNotice(page, req);
        return noticeDTOPage;
    }
}
