package com.tensquare.common.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bruce
 * @date 2020/1/18 0018 - 下午 11:03
 */
public class JwtTest {


    //签发可过期的token
    @Test
    public void testCreateTokenHasExpiration(){
        //当前系统时间的长整型
        long now=System.currentTimeMillis();
        //过期时间。这里是1分钟后的时间长整型
        long exp=now + 60*1000;
        //1.创建一个jwt构建者
        JwtBuilder jwtBuilder= Jwts.builder()
                //声明的标识
                .setId("888")
                //主体，“用户”
                .setSubject("Rose")
                //创建日期
                .setIssuedAt(new Date())
                //签名手段,参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256,"itcast")
                //设置过期的时间
                .setExpiration(new Date(exp));
        //2.获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
    }

    //解析可过期JWT token
    @Test
    public void testParseTokenHasExpiration(){
        //token
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNTc5MzYyODEwLCJleHAiOjE1NzkzNjI4NzB9.JWDwQfJVzgCn3AGzdphkbc2NzL1cVsU-Qyio8PxcBls";

        //解析token获取载荷中的声明对象
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws(token)
                .getBody();

        //打印声明的属性
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("issuedAt:"+claims.getIssuedAt());
        DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间:"+sf.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+sf.format(claims.getExpiration()));
        System.out.println("当前时间:"+sf.format(new Date()));
    }
    /**
     * 签发自定义claims的token
     */
    @Test
    public void testCreateJwtByCustomClaims(){
        //当前系统时间的长整型
        long now=System.currentTimeMillis();
        //过期时间。这里是1分钟后的时间长整型
        long exp=now + 60*1000;

        JwtBuilder jwtBuilder= Jwts.builder()
                .setId("888")
                .setSubject("Rose")
                .setIssuedAt(new Date())
                //参数1：签名加密方式，参数2：盐
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .setExpiration(new Date(exp))
                //自定义Claims
//                .setClaims(map)//直接传map
                .claim("roles","admin")
                .claim("logo","itcast.png")
                ;
        String token=jwtBuilder.compact();
        System.out.println(token);
    }

    //解析自定义claims的JWT
    @Test
    public void testParseJwtByCustomClaims(){
        //token
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNTc5MzYzMDcyLCJleHAiOjE1NzkzNjMxMzIsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoiaXRjYXN0LnBuZyJ9.yYpHc5LrzDqPlouVFGKgJWREQaHopbxaMascrzOJhco";
        //获取载荷数据
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());

        DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间:"+sf.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+sf.format(claims.getExpiration()));
        System.out.println("当前时间:"+sf.format(new Date()));

        System.out.println("roles:"+claims.get("roles"));
        System.out.println("logo:"+claims.get("logo"));

    }
}
