package com.iwhalecloud.retail.web.controller.b2b.order.dto;

import lombok.Data;

@Data
public class ExcelTitleName {

    String name;

    String value;

    public ExcelTitleName(String value, String name) {
        this.name = name;
        this.value = value;
    }
}
