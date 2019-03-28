package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.req.FileGetReq;
import com.iwhalecloud.retail.goods2b.manager.ProdFileManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chengxu
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ProdFileManager typeManager;

    @Override
    public ResultVO<List<ProdFileDTO>> getFile(FileGetReq fileGetReq){
        String subType = fileGetReq.getSubType();
        String targetId = fileGetReq.getTargetId();
        String targetType = fileGetReq.getTargetType();
        return ResultVO.success(typeManager.getFile(targetId, targetType, subType));
    }

}