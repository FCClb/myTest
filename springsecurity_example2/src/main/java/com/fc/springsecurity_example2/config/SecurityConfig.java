package com.fc.springsecurity_example2.config;

import com.fc.springsecurity_example2.filter.KaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 启用全局方法级别的安全控制
 *      设置prePostEnabled = true pre 表示在方法执行前进行授权校验,post 表示在方法执行后进行授权校验
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private KaptchaFilter kaptchaFilter;

//    @Autowired
//    private KaptchaFilter kaptchaFilter;

    /**
     * 用于配置 HTTP 请求的安全处理
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开始授权,允许哪些请求访问系统
        http.authorizeHttpRequests()
            //将指定页面设置为公共资源,无需认证和授权直接访问
            .mvcMatchers("/login.html","/code/image").permitAll()
            //除了上面的资源,其他的请求都要经过身份验证
            .anyRequest().authenticated();

        //开启表单认证
        http.formLogin()
            .loginPage("/login.html")  //登录页面
            .loginProcessingUrl("/login") //提交路径
            .usernameParameter("username") //表单中的用户名
            .passwordParameter("password") //表单中的密码
            .successHandler(successHandler)  //认证成功处理器
            .failureHandler(failureHandler); //认证失败处理器
//            .successForwardUrl("/index")    //指定登录成功后要跳转的路径
//            .defaultSuccessUrl("/index")    //重定向
//            .failureForwardUrl("/login.html")    //指定失败后跳转的路径

            ;

        //开启注销配置
        http.logout()
//            .logoutUrl("/logout")   //退出登录地址
            .invalidateHttpSession(true)   //退出时 session是否失效,默认true
            .clearAuthentication(true)     //退出时 是否清除认证信息,默认true
//            .logoutSuccessUrl("/login.html")    //退出登录时，跳转的地址
            .logoutSuccessHandler(logoutSuccessHandler);    //注销登录处理器

        //关闭CSRF
        http.csrf().disable();

        //将自定义的图形验证码校验过滤器 添加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(kaptchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
