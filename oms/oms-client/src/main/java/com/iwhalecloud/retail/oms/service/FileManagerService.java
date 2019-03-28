package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.resquest.FileManagerDTO;

import java.util.List;

public interface FileManagerService {

    /**
     * 上传图片
     */
    CommonResultResp uploadImage(FileManagerDTO file);

    /**
     * 批量上传，
     */
    CommonResultResp uploadImage(List<FileManagerDTO> file);

    /**
     * 删除图片
     */
    CommonResultResp deleteImg(FileManagerDTO file);
}
