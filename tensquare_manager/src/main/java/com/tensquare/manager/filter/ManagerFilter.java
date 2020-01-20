package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用zuulFilter网关过滤器实现后台用户的身份验证
 * @author bruce
 * @date 2020/1/20 0020 - 下午 10:31
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        //pre 之前执行，post 之后执行
        return "pre";
    }

    @Override
    public int filterOrder() {
        //多个过滤器时数字越小越优先执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //当前过滤器是否开启，true开启
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //return null就等同放行；requestContext.setSendZuulResponse(false)阻止
        System.out.println("执行了过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //内部第一次请求方法OPTIONS必须放行
        if (request.getMethod().equals("OPTIONS")){
            return null;
        }
        //登陆页面放行，根据请求uri是否有login
        if (request.getRequestURI().indexOf("login")>0){
            return null;
        }
        String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)){
            if (header.startsWith("tens ")){
                String token = header.substring(5);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles.equals("admin")){
                        requestContext.addZuulRequestHeader("Authorization",header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);//终止运行

                }
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("没有权限");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
