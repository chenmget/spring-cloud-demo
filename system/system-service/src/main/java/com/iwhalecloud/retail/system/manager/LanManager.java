package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.dto.LanDTO;
import com.iwhalecloud.retail.system.entity.Lan;
import com.iwhalecloud.retail.system.mapper.LanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LanManager {
    @Resource
    private LanMapper lanMapper;

    public int saveLan(LanDTO lanDTO){
        Lan lan = new Lan();
        BeanUtils.copyProperties(lanDTO, lan);
        return lanMapper.insert(lan);
    }

    public int deleteOrganization(String lanId){
        return lanMapper.deleteById(lanId);
    }

    public int updateOrganization(LanDTO lanDTO){
        Lan lan = new Lan();
        BeanUtils.copyProperties(lanDTO, lan);
        return lanMapper.deleteById(lan);
    }

    public List<Lan> listLan(){
        QueryWrapper<Lan> wrapper = new QueryWrapper<>();
        return lanMapper.selectList(wrapper);
    }
    public LanDTO getLanInfoById(String lanId) {
        Lan lan  = lanMapper.selectById(lanId);
        LanDTO lanDTO =new LanDTO();
        BeanUtils.copyProperties(lan, lanDTO);
        return lanDTO;
    }
}
