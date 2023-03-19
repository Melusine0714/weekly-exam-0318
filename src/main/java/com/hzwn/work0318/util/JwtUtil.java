package com.hzwn.work0318.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static String secrect ="aksASDFASD!@#$!@##$!ajsdkj91834asjdf!@#$!@#kasdf";

    public static String createToken(Integer minutes, Map<String,Object> info){


        JwtBuilder jwtBuilder = Jwts.builder();

        //头部
        jwtBuilder.setHeaderParam("alg","HS256");
        jwtBuilder.setHeaderParam("typ","JWT");


        //载荷
        jwtBuilder.setIssuer("woniuBookshop2");
        jwtBuilder.setSubject("蜗牛书店");
        //过期时间
        Date expireDate = new Date(  new Date().getTime() + minutes*60*1000 );
        jwtBuilder.setExpiration(expireDate);
        //增加自定义信息
//        jwtBuilder.claim("username","wukong");
//        jwtBuilder.claim("age","666");
        jwtBuilder.addClaims(info);


        //签名
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secrect);


        String token = jwtBuilder.compact();

        return token;
    }

    public static String createTokenSingleInfo(Integer minutes,String key,String value){
        HashMap<String, Object> infoMap = new HashMap<>();
        infoMap.put(key,value);

        return createToken(minutes,infoMap);
    }


    public static Claims parseToken(String token){
        JwtParser parser = Jwts.parser();

        Jws<Claims> claimsJws = parser.setSigningKey(secrect).parseClaimsJws(token);

        Claims body = claimsJws.getBody();

        return body;
    }

    public static String parseValue(String token,String key){
        return (String)parseToken(token).get(key);
    }


    public static String parseValueWithoutException(String token,String key){

        String value = "";
        try{
            String parseValue  = parseValue(token,key);
            value = StringUtils.isEmpty(parseValue) ? "" : parseValue;
        }catch (Exception e){
            //忽略异常
        }

        return value;
    }



}
