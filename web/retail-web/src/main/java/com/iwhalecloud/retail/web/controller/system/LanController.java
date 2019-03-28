package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.service.LanService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lan")
@Slf4j
public class LanController {

    @Reference
    private LanService lanService;

    @ApiOperation(value = "本地网列表", notes = "")
    @GetMapping(value = "listLan")
    public ResultVO listLans(){
//        ResultVO listLanResp = new ResultVO();
//        ResultVO list = lanService.listLans();
//        listLanResp.setResultCode(list.getResultCode());
//        listLanResp.setResultMsg(list.getResultMsg());
//        listLanResp.setResultData(list.getResultData());
        return lanService.listLans();
    }
}
