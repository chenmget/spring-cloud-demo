package cn.buildworld.elasticjob.job.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsSaleNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2019/4/15.
 */
@ElasticSimpleJob(cron = "0 10 1 * * ?",
        jobName = "firstJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class GoodsSaleOrderJob implements SimpleJob {
    @Reference(timeout = 60000)
    private GoodsSaleNumService goodsSaleNumService;

    @Override
    public void execute(ShardingContext shardingContext) {
        Date startDate = new Date();
        log.info("GoodsSaleOrderJob run start.....{}", startDate);
        try {
            //先清缓存再写入
            ResultVO<Boolean> resultVO = goodsSaleNumService.cleanCacheGoodSaleNum(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_7);
            if(resultVO.isSuccess() && resultVO.getResultData()){
                goodsSaleNumService.getGoodsSaleOrder(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_7);
            }

        }catch (Exception ex) {
            log.error("GoodsSaleOrderJob getGoodsSaleOrder_7 throw exception ex={}", ex);
        }

        try {
            //先清缓存再写入
            ResultVO<Boolean> resultVO = goodsSaleNumService.cleanCacheGoodSaleNum(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_15);
            if(resultVO.isSuccess() && resultVO.getResultData()){
                goodsSaleNumService.getGoodsSaleOrder(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_15);
            }

        }catch (Exception ex) {
            log.error("GoodsSaleOrderJob getGoodsSaleOrder_15 throw exception ex={}", ex);
        }

        Date endDate = new Date();
        log.info("SettleRecordJob run end.....{}", endDate);
    }
}
