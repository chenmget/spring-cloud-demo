package com.iwhalecloud.retail.goods.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/4
 **/
@Data
public class SelectTagListDTO implements Serializable {

    private String tagId;
    private String tagName;
    private Integer contentNumber;
    private String tagDesc;
    private String tagColor;

}
