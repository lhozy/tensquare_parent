package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //约定存储token的请求头的key(关键字段)为：Authorization,并且以tens空格开头
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header)) {
            if (header.startsWith("tens ")){
                String token = header.substring(5);//截取获得真正的token
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(StringUtils.isNotEmpty(roles) && "admin".equals(roles)){
                        request.setAttribute("claims_admin",token);
                    }
                    if(StringUtils.isNotEmpty(roles) && "user".equals(roles)){
                        request.setAttribute("claims_user",token);
                    }

                }catch (Exception e){
                    throw new RuntimeException("令牌错误");

                }
            }
        }

        return true;
    }
}
