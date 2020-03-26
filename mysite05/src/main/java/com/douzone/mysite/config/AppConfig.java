package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.app.DBConfig;
import com.douzone.mysite.config.app.MybatisConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.mysite.service", "com.douzone.mysite.repository", "com.douzone.mysite.aspect"})
@Import({DBConfig.class, MybatisConfig.class})
public class AppConfig {

}
