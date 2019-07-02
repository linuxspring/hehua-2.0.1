package com.hehua.core;

import com.hehua.core.conf.DataSource;
import com.hehua.core.conf.DataSourceEnum;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableScheduling
@SpringBootApplication
@Controller
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@EnableDiscoveryClient
@EnableCaching
//@MapperScan("com.pinyi.plugin.devastate.alarm.mapper")
//@MapperScan("com.hehua.plugin.*.mapper")
//@ComponentScan("com.hehua.plugin.*")
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Description("保存IP封堵接口")
    @RequestMapping(value = "/aop.data",method = RequestMethod.GET)
    @ResponseBody
    @DataSource(DataSourceEnum.DB2)
    public String saveAlarm(Integer index,Integer size){
        return "aop";
    }

    @RequestMapping("/login")
    public ModelAndView test(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("404");
        mv.addObject("name", "liyafei");

        mv.addObject("user", "xiaotolove");

        //设置返回的数据为json类型，也可以不设置，返回对象
        //mv.setView(new MappingJackson2JsonView());
        return mv;
    }



    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        //corsConfiguration.addExposedHeader(HttpHeaderConStant.X_TOTAL_COUNT);
        return  corsConfiguration;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
