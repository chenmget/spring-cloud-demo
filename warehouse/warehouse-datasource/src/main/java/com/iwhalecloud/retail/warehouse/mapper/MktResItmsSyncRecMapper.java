package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Class: MktResItmsSyncRecMapper
 * @author autoCreate
 */
@Mapper
public interface MktResItmsSyncRecMapper extends BaseMapper<MktResItmsSyncRec>{

    /**
     * 查询需要推给itms的字段
     * @param lanId 地市编码， typeId 类型ID， eventTypeArray 事件类型 ， startDate 时间戳
     * @return
     */
	List<Map<String, String>> findMKTInfoByLadId(@Param("lanId")String lanId, @Param("typeId")String typeId,
			@Param("eventTypeArray")String[] eventTypeArray, @Param("startDate")String startDate);

	/**
	 * 查询需要推给itms的字段
	 * @param  lanId 地市编码， type 不同事件类型对应的过滤条件， isItms 是否itms ,ResouceEvent resouceEvent
	 * @return
	 */

	List<MktResItmsSyncRec> findDateMKTInfoByParams(@Param("lanId")String lanId,@Param("typeOps")String typeOps,@Param("eventType")String eventType,
											  @Param("isItms")String[] isItms,@Param("even")ResouceEvent resouceEvent);
    /**
     * 根据ID保存itms推送表
     * @param id ID， destFileName 推送的文件路径， syncBatchId 推送的批次
     * @return
     */
	void updateFileNameById(@Param("id")String id, @Param("destFileName")String destFileName,
			@Param("syncBatchId")String syncBatchId);

	void updateByMktResChngEvtDetailId(@Param("mktResChngEvtDetailId")String mktResChngEvtDetailId, @Param("destFileName")String destFileName,
						@Param("syncBatchId")String syncBatchId);
	/**
     * 根据保存路径获取当前文件最近的批次
     * @param sendDir
     * @return
     */
	String getSeqBysendDir(@Param("sendDir")String sendDir);

	/**
	 * 批量插入
	 * @param mktResItmsSyncRecRep
	 */
	void batchAddMKTInfo(List<MktResItmsSyncRec> mktResItmsSyncRecRep);
}