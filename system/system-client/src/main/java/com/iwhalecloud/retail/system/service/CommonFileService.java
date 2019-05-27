package com.iwhalecloud.retail.system.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;

/**
 * CommonFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
public interface CommonFileService{

    /**
     * 保存通用附件
     * @param req
     * @return
     */
    ResultVO saveCommonFile(CommonFileDTO req);

    /**
     * 获取单个通用附件
     * @param req
     * @return
     */
    ResultVO<CommonFileDTO> getCommonFile(CommonFileDTO req);

    /**
     * 获取单个通用附件
     * @param fileId
     * @return
     */
    ResultVO<CommonFileDTO> getCommonFileById(String fileId);
}