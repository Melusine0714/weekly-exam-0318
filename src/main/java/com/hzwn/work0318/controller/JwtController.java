package com.hzwn.work0318.controller;

import com.hzwn.work0318.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("jwt")
public class JwtController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("toJwt")
    public String jwt(HttpServletResponse response, String key, String value) {
        System.out.println(key);
        System.out.println(value);
        String KV = key + "AND" + value;
        String userToken = UserTokenUtil.createUserToken(KV, response, stringRedisTemplate);
        System.out.println(userToken);
        return userToken;
    }

    @RequestMapping("noJwt")
    public String noJwt(HttpServletRequest request, HttpServletResponse response) {
        String userAccount = UserTokenUtil.getUserAccount(request);
        System.out.println("userAccount:" + userAccount);
        String[] split = userAccount.split("AND");
        UserTokenUtil.deleteUserToken(response, request, stringRedisTemplate);
        return split[1];
    }
}
