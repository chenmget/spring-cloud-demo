package com.iwhalecloud.retail.order2b.dto.base;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SelectModel {

    private String lanId;

    protected String timeFormat(String time){
        if(StringUtils.isEmpty(time) || !time.contains(".")){
            return time;
        }
        time=time.substring(0, time.indexOf("."));
        return time;
    }
}
