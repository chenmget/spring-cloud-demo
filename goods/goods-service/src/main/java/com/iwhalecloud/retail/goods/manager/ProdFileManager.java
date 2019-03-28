package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.common.FileConst;
import com.iwhalecloud.retail.goods.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods.entity.ProdFile;
import com.iwhalecloud.retail.goods.helper.FileHelper;
import com.iwhalecloud.retail.goods.mapper.ProdFileMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ProdFileManager {
    @Resource
    private ProdFileMapper prodFileMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;


    /**
     * 添加商品图片
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @param imageUrl 图片地址
     * @return
     */
    public int addGoodsImage(String goodsId,FileConst.SubType subType,String imageUrl) {

        ProdFile prodFile = new ProdFile();
        prodFile.setFileType(FileHelper.getFileType(imageUrl));
        prodFile.setSubType(subType.getType());
        prodFile.setFileUrl(replaceUrlPrefix(imageUrl));

        prodFile.setTargetId(goodsId);
        prodFile.setTargetType(FileConst.TargetType.GOODS_TARGET.getType());
        prodFile.setCreateDate(new Date());
        return prodFileMapper.insert(prodFile);
    }


    /**
     * 通过商品ID获取附件集合
     * @param goodsId 商品ID
     * @return 附件集合
     */
    public List<ProdFileDTO> queryGoodsImage(String goodsId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        //wrapper.eq(ProdFile.FieldNames.fileType.getTableFieldName(),FileConst.FileType.IMAGE_TYPE.getType());
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);

        return this.queryProdFiles(wrapper);
    }


    /**
     * 通过商品ID和子类型获取附件集合
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @return 附件集合
     */
    public List<ProdFileDTO> queryGoodsImage(String goodsId,FileConst.SubType subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        //wrapper.eq(ProdFile.FieldNames.fileType.getTableFieldName(),FileConst.FileType.IMAGE_TYPE.getType());
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);
        wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(),subType.getType());

        return this.queryProdFiles(wrapper);
    }


    /**
     * 根据商品ID删除图片
     * @param goodsId 商品ID
     * @return 删除的记录数
     */
    public int deleteByGoodsId(String goodsId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        //wrapper.eq(ProdFile.FieldNames.fileType.getTableFieldName(),FileConst.FileType.IMAGE_TYPE.getType());
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);

        return this.deleteProdFile(wrapper);
    }

    /**
     * 通过商品ID和子类型删除图片类的附件
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @return 删除的记录数
     */
    public int deleteByGoodsSubType(String goodsId,FileConst.SubType subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        //wrapper.eq(ProdFile.FieldNames.fileType.getTableFieldName(),FileConst.FileType.IMAGE_TYPE.getType());
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);
        wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(),subType.getType());
        return this.deleteProdFile(wrapper);
    }



    /**
     * 根据主键ID删除附件
     * @param fileId 附件ID
     * @return 删除记录的条数
     */
    public int deleteById(String fileId) {
        return this.prodFileMapper.deleteById(fileId);
    }

    /**
     * 根据条件删除附件
     * @param queryWrapper 查询条件
     * @return 删除记录的条数
     */
    private int deleteProdFile(QueryWrapper<ProdFile> queryWrapper) {
        return this.prodFileMapper.delete(queryWrapper);
    }

    /**
     * 根据条件查询附件集合
     * @param queryWrapper ProdFile对象wraaper
     * @return 附件集合
     */
    private List<ProdFileDTO> queryProdFiles(QueryWrapper<ProdFile> queryWrapper) {
        List<ProdFileDTO> dtos = new ArrayList<ProdFileDTO>();
        List<ProdFile> prodFiles = this.prodFileMapper.selectList(queryWrapper);
        if (prodFiles != null) {
            for (ProdFile prodFile : prodFiles) {
                ProdFileDTO dto = new ProdFileDTO();
                BeanUtils.copyProperties(prodFile,dto);
                dto.setFileUrl(attacheUrlPrefix(dto.getFileUrl()));
                dto.setThreeDimensionsUrl(attacheUrlPrefix(dto.getThreeDimensionsUrl()));
                dto.setThumbnailUrl(attacheUrlPrefix(dto.getThumbnailUrl()));

                dtos.add(dto);
            }
        }
        return dtos;
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
