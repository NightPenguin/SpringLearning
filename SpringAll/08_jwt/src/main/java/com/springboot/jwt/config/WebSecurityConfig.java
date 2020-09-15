package com.springboot.jwt.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 该注解已含@Configuration
 * WebSecurityConfigurerAdapter: 已经默认声明了一些安全特性
 *  1、验证所有的请求。
 *  2、允许所有用户进行表单登陆进行身份验证。
 *  3、允许用户使用HTTP 基本认证。
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 自定义表单登录页

    /**
     * httpSecurity：对应Spring Security 命名空间配置方式中XML 文件内的标签。调用后，上下文回到HTTPSecurity 除非使用and（）方法结束改标签。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            // 返回一个URL 拦截注册器，可以调用anyRequest()、antMatchers()、regexMatchers() 等方法匹配系统的URL，并为其指定安全策略。
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                //指定自定义登录页，并注册一个POST 路由，用于接收登录请求。
                .formLogin()
                .loginPage("/myLogin.html")
                // 自定义登录URL
                .loginProcessingUrl("/login")
                //设置登录页不需要访问权限
                .permitAll()
                .and()
                // csrf（）：提供跨站请求伪造防护功能，继承WebSecurityConfigurerAdapter 时，会默认开启csrf。
                .csrf().disable();
    }
}
