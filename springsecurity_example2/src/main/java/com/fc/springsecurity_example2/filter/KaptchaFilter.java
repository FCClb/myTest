package com.fc.springsecurity_example2.filter;

import com.fc.springsecurity_example2.entity.CheckCode;
import com.fc.springsecurity_example2.exception.KaptchaNotMatchException;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码校验过滤器
 */
@Component
public class KaptchaFilter extends OncePerRequestFilter {

    //前端输入的图形验证码参数
    private String codeParam = "imageCode";

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //非post请求的表单 提交不校验
        if (request.getMethod().equals("POST")) {

            try {
                //校验图形验证码是否合法
                validateCode(request);
            } catch (KaptchaNotMatchException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        //放行 进入下一个过滤器
        filterChain.doFilter(request, response);

    }

    private void validateCode(HttpServletRequest request) {

        //1.获取用户传入的图形验证码
        String requestCode = request.getParameter(this.codeParam);
        if (requestCode == null) {
            requestCode = "";
        }

        requestCode = requestCode.trim();

        //2.获取session中的验证码值
        HttpSession session = request.getSession();
        CheckCode checkCode = (CheckCode) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (checkCode != null) {
            //清除验证码，不管客户端成功还是不成功，都应该刷新验证码
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }

        //校验验证码，出错则抛出异常
        if (!requestCode.equalsIgnoreCase(checkCode.getCode())) {
            throw new KaptchaNotMatchException("验证码输入错误");
        }

        if (checkCode.isExpired()) {
            throw new KaptchaNotMatchException("验证码过期");
        }

        if (checkCode == null) {
            throw new KaptchaNotMatchException("验证码不存在");
        }
    }
}
