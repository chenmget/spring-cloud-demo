package com.iwhalecloud.retail.oms.manager;


import com.iwhalecloud.retail.oms.dto.TRfidGoodsRelDTO;
import com.iwhalecloud.retail.oms.entity.TRfidGoodsRel;
import com.iwhalecloud.retail.oms.mapper.RfidGoodsRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RfidGoodsRelManager {

    @Resource
    private RfidGoodsRelMapper rfidGoodsRelMapper;

    /**
     * 新增.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/10/25 15:27
     */
    public int insert(TRfidGoodsRelDTO dto) {
        TRfidGoodsRel tRfidGoodsRel = new TRfidGoodsRel();
        BeanUtils.copyProperties(dto, tRfidGoodsRel);
        int t = rfidGoodsRelMapper.insert(tRfidGoodsRel);
        return t;
    }

    /**
     * 修改.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/10/25 15:27
     */
    public int modify(TRfidGoodsRelDTO dto) {
        int t = rfidGoodsRelMapper.updateRfidGoodsRel(dto);
        return t;
    }
    /**
     * 查询.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/10/25 15:27
     */
    public List<TRfidGoodsRelDTO> select(TRfidGoodsRelDTO dto) {
        List<TRfidGoodsRelDTO> list = rfidGoodsRelMapper.selectRfidGoodsRel(dto);
        return list;
    }
    /**
     * 删除.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/10/25 15:27
     */
    public boolean remove(TRfidGoodsRelDTO dto) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("rel_id", dto.getRelId());

        return rfidGoodsRelMapper.deleteByMap(columnMap) > 0;
    }
}
