package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.oms.dto.ContentVedioctrDTO;
import com.iwhalecloud.retail.oms.entity.ContentVedioctr;
import com.iwhalecloud.retail.oms.mapper.ContentVedioctrMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class ContentVedioctrManager{
    @Resource
    private ContentVedioctrMapper contentVedioctrMapper;


    /**
     * 新增
     * @param contentVedioctrDTO
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    public ContentVedioctrDTO insert(ContentVedioctrDTO contentVedioctrDTO){
        ContentVedioctr contentVedioctr = new ContentVedioctr();
        BeanUtils.copyProperties(contentVedioctrDTO, contentVedioctr);
        contentVedioctr.setGmtCreat(new Date());
        contentVedioctr.setGmtModify(new Date());
        contentVedioctrMapper.insert(contentVedioctr);
        BeanUtils.copyProperties(contentVedioctr, contentVedioctrDTO);
        return contentVedioctrDTO;
    }

    /**
     * 停用内容轮播
     * @param StorageNum
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    public int display(String StorageNum){
        return contentVedioctrMapper.display(StorageNum);
    }

    /**
     * 根据厅店Id获得轮播设置
     * @param StorageNum
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    public List<ContentVedioctr> selectByStorageNum(String StorageNum, Long isPlayback){

        QueryWrapper<ContentVedioctr> queryWrapper = new QueryWrapper<ContentVedioctr>();
        queryWrapper.eq(ContentVedioctr.FieldNames.storageNum.getTableFieldName(), StorageNum);
        queryWrapper.eq(ContentVedioctr.FieldNames.isPlayback.getTableFieldName(), isPlayback);
        queryWrapper.orderByDesc(ContentVedioctr.FieldNames.gmtModify.getTableFieldName());
        return contentVedioctrMapper.selectList(queryWrapper);
    }

}
