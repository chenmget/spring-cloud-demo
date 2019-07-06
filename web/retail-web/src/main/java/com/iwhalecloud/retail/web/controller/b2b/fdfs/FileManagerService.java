package com.iwhalecloud.retail.web.controller.b2b.fdfs;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.dto.FileManagerDTO;

import java.util.List;

public interface FileManagerService {

    /**
     * 上传图片
     */
    ResultVO uploadImage(FileManagerDTO file);

    /**
     * 批量上传，
     */
    ResultVO uploadImage(List<FileManagerDTO> file);

    /**
     * 删除图片
     */
    ResultVO deleteImg(FileManagerDTO file);
}
