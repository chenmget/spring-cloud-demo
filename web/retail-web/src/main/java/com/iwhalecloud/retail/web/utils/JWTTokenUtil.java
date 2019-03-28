package com.iwhalecloud.retail.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.iwhalecloud.retail.web.consts.UserType;
import com.twmacinta.util.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component  // 需要添加Component注释才会被springboot管理
public class JWTTokenUtil {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
