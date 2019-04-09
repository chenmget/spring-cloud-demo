package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderItemDetailModel extends OrderItemDetail implements Serializable {

    List<String> resNbrList;

    List<String> detailList;

    private List<String> itemIds;

    private List<Integer> states;

    private String uOrderApply;


    private List<String> applyIdList;

    private List<String> lanIdList;


}
