package com.iwhalecloud.retail.web.controller.b2b.cache;

import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/redis")
public class RedisController {

//    是否走集群
    private String jiqun = "yes";

    @Autowired
    private  RedisCacheUtils redisCacheUtils;

//    @Autowired
//    private JedisCluster jedisCluster;

    @GetMapping("/get")
    public ResultVO get(@RequestParam(value = "key") String key) {
        ResultVO resultVO = new ResultVO();
        try{
            String value = (String)redisCacheUtils.getCacheObject(key);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(value);
            resultVO.setResultMsg("成功");
        }catch (Exception ex){
            ex.printStackTrace();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(ex.getMessage());
        }
        return resultVO;
    }

    @PostMapping("/del")
    public ResultVO del(@RequestParam(value = "key") String key) {
        ResultVO resultVO = new ResultVO();
        try{
            redisCacheUtils.del(key);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(true);
            resultVO.setResultMsg("成功");
        }catch (Exception ex){
            ex.printStackTrace();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(ex.getMessage());
        }
        return resultVO;
    }

    @PostMapping("/save")
    public ResultVO save(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        ResultVO resultVO = new ResultVO();
        try{
            redisCacheUtils.setCacheObject(key, value);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(true);
            resultVO.setResultMsg("成功");
        }catch (Exception ex){
            ex.printStackTrace();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(ex.getMessage());
        }
        return resultVO;
    }

}
