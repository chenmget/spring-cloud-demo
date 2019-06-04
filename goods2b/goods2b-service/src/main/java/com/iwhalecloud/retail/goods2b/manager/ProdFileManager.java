package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.entity.ProdFile;
import com.iwhalecloud.retail.goods2b.helper.FileHelper;
import com.iwhalecloud.retail.goods2b.mapper.ProdFileMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ProdFileManager {
    @Resource
    private ProdFileMapper prodFileMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @Value("${fdfs.imageUrl}")
    private String imageUrl;
    /**
     * 添加商品图片
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @param imageUrl 图片地址
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId + '_' + #subType.type")
    })
    public int addGoodsImage(String goodsId,FileConst.SubType subType,String imageUrl) {
        //缩略图
        final String thumbnailUrl = "";
        return this.addGoodsImage(goodsId,subType,imageUrl,thumbnailUrl);
    }

    /**
     * 添加商品图片
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @param imageUrl 图片地址
     * @param thumbnailUrl 缩略图
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId + '_' + #subType.type")
    })
    public int addGoodsImage(String goodsId,FileConst.SubType subType,String imageUrl,String thumbnailUrl) {

        ProdFile prodFile = new ProdFile();
        prodFile.setFileType(FileHelper.getFileType(imageUrl));
        prodFile.setSubType(subType.getType());
        prodFile.setFileUrl(replaceUrlPrefix(imageUrl));
        prodFile.setThumbnailUrl(replaceUrlPrefix(thumbnailUrl));
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
//    @Cacheable(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId")
//    public List<ProdFileDTO> queryGoodsImage(String goodsId) {
//        QueryWrapper wrapper = new QueryWrapper<>();
//        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
//        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);
//        wrapper.ne(ProdFile.FieldNames.subType.getTableFieldName(), "8");
//        wrapper.ne(ProdFile.FieldNames.subType.getTableFieldName(),"10");
//        return this.queryProdFiles(wrapper);
//    }

    public List<ProdFileDTO> queryGoodsImage(String goodsId) {
    	
    	List<ProdFileDTO> dtos = new ArrayList<ProdFileDTO>();
        List<ProdFileDTO> prodFiles = prodFileMapper.queryGoodsImage(goodsId);
        if (prodFiles != null) {
            for (ProdFileDTO prodFile : prodFiles) {
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
  
    public List<ProdFileDTO> queryGoodsImageHDdetail(String goodsId) {
    	List<ProdFileDTO> listProds = new ArrayList<ProdFileDTO>();
    	List<ProdFileDTO> listProd =  prodFileMapper.queryGoodsImageHDdetail(goodsId);
    	if (listProd != null) {
            for (ProdFileDTO prodFileDTO : listProd) {
                ProdFileDTO dto = new ProdFileDTO();
                BeanUtils.copyProperties(prodFileDTO,dto);
                String startFileUrl = dto.getFileUrl();
        		if(startFileUrl != null) {
        			startFileUrl = startFileUrl.replace("/CloudShelfNew", "");
        		}
                dto.setFileUrl(fullImageUrl(startFileUrl, imageUrl, true));
//                dto.setThreeDimensionsUrl(fullImageUrl(dto.getThreeDimensionsUrl(), imageUrl, true));
//                dto.setThumbnailUrl(fullImageUrl(dto.getThumbnailUrl(), imageUrl, true));

                listProds.add(dto);
            }
        }
    	
    	return listProds;
    }

    /**
     * 通过商品ID和子类型获取附件集合
     * @param goodsId 商品ID
     * @param subType 子类型
     * @link TargetType
     * @return 附件集合
     */
    @Cacheable(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId + '_' + #subType.type")
    public List<ProdFileDTO> queryGoodsImage(String goodsId,FileConst.SubType subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);
        wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(),subType.getType());

        return this.queryProdFiles(wrapper);
    }


    /**
     * 通过商品ID集合和子类型获取附件集合
     * @param goodsIds 商品ID集合
     * @param subType 子类型
     * @link TargetType
     * @return 附件集合
     */
    public List<ProdFileDTO> queryGoodsImage(List<String> goodsIds,FileConst.SubType subType) {
        if (goodsIds==null || goodsIds.size()<=0) {
            log.warn("ProdFileManager.queryGoodsImage goodsIds is null");
            return null;
        }
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.in(ProdFile.FieldNames.targetId.getTableFieldName(), goodsIds);
        wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(),subType.getType());

        return this.queryProdFiles(wrapper);
    }

    public ProdFileDTO queryGoodsImageHD(String goodsId){
    	ProdFileDTO prodFileDTO = prodFileMapper.queryGoodsImageHD(goodsId);
    	if(prodFileDTO != null) {
    		String startUrl = prodFileDTO.getFileUrl();
    		if(startUrl != null) {
    			startUrl = startUrl.replaceAll("/CloudShelfNew", "");
    		}
    		String fileUrl = fullImageUrl(startUrl, imageUrl, true);
    		prodFileDTO.setFileUrl(fileUrl);
    	}
    	return prodFileDTO;
    }
    
    
    /**
     * 拼接完整的地址
     * @param imagePath
     * @param showUrl
     * @param flag 为true时，拼接完整地址，为false时，是截取地址
     * @return
     */
    public static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (org.springframework.util.StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }
    
    /**
     * 根据商品ID删除图片
     * @param goodsId 商品ID
     * @return 删除的记录数
     */
    @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, allEntries = true)
    public int deleteByGoodsId(String goodsId) {
        QueryWrapper wrapper = new QueryWrapper<>();
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
    @Caching(evict = {
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#goodsId + '_' + #subType.type")
    })
    public int deleteByGoodsSubType(String goodsId,FileConst.SubType subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), FileConst.TargetType.GOODS_TARGET.getType());
        wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(),goodsId);
        wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(),subType.getType());
        return this.deleteProdFile(wrapper);
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

    /**
     * 添加图片
     * @param file
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#file.targetId"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#file.targetId + '_' + #file.subType"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#file.targetId + '_' + #file.subType + '_' + #file.targetType")
    })
    public Integer addFile(ProdFile file) {
        String fileUrl = replaceUrlPrefix(file.getFileUrl());
        String fileType = FileHelper.getFileType(file.getFileUrl());
        file.setFileUrl(fileUrl);
        file.setFileType(fileType);
        file.setCreateDate(new Date());
        return prodFileMapper.insert(file);
    }

    /**
     * 删除图片
     * @param targetId 商品ID
     * @param targetType 商品ID
     * @return 删除的记录数
     */
    @Caching(evict = {
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#targetId"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#targetId + '_' + #subType"),
            @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#targetId + '_' + #subType + '_' + #targetType")
    })
    public Integer deleteFile(String targetId, String targetType, String subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(targetId)){
            wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(), targetId);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(targetType)){
            wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), targetType);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(subType)){
            wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(), subType);
        }
        return this.deleteProdFile(wrapper);
    }

    /**
     * 查询图片
     * @param targetId
     * @param targetType
     * @param subType
     * @return 查询图片记录数
     */
    @Cacheable(value = GoodsConst.CACHE_NAME_PROD_FILE, key = "#targetId + '_' + #subType + '_' + #targetType")
    public List<ProdFileDTO> getFile(String targetId, String targetType, String subType) {
        QueryWrapper wrapper = new QueryWrapper<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(targetId)){
            wrapper.eq(ProdFile.FieldNames.targetId.getTableFieldName(), targetId);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(targetType)){
            wrapper.eq(ProdFile.FieldNames.targetType.getTableFieldName(), targetType);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(subType)){
            wrapper.eq(ProdFile.FieldNames.subType.getTableFieldName(), subType);
        }

        List<ProdFile> prodFiles = prodFileMapper.selectList(wrapper);
        if (null == prodFiles || prodFiles.isEmpty()){
            return null;
        }
        List<ProdFileDTO> dtos = new ArrayList<ProdFileDTO>(prodFiles.size());
        for (ProdFile prodFile : prodFiles) {
            ProdFileDTO dto = new ProdFileDTO();
            BeanUtils.copyProperties(prodFile,dto);
            dto.setFileUrl(attacheUrlPrefix(dto.getFileUrl()));
            dto.setThreeDimensionsUrl(attacheUrlPrefix(dto.getThreeDimensionsUrl()));
            dto.setThumbnailUrl(attacheUrlPrefix(dto.getThumbnailUrl()));

            dtos.add(dto);
        }
        return dtos;
    }
}
