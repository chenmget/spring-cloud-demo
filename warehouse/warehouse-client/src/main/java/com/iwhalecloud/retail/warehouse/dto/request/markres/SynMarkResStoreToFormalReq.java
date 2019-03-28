package com.iwhalecloud.retail.warehouse.dto.request.markres;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/11 16:47
 */
@Data
public class SynMarkResStoreToFormalReq extends PageVO implements Serializable {
    private Integer day;
    private String synStatus;
}
