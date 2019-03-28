package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountUpdateReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantAccountSaveResp;
import com.iwhalecloud.retail.partner.entity.MerchantAccount;
import com.iwhalecloud.retail.partner.manager.MerchantAccountManager;
import com.iwhalecloud.retail.partner.service.MerchantAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Component("merchantAccountService")
public class MerchantAccountServiceImpl implements MerchantAccountService {

    @Autowired
    private MerchantAccountManager merchantAccountManager;

    /**
     * 添加一个 商家账号
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<MerchantAccountDTO> saveMerchantAccount(MerchantAccountSaveReq req) {
        log.info("MerchantAccountServiceImpl.saveMerchantAccount req={}", JSON.toJSONString(req));
        MerchantAccountSaveResp resp = new MerchantAccountSaveResp();
        MerchantAccount merchantAccount = new MerchantAccount();
        BeanUtils.copyProperties(req, merchantAccount);
        MerchantAccountDTO merchantAccountDTO = merchantAccountManager.insert(merchantAccount);
        log.info("MerchantAccountServiceImpl.saveMerchantAccount merchantAccountManager.insert req={} resp={}", JSON.toJSONString(merchantAccount), JSON.toJSONString(merchantAccountDTO));
        if (null == merchantAccountDTO) {
            return ResultVO.error("新增商家账号信息失败");
        }
        return ResultVO.success(merchantAccountDTO);
    }

    /**
     * 获取一个 商家账号
     *
     * @param accountId
     * @return
     */
    @Override
    public ResultVO<MerchantAccountDTO> getMerchantAccountById(String accountId) {
        log.info("MerchantAccountServiceImpl.getMerchantAccountById(), 入参accountId={} ", accountId);
        MerchantAccountDTO merchantAccountDTO = merchantAccountManager.getMerchantAccountById(accountId);
        log.info("MerchantAccountServiceImpl.getMerchantAccountById(), 出参manufacturerDTO={} ", merchantAccountDTO);
        return ResultVO.success(merchantAccountDTO);
    }

    /**
     * 编辑 商家账号 信息
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateMerchantAccount(MerchantAccountUpdateReq req) {
        log.info("MerchantAccountServiceImpl.updateMerchantAccount(), 入参MerchantAccountUpdateReq={} ", req);
        MerchantAccount merchantAccount = new MerchantAccount();
        BeanUtils.copyProperties(req, merchantAccount);
        int result = merchantAccountManager.updateMerchantAccount(merchantAccount);
        log.info("MerchantAccountServiceImpl.updateMerchantAccount(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0) {
            return ResultVO.error("编辑商家账号信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 删除 商家账号
     *
     * @param accountId
     * @return
     */
    @Override
    public ResultVO<Integer> deleteMerchantAccountById(String accountId) {
        log.info("MerchantAccountServiceImpl.deleteMerchantAccountById(), 入参accountId={} ", accountId);
        int result = merchantAccountManager.deleteMerchantAccountById(accountId);
        log.info("MerchantAccountServiceImpl.deleteMerchantAccountById(), 出参对象(删除影响数据条数）={} ", result);
        if (result <= 0) {
            return ResultVO.error("删除商家账号信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 商家账号信息列表
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<MerchantAccountDTO>> listMerchantAccount(MerchantAccountListReq req) {
        log.info("MerchantAccountServiceImpl.listMerchantAccount(), 入参MerchantAccountListReq={} ", req);
        List<MerchantAccount> merchantAccountList = merchantAccountManager.listMerchantAccount(req);
        List<MerchantAccountDTO> list = new ArrayList<>();
        for (MerchantAccount merchantAccount : merchantAccountList) {
            MerchantAccountDTO merchantAccountDTO = new MerchantAccountDTO();
            BeanUtils.copyProperties(merchantAccount, merchantAccountDTO);
            list.add(merchantAccountDTO);
        }
        log.info("MerchantAccountServiceImpl.listMerchantAccount(), 出参list={} ", list);
        return ResultVO.success(list);
    }


}