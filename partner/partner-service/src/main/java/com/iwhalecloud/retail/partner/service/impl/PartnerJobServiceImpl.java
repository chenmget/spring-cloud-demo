package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.partner.service.ChannelViewSyncService;
import com.iwhalecloud.retail.partner.service.PartnerJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Service
@Slf4j
public class PartnerJobServiceImpl implements PartnerJobService {

    @Autowired
    ChannelViewSyncService channelViewSyncService;

    @Override
    public void syncBusinessEntity() {
        // 删除经营主体临时表数据
        channelViewSyncService.deleteBusinessEntityTempData();
        // 保存文件到临时表
        channelViewSyncService.syncBusinessEntity();
        // 将临时文件的数据解析到正式表
        channelViewSyncService.dealBusinessEntityData();
        Date endDate = new Date();
        log.info("SyncBusinessEntityJob run end.....{}", endDate);
    }

    @Override
    public void syncMerchant() {
        // 删除商家信息临时表的数据
        channelViewSyncService.deleteMerchantTempData();
        // 保存文件到临时表
        channelViewSyncService.syncMerchant();
        // 将临时文件的数据解析到正式表
        channelViewSyncService.dealMerchantData();
        Date endDate = new Date();
        log.info("SyncBusinessEntityJob run end.....{}", endDate);
    }
}
