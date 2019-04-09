package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.Promotion;
import lombok.Data;

import java.util.List;

@Data
public class PromotionModel extends Promotion{

    private List<String> lanIdList;
}
