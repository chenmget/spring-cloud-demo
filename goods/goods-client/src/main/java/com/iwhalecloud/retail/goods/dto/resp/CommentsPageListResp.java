package com.iwhalecloud.retail.goods.dto.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @Author My
 * @Date 2018/12/6
 **/
@Data
public class CommentsPageListResp extends Page {

    /**
     * 好评率
     */
    private String goodCommentRate;
}
