package com.hzwn.work0318.util;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class UserTokenUtil {

    private static String token_key = "account";
    private static String cookike_key = "user_token";

    //1.登录成功之后,生成包含用户账号的token,并写到cookie中,这样之后的每次请求都会带着token来访问.
    public static String createUserToken(String account, HttpServletResponse response, StringRedisTemplate stringRedisTemplate){

        //生成jwtToken,并放到Cookie中去
        String token = JwtUtil.createTokenSingleInfo(30, token_key, account);
        CookieUtils.addCookie(response,cookike_key,token,false);

        //生成关联数据,发到redis中
        stringRedisTemplate.opsForValue().set(token,account,60, TimeUnit.MINUTES);

        return token;
    }


    // 2.从cookie里读取用户信息
    public static String getUserAccount(HttpServletRequest request){

        //先从cookies中读取到token
        String tokenFromCookie = CookieUtils.getCookieValueByName(request, cookike_key);

        //从token中解析出用户account
        String account = JwtUtil.parseValueWithoutException(tokenFromCookie, token_key);

        return account;
    }

    //3.删除对应的cookie
    public static void deleteUserToken(HttpServletResponse response,HttpServletRequest request,StringRedisTemplate stringRedisTemplate){

        //响应返回时删除浏览器里对应的cookie
        CookieUtils.addCookie(response,cookike_key,"xxxx",true);

        //删除关联数据
        String tokenFromCookie = CookieUtils.getCookieValueByName(request, cookike_key);
        stringRedisTemplate.delete(tokenFromCookie);

    }

    //4.token续期的处理
    public static void checkIfNeedDelayToken(HttpServletResponse response,HttpServletRequest request,StringRedisTemplate stringRedisTemplate) {

        //检查cookies中是否有userToken
        String tokenFromCookie = CookieUtils.getCookieValueByName(request, cookike_key);
        if (!StringUtils.isEmpty(tokenFromCookie)) {
            //cookie中有userToken
            try {
                String account = JwtUtil.parseValue(tokenFromCookie, token_key);
            } catch (ExpiredJwtException expiredJwtException) {
                //userToken已过期,检查是否需要续期
                System.out.println("userToken已过期,需要判断是否需要展期");

                ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
                String accountFromRedis = opsForValue.get(tokenFromCookie);
                if (!StringUtils.isEmpty(accountFromRedis)) {
                    //符合续期条件,执行续期
                    System.out.println("关联数据未过期,符合展期条件,进行展期");

                    //展期操作:  生成新的token    关联数据也更新   当前request中的过期token也要更新掉
                    String refreshToken = createUserToken(accountFromRedis, response, stringRedisTemplate);
                    stringRedisTemplate.delete(tokenFromCookie);  //删除原有关联数据

                    //当前request中的过期token也要被更新掉
                    CookieUtils.refreshRequestCookie(request, cookike_key, refreshToken);

                } else {
                    System.out.println("关联数据已过期过期,不进行展期");
                }

            } catch (Exception e){
                //忽略其它异常
            }
        }
    }




}
