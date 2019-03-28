package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreToFormalReq;
import com.iwhalecloud.retail.warehouse.entity.MktResStoreTemp;
import com.iwhalecloud.retail.warehouse.mapper.MktResStoreTempMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MktResStoreTempManager extends ServiceImpl<MktResStoreTempMapper, MktResStoreTemp> {
    @Resource
    private MktResStoreTempMapper mktResStoreTempMapper;


    public MktResStoreTemp getMktResStoreTempDTO(String mktResStoreId){
        return this.mktResStoreTempMapper.selectById(mktResStoreId);
    }


    public ResultVO addMktResStoreTemp(MktResStoreTemp addData){

        int i = this.mktResStoreTempMapper.insert(addData);
        ;
        if(i>0){
            return ResultVO.success();
        }else{
            return ResultVO.error("新增失败");
        }
    }

    public ResultVO updateMktResStoreTemp(MktResStoreTemp updateData){


        int i = this.mktResStoreTempMapper.updateMktResStoreTemp(updateData);
        if(i>0){
            return ResultVO.success();
        }else{
            return ResultVO.error("更新失败");
        }
    }

    public Page<MktResStoreTempDTO> listSynMktResStoreTempDTOList(SynMarkResStoreToFormalReq req){

        Page<MktResStoreTempDTO> page = new Page<MktResStoreTempDTO>(req.getPageNo(), req.getPageSize());

        return mktResStoreTempMapper.listSynMktResStoreTempDTOList(page, req);

    }
    public int updateMktResStoreTempSyn(List<String> list){
        return mktResStoreTempMapper.updateMktResStoreTempSyn(list);
    }
    
    
}
