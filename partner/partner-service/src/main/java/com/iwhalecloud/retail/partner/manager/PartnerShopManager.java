package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.entity.PartnerShop;
import com.iwhalecloud.retail.partner.mapper.PartnerShopMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class PartnerShopManager{
    @Resource
    private PartnerShopMapper partnerShopMapper;

    private static double EARTH_RADIUS = 6378.137;

    private static String LAT_MIN = "latMin";
	private static String LAT_MAX = "latMax";
	private static String LNG_MIN = "lngMin";
	private static String LNG_MAX = "lngMax";

    private static double dis = 10;

	/**
	 * 根据条件查询一条记录
	 * @param partnerShopId
	 * @return
	 */
	public PartnerShopDTO selectOne(String partnerShopId) {
		PartnerShop entity = getPartnerShopByShopId(partnerShopId);
		if (entity == null) {
			log.warn("PartnerShopManager.selectOne 未查询出店铺数据，partnerShopId={}",partnerShopId);
			return null;
		}
		PartnerShopDTO dto = new PartnerShopDTO();
		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	/**
	 * 根据店铺ID查询单个店铺
	 * @param shopId
	 * @return
	 */
	private PartnerShop getPartnerShopByShopId(String shopId) {
		QueryWrapper<PartnerShop> queryWrapper = new QueryWrapper<PartnerShop>();
		if(shopId == null){
			return null;
		}
		queryWrapper.eq("partner_shop_id", shopId);
		return partnerShopMapper.selectOne(queryWrapper);
	}

	/**
	 * 修改店铺
	 * @param request
	 * @return
	 */
	public int update(PartnerShopUpdateReq request) {
		PartnerShop entity = getPartnerShopByShopId(request.getPartnerShopId());
        if (entity == null) {
        	log.warn("PartnerShopManager.update 未查询出店铺数据，partnerShopId={}",request.getPartnerShopId());
        	return 0;
        }
        
        BeanUtils.copyProperties(request, entity);
//        UpdateWrapper<PartnerShop> updateWrapper = new UpdateWrapper<PartnerShop>();
//		updateWrapper.eq("partner_shop_id", request.getPartnerShopId());
		int t = partnerShopMapper.updateById(entity);
        return t;
	}

	/**
	 * 修改店铺状态
	 * @param req
	 * @return
	 */
	public int updatePartnerShopState(PartnerShopStateUpdateReq req) {
		PartnerShop entity = getPartnerShopByShopId(req.getPartnerShopId());
        if (entity == null) {
        	log.warn("PartnerShopManager.updatePartnerShopState 未查询出店铺数据，partnerShopId={}",req.getPartnerShopId());
        	return 0;
        }
        
        BeanUtils.copyProperties(req, entity);
        UpdateWrapper<PartnerShop> updateWrapper = new UpdateWrapper<PartnerShop>();
		updateWrapper.eq("partner_shop_id", req.getPartnerShopId());
		int t = partnerShopMapper.update(entity, updateWrapper);
        return t;
	}

	/**
	 * 查询厅店
	 * @param req
	 * @return
	 */
	public Page<PartnerRelShopDTO> pagePartnerNearby(PartnerShopListReq req){
		Page<PartnerRelShopDTO> page = new Page<PartnerRelShopDTO>(req.getPageNo(), req.getPageSize());
		double lat =Double.valueOf(req.getLat());
		double lng =Double.valueOf(req.getLng());
		Map<String,Double> map = getPoiRange(PartnerConst.distance,lng,lat);
		req.setLngMin(map.get(LNG_MIN).toString());
		req.setLngMax(map.get(LNG_MAX).toString());
		req.setLatMax(map.get(LAT_MAX).toString());
		req.setLatMin(map.get(LAT_MIN).toString());
		return partnerShopMapper.pagePartnerNearby(page, req);
	}
	/**
	 * 查询厅店列表(不分页）
	 * @param req
	 * @return
	 */
	public List<PartnerShopDTO> queryPartnerShopList(PartnerShopQueryListReq req) {
		return partnerShopMapper.queryPartnerShopList(req);
	}

	/**
	 * 分页查询厅店列表
	 * @param req
	 * @return
	 */
	public Page<PartnerShopDTO> queryPartnerShopPage(PartnerShopQueryPageReq req) {
		Page<PartnerShopDTO> page = new Page<PartnerShopDTO>(req.getPageNo(), req.getPageSize());
		return partnerShopMapper.queryPartnerShopPage(page, req);
	}
	public static Map<String,Double> getPoiRange(double dis, double lng, double lat) {
		double[] poi = new double[4];
		Map<String,Double> map = new HashedMap();
		double lng1 = (Math.PI / 180) * lng;
		double lng2 = (Math.PI / 180) * lng;
		double lat1 = (Math.PI / 180) * lat;
		double lat2 = (Math.PI / 180) * lat;

		// 角度
		double theta = dis / EARTH_RADIUS;

		// 维度相同，反算经度
		double lngVal = Math.cos(theta) - Math.sin(lat1) * Math.sin(lat2);
		double lngThetaVal = lngVal / (Math.cos(lat1) * Math.cos(lat2));
		double elng1 = (-Math.acos(lngThetaVal) + lng1) / (Math.PI / 180);
		double elng2 = (Math.acos(lngThetaVal) + lng1) / (Math.PI / 180);

		//LogLIB.LogRun.info("elng1=" + UtilTools.format(elng1) + " elng2=" + UtilTools.format(elng2));

		// 经度相同，反算维度（根据辅助角公式计算 asinx + bcosx）
		// theta = a * Math.sin(lat2) + b * Math.cos(lat2) = Math.sqrt(a平方 + b平方)sin(x + arctan(b/a))
		double a = Math.sin(lat1);
		double b = Math.cos(lat1) * Math.cos(lng2 - lng1);
		double sqrt = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		double latTheteVal = Math.cos(theta) / sqrt;
		double elat1 = (Math.asin(latTheteVal) - Math.atan(b/a)) / (Math.PI / 180);
		double elat2 = (Math.asin(-latTheteVal) - Math.atan(b/a)) / (Math.PI / 180) + 180;

		//LogLIB.LogRun.info("elat1=" + UtilTools.format(elat1) + " elat2=" + UtilTools.format(elat2));

		poi[0] = elng1;
		poi[1] = elng2;
		poi[2] = elat1;
		poi[3] = elat2;
		map.put(LAT_MIN,poi[2]);
		map.put(LAT_MAX,poi[3]);
		map.put(LNG_MIN,poi[0]);
		map.put(LNG_MAX,poi[1]);
		return map;
	}

	public static void main(String[] args) {
		getPoiRange(10,28.256552,112.986871);
	}
	/**
	 * <p>Description: 计算两点之间距离</p>
	 * @param slng 起始经度
	 * @param slat 起始纬度
	 * @param elng 结束经度
	 * @param elat 结束纬度
	 * @return 米
	 */
	public static double getDistanceByGaoDe(double slng, double slat, double elng, double elat) {
		double lng1 = (Math.PI / 180) * slng;
		double lng2 = (Math.PI / 180) * elng;
		double lat1 = (Math.PI / 180) * slat;
		double lat2 = (Math.PI / 180) * elat;

		// 两点间距离 km，如果想要米的话，结果*1000就可以了
		double d = Math.acos(
				Math.sin(lat1) * Math.sin(lat2)
						+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1)
		) * EARTH_RADIUS;
		// 精度1位小数
		d = Math.round(d * 10000d) / 10000d;

		return d * 1000;
	}
    
}
