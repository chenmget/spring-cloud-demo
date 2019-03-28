package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreToFormalReq;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.SynMarkResStoreResp;
import com.iwhalecloud.retail.warehouse.service.MktResStoreTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/11 10:22
 */
@Service
@Slf4j
public class MktResStoreTempOpenServiceImpl implements MktResStoreTempService {

    @Autowired
    private MktResStoreTempService mktResStoreTempService;

    @Override
    public ResultVO<SynMarkResStoreResp> synMarkResStore(SynMarkResStoreReq req) {
        log.info(MktResStoreTempOpenServiceImpl.class.getName()+".synMarkResStore req={}", JSON.toJSONString(req));
        ResultVO<SynMarkResStoreResp> resultVO = mktResStoreTempService.synMarkResStore(req);
        log.info(MktResStoreTempOpenServiceImpl.class.getName()+".synMarkResStore resp={}", JSON.toJSONString(resultVO));
        return resultVO;
    }

    @Override
    public ResultVO addMarkResStore(SynMarkResStoreReq req) {
        return mktResStoreTempService.addMarkResStore(req);
    }

    @Override
    public ResultVO updateMarkResStore(SynMarkResStoreReq req) {
        return mktResStoreTempService.updateMarkResStore(req);
    }

    @Override
    public MktResStoreTempDTO getMktResStoreTempDTO(String mktResStoreId) {
        return mktResStoreTempService.getMktResStoreTempDTO(mktResStoreId);
    }

    @Override
    public ResultVO synTempToMktResStore() {
        return mktResStoreTempService.synTempToMktResStore();
    }
    @Override
    public List<MktResStoreTempDTO> listSynMktResStoreTempDTOList(SynMarkResStoreToFormalReq req){
        return mktResStoreTempService.listSynMktResStoreTempDTOList(req);
    }

    @Override
    public ResultVO synTempToMktResStoreBatch(List<MktResStoreTempDTO> dataList) {
        return mktResStoreTempService.synTempToMktResStoreBatch(dataList);
    }
}
