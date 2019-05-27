package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.manager.CommonFileManager;
import com.iwhalecloud.retail.system.service.CommonFileService;

/**
 * CommonFile
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Service
public class CommonFileServiceImpl implements CommonFileService {

    @Autowired
    private CommonFileManager commonFileManager;


    @Override
    public ResultVO saveCommonFile(CommonFileDTO req) {
        int ret = commonFileManager.saveCommonFile(req);
        if (ret > 0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }

    @Override
    public ResultVO<CommonFileDTO> getCommonFile(CommonFileDTO req) {
        CommonFileDTO commonFileDTO = commonFileManager.getCommonFile(req);
        if (null == commonFileDTO) {
            return ResultVO.error();
        }
        return ResultVO.success(commonFileDTO);
    }

    @Override
    public ResultVO<CommonFileDTO> getCommonFileById(String fileId) {
        CommonFileDTO commonFileDTO = commonFileManager.getCommonFileById(fileId);
        if (null == commonFileDTO) {
            return ResultVO.error();
        }
        return ResultVO.success(commonFileDTO);
    }
}