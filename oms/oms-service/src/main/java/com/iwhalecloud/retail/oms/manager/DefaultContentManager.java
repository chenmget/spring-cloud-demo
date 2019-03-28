package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.DefaultContentDTO;
import com.iwhalecloud.retail.oms.entity.ContentBase;
import com.iwhalecloud.retail.oms.mapper.ContentBaseMapper;
import com.iwhalecloud.retail.oms.mapper.DefaultContentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class DefaultContentManager{
    @Resource
    private DefaultContentMapper defaultContentMapper;

    @Resource
    private ContentBaseMapper contentBaseMapper;

    /**
     * 查询默认内容
     * @param id
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    public DefaultContentDTO selectById(String id) {

        DefaultContentDTO defaultContentDTO = new DefaultContentDTO();
        BeanUtils.copyProperties(defaultContentMapper.selectById(id), defaultContentDTO);
        return defaultContentDTO;
    }

    /**
     * 根据默认内容 创建内容信息
     * @param id
     * @param userId
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    public ContentBaseDTO CreateContentBase(String id, String userId) throws ParseException {

        DefaultContentDTO defaultContentDTO = selectById(id);
        ContentBase contentBase = new ContentBase();
        contentBase.setTitle(defaultContentDTO.getContentTittle());
        contentBase.setDesp(defaultContentDTO.getContentBrief());
        contentBase.setCopywriter(defaultContentDTO.getContentDetail());
        contentBase.setStatus(OmsConst.ContentStatusEnum.NOT_AUDIT.getCode());
        contentBase.setOprId(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        contentBase.setEffDate(new Date());
        contentBase.setExpDate(sdf.parse("2199-12-12"));
        contentBase.setUpdDate(new Date());
        contentBaseMapper.insert(contentBase);
        ContentBaseDTO contentBaseDTO = new ContentBaseDTO();
        BeanUtils.copyProperties(contentBase, contentBaseDTO);
        return contentBaseDTO;
    }
    
}
