package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBaseByObjTypePageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePTPageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePageReq;
import com.iwhalecloud.retail.oms.entity.ContentBase;
import com.iwhalecloud.retail.oms.mapper.ContentBaseMapper;
import com.iwhalecloud.retail.oms.mapper.ContentMaterialMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ContentBaseManager{
    @Resource
    private ContentBaseMapper contentBaseMapper;

    @Resource
    private ContentMaterialMapper contentMaterialMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    /**
     * 根据在页面上选择的目录、内容类型、内容状态、标签以及输入的关键字等查询内容
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    public Page<ContentBaseDTO> qryContentBasePageList(ContentBasePageReq pageReq) {
        Page<ContentBaseDTO> page = new Page<ContentBaseDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        if (null != pageReq.getStatus() && pageReq.getStatus().size() == 0)
        {
            pageReq.setStatus(null);
        }
        if (null != pageReq.getTagIds() && pageReq.getTagIds().size() == 0)
        {
            pageReq.setTagIds(null);
        }
        if (null != pageReq.getTypes() && pageReq.getTypes().size() == 0)
        {
            pageReq.setTypes(null);
        }
        return contentBaseMapper.qryContentBasePageList(page, pageReq);
    }

    /**
     * @Author Wu.LiangHang
     * @Description
     * @Date 2018/11/13 19:23
     * @Param
     * @return
     **/
    public Page<ContentBaseDTO> qryContentBasePicsetOrTextPageList(ContentBasePTPageReq pageReq) {
        Page<ContentBaseDTO> page = new Page<ContentBaseDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        page = contentBaseMapper.qryContentBasePicsetOrTextPageList(page, pageReq);
        //暂时不set素材
//        for (ContentBaseDTO contentBaseDTO: page.getRecords())
//        {
//            List<ContentMaterialDTO> contentMaterials = contentMaterialMapper.queryContentMaterialList(contentBaseDTO.getContentId());
//            contentBaseDTO.setContentMaterials(contentMaterials);
//        }
        return page;
    }

    public ContentBaseDTO insertContentBase(ContentBaseDTO contentBaseDTO){
        ContentBase contentBase = new ContentBase();
        BeanUtils.copyProperties(contentBaseDTO,contentBase);
        contentBaseMapper.insert(contentBase);
        BeanUtils.copyProperties(contentBase,contentBaseDTO);
        return contentBaseDTO;
    }


    public ContentBaseDTO queryContentBaseById(Long contentId){
        ContentBase contentBase = contentBaseMapper.selectById(contentId);
        ContentBaseDTO contentBaseDTO = new ContentBaseDTO();
        if(contentBase == null){
            contentBaseDTO.setContentId(contentId);
        }
        BeanUtils.copyProperties(contentBase,contentBaseDTO);
        return contentBaseDTO;
    }

    /**
     * 根据条件查询内容列表
     * @param contentBaseDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    public List<ContentBaseDTO> qryContentBaseList(ContentBaseDTO contentBaseDTO) {

        return contentBaseMapper.qryContentBaseList(contentBaseDTO);
    }

    public ContentBaseDTO queryContentBase(ContentBaseDTO contentBaseDTO){
        ContentBaseDTO ContentBaseRespDTO = new ContentBaseDTO();
        Long contentId = contentBaseDTO.getContentId();
        List<ContentBaseDTO> contentBaseList = contentBaseMapper.queryContentBase(contentId);
        if(contentBaseList!=null&&contentBaseList.size()>0){
            ContentBaseRespDTO = contentBaseList.get(0);
        }
        return ContentBaseRespDTO;
    }


    /**
     * 查询失效内容列表
     * @param days
     * @return
     * @author Ji.kai
     * @date 2018/10/30 15:27
     */
    public List<ContentBaseDTO> getExpContentBases(int days) {
        return contentBaseMapper.getExpContentBases(days);
    }
    /**
     * 内容失效
     * @param
     * @return
     * @author Ji.kai
     * @date 2018/11/2 15:27
     */
    public int invalidateContentBase() {
        return contentBaseMapper.invalidateContentBase();
    }

    public int updateContentBase(ContentBaseDTO contentBaseDTO){
        return  contentBaseMapper.updateContentBase(contentBaseDTO);
    }

    public int updateContentBaseForEdit(ContentBaseDTO contentBaseDTO){
        ContentBase contentBase = new ContentBase();
        BeanUtils.copyProperties(contentBaseDTO, contentBase);
        return contentBaseMapper.updateById(contentBase);
    }

    /**
     * 查询条件根据对象类型 内容列表分页查询接口
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    public Page<ContentBaseDTO> qryContentBaseByObjTypePageList(ContentBaseByObjTypePageReq pageReq) {
        Page<ContentBaseDTO> page = new Page<ContentBaseDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        return contentBaseMapper.qryContentBaseByObjTypePageList(page, pageReq);
    }
}
