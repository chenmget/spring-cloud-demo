package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecGroupDTO;
import com.iwhalecloud.retail.goods2b.manager.AttrSpecGroupManager;
import com.iwhalecloud.retail.goods2b.service.AttrSpecGroupService;
import com.iwhalecloud.retail.goods2b.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
@Slf4j
public class AttrSpecGroupServiceImpl implements AttrSpecGroupService {

    @Autowired
    private AttrSpecGroupManager AttrSpecGroupManager;

    @Override
    public ResultVO<List<AttrSpecGroupDTO>> listAttrSpecGroupByCondition(AttrSpecGroupDTO condition) {
        return ResultVOUtils.genQueryResultVO(AttrSpecGroupManager.listByCondition(condition));
    }

    @Override
    public ResultVO addAttrSpecGroup(AttrSpecGroupDTO entity) {
        return ResultVOUtils.genAduResultVO(AttrSpecGroupManager.addAttrSpecGroup(entity));
    }

    @Override
    public ResultVO deleteAttrSpecGroup(String id) {
        return ResultVOUtils.genAduResultVO(AttrSpecGroupManager.deleteAttrSpecGroup(id));
    }

    @Override
    public ResultVO updateAttrSpecGroup(AttrSpecGroupDTO entity) {
        return ResultVOUtils.genAduResultVO(AttrSpecGroupManager.updateAttrSpecGroup(entity));
    }

}