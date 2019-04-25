package com.iwhalecloud.retail.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.iwhalecloud.retail.web.consts.UserType;
import com.iwhalecloud.retail.web.controller.cache.RedisCacheUtils;
import com.twmacinta.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component  // 需要添加Component注释才会被springboot管理
public class JWTTokenUtil {

    private static RedisCacheUtils redisCacheUtils;

    @Autowired
    private RedisCacheUtils redisCacheUtils1;

    @PostConstruct
    public void beforeInit() {
        redisCacheUtils = redisCacheUtils1;
    }

    // springboot不允许/不支持把值注入到静态变量中
    // springboot支持通过set方法实现注入，我们可以利用非静态set方法注入静态变量
//    @Value("${jwt.secret}")
    private static String SECRET; //密钥

//    @Value("${jwt.expiresAt}")
    private static String expiresAt; //token有效时间 （分钟）

    // *核心：通过非静态set方法实现注入*
    @Value("${jwt.secret}")
    public void setSECRET(String secret) {
        JWTTokenUtil.SECRET = secret;
    }

    @Value("${jwt.expiresAt}")
    public void setExpiresAt(String expiresAt) {
        JWTTokenUtil.expiresAt = expiresAt;
    }


    public static String getMemberToken(String memberId, String memberSessionId) {
        return createToken(memberId, memberSessionId, UserType.MEMBER);
    }

    public static String getUserToken(String userId, String userSessionId) {
        if(StringUtils.isEmpty(userId)){
            return "";
        }
        return createToken(userId, userSessionId, UserType.USER);
    }

    /**
     * token有效时间逻辑：登录成功时，把用于生成token的 sessionId 当作key 存放在redis 设置有效时间
     * 判断时  就去缓存里面 取  有没有这个sessionId key的数据  有就代表有效，没有就代表失效
     *
     * 更新有效时间
     * 会先判断有没有值
     * @param sessionId
     * @return
     */
    public static boolean updateTokenExpireTime(String sessionId) {
        if(!redisCacheUtils.hasKey(sessionId)){
            // 第一次 设置
            redisCacheUtils.setCacheObject(sessionId, true);
        }
        Integer time = Integer.parseInt(expiresAt);
        if (Objects.isNull(time)) {
            time = 30; // 默认30分钟
        }
        return redisCacheUtils.expire(sessionId, time * 60);
    }

    /**
     * token 是否有效
     * @param sessionId
     * @return
     */
    public static boolean isTokenEffect(String sessionId) {
        if(redisCacheUtils.hasKey(sessionId)){
            return true;
        }
        return false;
    }


    private static String createToken(String id, String sessionId, UserType type){
        Date iat = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, Integer.parseInt(expiresAt));
        Date exp = nowTime.getTime();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = "";
        try {
            String hash = "id="+id+"&sessionId="+sessionId+"&secret="+SECRET+"&exp="+String.valueOf(exp.getTime()/1000)+"&iat="+String.valueOf(iat.getTime()/1000);
            MD5 md5 = new MD5();
            md5.Update(hash, null);
            token = JWT.create()
                    .withHeader(map)
                    .withClaim("id", id)
                    .withClaim("sessionId", sessionId)
                    .withClaim("type",type.toString())
                    .withClaim("selfToken", md5.asHex())
                    .withExpiresAt(exp)
                    .withIssuedAt(iat)
                    .sign(Algorithm.HMAC256(SECRET));

            // 保存有效时间
            updateTokenExpireTime(sessionId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
