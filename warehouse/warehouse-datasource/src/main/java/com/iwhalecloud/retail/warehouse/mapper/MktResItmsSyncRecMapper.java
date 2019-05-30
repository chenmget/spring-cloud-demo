package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.entity.ResourceChngEvtDetail;
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
     * 通过时间戳查询需要推送到ITMS的所有串码
     * @param startDate
     * @return
     */
    List<MktResItmsSyncRec> findMKTInfoByDate(@Param("startDate") String startDate);

    /**
     * 查询需要推给itms的字段
     * @param lanId 地市编码， typeId 类型ID， eventTypeArray 事件类型 ， startDate 时间戳
     * @return
     */
	List<Map<String, String>> findMKTInfoByLadId(@Param("lanId")String lanId, @Param("typeId")String typeId,
			@Param("eventTypeArray")String[] eventTypeArray, @Param("startDate")String startDate);

    /**
     * 根据ID保存itms推送表
     * @param id ID， destFileName 推送的文件路径， syncBatchId 推送的批次
     * @return
     */
	void updateFileNameById(@Param("id")String id, @Param("destFileName")String destFileName,
			@Param("syncBatchId")String syncBatchId);

	/**
     * 根据保存路径获取当前文件最近的批次
     * @param sendDir
     * @return
     */
	String getSeqBysendDir(@Param("sendDir")String sendDir);

	/**
	 * 获取当天未更新文件路径
	 * @param date
	 * @return
	 */
	List<MktResItmsSyncRec> getFileNameByDate(String date);

	/**
	 * 根据回传文件，串码批量更新推送状态
	 * @param mktResItmsSyncRecRep
	 * @return
	 */
	int batchUpdateMRIyFileName(@Param("list")List<MktResItmsSyncRec> mktResItmsSyncRecRep);
}