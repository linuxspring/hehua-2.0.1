package com.hehua.core.conf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(-1)
public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@within(com.hehua.core.conf.DataSource) || @annotation(com.hehua.core.conf.DataSource)")
    public void pointCut(){

    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        logger.info("选择数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }

    @Around("@annotation(dataSource)")
    public Object around(ProceedingJoinPoint pjp, DataSource dataSource) {
        //获取注解里的值
        System.out.println("in around");
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

}
