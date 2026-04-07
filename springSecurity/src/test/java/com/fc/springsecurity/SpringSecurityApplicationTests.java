package com.fc.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 创建token
     */
    @Test
    public void testCreateJWT(){

        JwtBuilder builder = Jwts.builder().
                setId("9527") //设置唯一id  用户id
                .setSubject("hejiayun_community")  //主体内容
                .setIssuedAt(new Date())    //签约时间
                .signWith(SignatureAlgorithm.HS256, "FcClb");//设置签名 使用HS256算法,并设置 secretkey

        //压缩成String形式
        String jws = builder.compact();

        System.out.println(jws);
        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NTI3Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzYzMjgyNjI1fQ.icGxggUYm1tGA2uc0qRDRwmewHb_yds10HAOcwhu3Ok
    }

    /**
     * 解析token
     */
    @Test
    public void parserJWT(){

        String jws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NTI3Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzYzMjgyNjI1fQ.icGxggUYm1tGA2uc0qRDRwmewHb_yds10HAOcwhu3Ok";

        Claims claims = Jwts.parser().setSigningKey("FcClb")
                .parseClaimsJws(jws)
                .getBody();

        System.out.println(claims);

        //{jti=9527, sub=hejiayun_community, iat=1763282625}
    }

    /**
     * 设置过期时间
     */
    @Test
    public void testCreateJWT2(){

        long currentTimeMillis = System.currentTimeMillis();
        Date expTime = new Date(currentTimeMillis);

        JwtBuilder builder = Jwts.builder().
                setId("9527") //设置唯一id  用户id
                .setSubject("hejiayun_community")  //主体内容
                .setIssuedAt(new Date())    //签约时间
                .setExpiration(expTime)     //设置过期时间
                .signWith(SignatureAlgorithm.HS256, "FcClb");//设置签名 使用HS256算法,并设置 secretkey

        //压缩成String形式
        String jws = builder.compact();

        System.out.println(jws);
        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NTI3Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzYzMjgyODI3LCJleHAiOjE3NjMyODI4Mjd9.BpPYxsAgrbpQaI2YPSLxaCp9hy0xwbi8jqxR4lr-qDU
    }

    /**
     * 自定义claims
     */
    @Test
    public void testJWT3(){

        long currentTimeMillis = System.currentTimeMillis() + 1000 * 60 * 60 * 24L;
        //                                           1秒是1000毫秒,1分钟60秒，1小时60分钟，1天24小时
        Date expTime = new Date(currentTimeMillis);

        JwtBuilder builder = Jwts.builder().
                setId("9527") //设置唯一id  用户id
                .setSubject("hejiayun_community")  //主体内容
                .setIssuedAt(new Date())    //签约时间
                .setExpiration(expTime)     //设置过期时间
                .claim("roles","admin")
                .signWith(SignatureAlgorithm.HS256, "FcClb");   //设置签名 使用HS256算法,并设置 secretkey

        //压缩成String形式
        String jws = builder.compact();

        System.out.println(jws);
        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NTI3Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzYzMjgzMDY1LCJleHAiOjE3NjMzNjk0NjUsInJvbGVzIjoiYWRtaW4ifQ.jnLsSK2N5_qOenjOYaMuaG3QuvBv7onLEs6rU2nNXzU
    }

}
