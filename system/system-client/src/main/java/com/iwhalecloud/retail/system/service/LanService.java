package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.LanDTO;

public interface LanService{

    ResultVO listLans();

    LanDTO getLanInfoById(String lanId);

}