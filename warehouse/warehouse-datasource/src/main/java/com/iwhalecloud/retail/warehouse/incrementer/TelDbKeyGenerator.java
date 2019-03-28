package com.iwhalecloud.retail.warehouse.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;

/**
 * TelDB主键生成策略
 * @author z
 * @date 2019/3/11
 */
public class TelDbKeyGenerator implements IKeyGenerator {

    @Override
    public String executeSql(String incrementerName) {
        return "SELECT seq_retail_all_tables.nextval" ;
    }
}
