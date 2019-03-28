package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WhaleCloudKeyGeneratorTest extends TestBase {

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Test
    public void seq(){
       String aaa= whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER.getCode());
       System.out.println(aaa);
    }


}
