package com.iwhalecloud.retail.goods.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComplexGoodsGetResp implements Serializable{
    
	private static final long serialVersionUID = 1L;

	private String goodsId;

    private String name;

    private String sn;

    private String brandId;

    private String catId;

    private Double weight;

    private Short marketEnable;

    private String intro;

    private Double price;

    private Double cost;

    private Double mktprice;

    private Date createTime;

    private Date lastModify;

    private Long viewCount;

    private Long buyCount;

    private Short disabled;

    private Long sord;

    private String creatorUser;

    private String supperId;

    private String auditState;

    private String simpleName;

    private Integer minNim;

    private String unit;

    private String searchKey;

    private String sellingPoint;

    private String regionId;

    private String regionName;
    
    private String defaultImage;
   
}