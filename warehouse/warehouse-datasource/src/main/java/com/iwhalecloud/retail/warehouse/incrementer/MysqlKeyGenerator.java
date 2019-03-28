package com.iwhalecloud.retail.warehouse.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * TelDB主键生成策略
 * @author z
 * @date 2019/3/11
 */
public class MysqlKeyGenerator implements IKeyGenerator {

    @Override
    public String executeSql(String incrementerName) {
        return "SELECT " + IdWorker.getIdStr();
    }
}
