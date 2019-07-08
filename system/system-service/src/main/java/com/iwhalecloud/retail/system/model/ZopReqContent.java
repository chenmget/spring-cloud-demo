package com.iwhalecloud.retail.system.model;


import lombok.Data;
import com.ztesoft.fastjson.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
public class ZopReqContent implements Serializable {
    @JSONField(name="BillReqVo")
    private List<ZopMsgModel> billReqVo;
}
