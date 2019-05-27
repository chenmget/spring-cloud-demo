package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;
import com.iwhalecloud.retail.system.entity.CommonFile;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.system.mapper.CommonFileMapper;

@Component
public class CommonFileManager{
    @Resource
    private CommonFileMapper commonFileMapper;

    public int saveCommonFile(CommonFileDTO req) {
        CommonFile commonFile = new CommonFile();
        BeanUtils.copyProperties(req, commonFile);
        return commonFileMapper.insert(commonFile);
    }

    public CommonFileDTO getCommonFile(CommonFileDTO req) {
        QueryWrapper<CommonFile> queryWrapper = new QueryWrapper<CommonFile>();
        queryWrapper.eq(CommonFile.FieldNames.objId.getTableFieldName(), req.getObjId());
        queryWrapper.eq(CommonFile.FieldNames.fileType.getTableFieldName(), req.getFileType());
        queryWrapper.eq(CommonFile.FieldNames.fileClass.getTableFieldName(), req.getFileClass());
        CommonFile commonFile = commonFileMapper.selectOne(queryWrapper);
        if (null == commonFile) {
            return null;
        }
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        BeanUtils.copyProperties(commonFile, commonFileDTO);
        return commonFileDTO;
    }

    public CommonFileDTO getCommonFileById(String fileId) {
        CommonFile commonFile = commonFileMapper.selectById(fileId);
        if (null == commonFile) {
            return null;
        }
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        BeanUtils.copyProperties(commonFile, commonFileDTO);
        return commonFileDTO;
    }
    
}
