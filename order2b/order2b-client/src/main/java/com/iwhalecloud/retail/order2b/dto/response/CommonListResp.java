package com.iwhalecloud.retail.order2b.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonListResp<T> implements Serializable {

    List<T> list;
}
