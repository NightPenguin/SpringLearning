package com.springboot.jwt.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaProducerConfig {

    /**
     * 图形验证码
     * @return
     */
    @Bean
    public Producer captcha(){
        Properties properties = new Properties();
        // 设定图片长宽
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.height","150");
        // 设定字符集、字符长度
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        properties.setProperty("kaptcha.textproducer.char.length","4");

        Config config = new Config(properties);
        // 自定义实现图形验证码
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
