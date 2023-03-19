package com.hzwn.work0318.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    /**
     * 向response中新增cookie, 如果isDelete为true则是删除cookie
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param isDelete
     */
    public static void addCookie(HttpServletResponse response,String cookieName,String cookieValue,Boolean isDelete){

        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath("/");
        if (isDelete){
            cookie.setMaxAge(0);  //设置最大有效时间为0,等效于删除cookie
        }

        response.addCookie(cookie);
    }


    /**
     * 从request中读取cookieName对应的value
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                if (cookie.getName().equalsIgnoreCase(cookieName)){
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 修改request中cookieName的value 为 newCookieValue
     *
     * @param request
     * @param cookieName
     * @param newCookieValue
     * @return
     */
    public static Boolean refreshRequestCookie(HttpServletRequest request,String cookieName,String newCookieValue){

        Boolean result = false;

        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                if (cookie.getName().equalsIgnoreCase(cookieName)){
                    cookie.setValue(newCookieValue);
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
