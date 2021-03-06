package com.springboot.jwt.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * 该注解已含@Configuration
 * WebSecurityConfigurerAdapter: 已经默认声明了一些安全特性
 * 1、验证所有的请求。
 * 2、允许所有用户进行表单登陆进行身份验证。
 * 3、允许用户使用HTTP 基本认证。
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 自定义表单登录页

    /**
     * httpSecurity：对应Spring Security 命名空间配置方式中XML 文件内的标签。调用后，上下文回到HTTPSecurity 除非使用and（）方法结束改标签。
     *          HTTPSecurity 实际上配置Spring Security 的过滤器链，诸如CSRF、CORS、表单登陆等，每一个配置器对应一个过滤器。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 返回一个URL 拦截注册器，可以调用anyRequest()、antMatchers()、regexMatchers() 等方法匹配系统的URL，并为其指定安全策略。
        http.authorizeRequests()
                // antNatchers()：采用ANT 模式匹配器。
                // 使用？匹配任意单个字符，使用* 匹配任意多个字符，使用** 匹配任意多个目录
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                //.antMatchers("/app/api/**").permitAll()

                // 开启captcha.jpg 的访问权限
                .antMatchers("/app/api/**","/captcha.jpg").permitAll()

                .anyRequest().authenticated() // 匹配任意URL 请求

                .and()
                //指定自定义登录页，并注册一个POST 路由，用于接收登录请求。
                .formLogin()
                .loginPage("/myLogin.html")
                // 自定义登录URL
                //.loginProcessingUrl("/login")
                .loginProcessingUrl("/auth/form")
                //设置登录页不需要访问权限
                .permitAll()

                // 制定处理登陆成功时处理逻辑,该方法带有Authentication 参数，携带当前用户名级角色等信息
                /*.successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request
                            , HttpServletResponse response
                            , Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"欢迎登陆系统\"}");
                    }
                })

                // 制定登陆失败时的处理逻辑，该方法带有AuthenticationException 异常参数，处理方式按照系统的情况自定义
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request
                            , HttpServletResponse response
                            , AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"error_code\":\"401\"," +
                                "\"name\":" + exception.getClass() +
                                "\"message\":" + exception.getMessage() + "}");
                    }
                })*/
                .and()
                // csrf（）：提供跨站请求伪造防护功能，继承WebSecurityConfigurerAdapter 时，会默认开启csrf。
                .csrf().disable()
                .sessionManagement()// session管理
                .maximumSessions(1);

    }


    /**
     * 允许我们配置认证用户
     * * @param  authenticationManagerBuilder
     * @throws Exception
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("123456").roles("ADMIN")
                .and()
                .withUser("user").password("123456").roles("USER");
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 基于内存认证的多用户支持
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService() {
        // UserDetailService：抽象接口，Spring Security支持各种来源的用户数据，包括内存、数据库、LDAP 等。
        //InMemoryUserDetailsManager：实现UserDetailService 接口，将用户数据源存在内存中。
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("123456").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("123456").roles("ADMIN").build());
        return manager;
    }*/

    /*@Autowired
    private DataSource dataSource;

    *//**
     * 使用数据库管理用户, 自定义实现则注销
     * @return
     *//*
    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        if(!manager.userExists("user")){
            manager.createUser(User.withUsername("user").password("123456").roles("USER").build());
        }
        if(!manager.userExists("admin")){
            manager.createUser(User.withUsername("admin").password("123456").roles("ADMIN").build());
        }
        return manager;
    }*/

    /**
     *  自定义过滤器：
     *      1、addFilterAfter：将自定义过滤器添加在指定过滤器之后。
     *      2、addFilterBefore：将自定义过滤器添加在指定过滤器之前。
     *      3、addFilterAfter：添加一个过滤器，但必须继承Spring Security 自身提供的顾虑器或者其它可继承过滤器。
     *      4、addFilterAt：添加一个自定义过滤器在指定位置。
     */



}
