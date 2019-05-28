package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonFileDTO;
import com.iwhalecloud.retail.system.entity.CommonFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.system.mapper.CommonFileMapper;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class CommonFileManager{

    @Resource
    private CommonFileMapper commonFileMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    public int saveCommonFile(CommonFileDTO req) {
        CommonFile commonFile = new CommonFile();
        BeanUtils.copyProperties(req, commonFile);
        commonFile.setFileUrl(replaceUrlPrefix(commonFile.getFileUrl()));
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
        commonFileDTO.setFileUrl(attacheUrlPrefix(commonFileDTO.getFileUrl()));
        return commonFileDTO;
    }

    public List<CommonFileDTO> listCommonFile(CommonFileDTO req) {
        QueryWrapper<CommonFile> queryWrapper = new QueryWrapper<CommonFile>();
        queryWrapper.in(CommonFile.FieldNames.objId.getTableFieldName(), req.getObjId().split(","));
        if (!StringUtils.isEmpty(req.getFileType())) {
            queryWrapper.eq(CommonFile.FieldNames.fileType.getTableFieldName(), req.getFileType());
        }
        if (!StringUtils.isEmpty(req.getFileClass())) {
            queryWrapper.eq(CommonFile.FieldNames.fileClass.getTableFieldName(), req.getFileClass());
        }
        List<CommonFile> list = commonFileMapper.selectList(queryWrapper);
        List<CommonFileDTO> result = Lists.newArrayList();
        for (CommonFile commonFile : list) {
            CommonFileDTO commonFileDTO = new CommonFileDTO();
            BeanUtils.copyProperties(commonFile, commonFileDTO);
            commonFileDTO.setFileUrl(attacheUrlPrefix(commonFileDTO.getFileUrl()));
            result.add(commonFileDTO);
        }
        return result;
    }

    public CommonFileDTO getCommonFileById(String fileId) {
        CommonFile commonFile = commonFileMapper.selectById(fileId);
        if (null == commonFile) {
            return null;
        }
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        BeanUtils.copyProperties(commonFile, commonFileDTO);
        commonFileDTO.setFileUrl(attacheUrlPrefix(commonFileDTO.getFileUrl()));
        return commonFileDTO;
    }

    public List<CommonFileDTO> getCommonFileByIds(String[] fileIds) {
        QueryWrapper<CommonFile> queryWrapper = new QueryWrapper<CommonFile>();
        queryWrapper.in(CommonFile.FieldNames.fileId.getTableFieldName(), fileIds);
        List<CommonFile> list = commonFileMapper.selectList(queryWrapper);
        List<CommonFileDTO> result = Lists.newArrayList();
        for (CommonFile commonFile : list) {
            CommonFileDTO commonFileDTO = new CommonFileDTO();
            BeanUtils.copyProperties(commonFile, commonFileDTO);
            commonFileDTO.setFileUrl(attacheUrlPrefix(commonFileDTO.getFileUrl()));
            result.add(commonFileDTO);
        }
        return result;
    }

    /**
     * 统一替换附件的前缀，多个图片用逗号，隔开。比如http://xxx.com/group1/xx.jpg替换后为group1/xx.jpg
     * @param  originalUrls 需要替换前缀的url
     * @return 替换后的字符串
     */
    private String replaceUrlPrefix(String originalUrls) {
        if (StringUtils.isEmpty(originalUrls)) {
            return "";
        }

        return originalUrls.replaceAll(dfsShowIp,"");
    }


    /**
     * 统一增加附件的前缀，多个用逗号，隔开。比如group1/xx.jpg,group1/yy.jpg，增加前缀后http://xxx.com/group1/xx.jpg,http://xxx.com/group1/yy.jpg
     * @param originalUrls 需要增加前缀的url
     * @return
     */
    private String attacheUrlPrefix(String originalUrls) {
        if (StringUtils.isEmpty(originalUrls)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String[] urls = StringUtils.tokenizeToStringArray(originalUrls,",");
        for (String url : urls) {
            if (!url.startsWith("http")) {
                sb.append(",").append(dfsShowIp).append(url);
            } else {
                sb.append(",").append(url);
            }
        }
        return sb.substring(1).toString();
    }
}
