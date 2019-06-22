package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.entity.MktResItmsReturnRec;
import com.iwhalecloud.retail.warehouse.mapper.MktResItmsReturnRecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MktResItmsReturnRecManager{
    @Resource
    private MktResItmsReturnRecMapper mktResItmsReturnRecMapper;


    
    public void batchAddMKTReturnInfo(List<MktResItmsReturnRec> mktResItmsReturnRec){
        mktResItmsReturnRecMapper.batchAddMKTReturnInfo(mktResItmsReturnRec);
    }
}
