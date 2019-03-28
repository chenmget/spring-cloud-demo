package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.req.FileGetReq;

import java.util.List;

public interface FileService {
    /**
     * 查询图片
     * @param fileGetReq
     * @return 查询图片记录数
     */
    public ResultVO<List<ProdFileDTO>> getFile(FileGetReq fileGetReq);
}