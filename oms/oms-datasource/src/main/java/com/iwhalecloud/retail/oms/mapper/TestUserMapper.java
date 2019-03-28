package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.TestUserDTO;
import com.iwhalecloud.retail.oms.entity.UserCopyTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestUserMapper extends BaseMapper<UserCopyTest> {

    List<TestUserDTO> find();

    IPage<TestUserDTO> selectPageVo(Page page);
}
