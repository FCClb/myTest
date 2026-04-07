package com.fc.springsecurity.config;

import com.fc.springsecurity.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 启用全局方法级别的安全控制
 *      设置prePostEnabled = true  pre表示在方法执行前进行授权校验，post表示在方法执行后进行授权校验
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //BCryptPasswordEncoder注入到Spring容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入AuthenticationManager,供外部类使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用于配置 HTTP 请求的安全处理
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable();

        //允许跨域
        http.cors();
        
        http.
                //不会去创建会话,每个请求都被视为独立的请求,STATELESS表示无状态
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //定义请求的授权规则
                .authorizeRequests()
                //对登录接口 允许匿名访问
                .mvcMatchers("/user/login","/captchaImage").anonymous()
                //配置形式的权限控制
                .antMatchers("/myYes").hasAuthority("system:menu:list")
                //除上面外的所有请求 就全部需要鉴权认证
                .anyRequest().authenticated();

        //将自定义认证过滤器 添加到过滤器链
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //配置认证异常处理器 401
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置授权异常处理器 403
                .accessDeniedHandler(accessDeniedHandler);
    }
}
