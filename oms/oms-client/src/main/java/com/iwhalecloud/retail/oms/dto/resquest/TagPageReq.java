package com.iwhalecloud.retail.oms.dto.resquest;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lin.wh
 * @Date: 2018/11/1 17:24
 * @Description:
 */

@Data
@ApiModel(value = "标签列表分页查询请求参数对象")
public class TagPageReq extends PageVO {

    @ApiModelProperty(value = "标签名称")
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

