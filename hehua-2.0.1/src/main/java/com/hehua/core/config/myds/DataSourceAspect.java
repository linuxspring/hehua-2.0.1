//package com.pinyi.mian.config.myds;
//
//
//
//import com.pinyi.mian.config.MyDataSource;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * Created by Administrator on 2019/4/24.
// * IntelliJ IDEA 2019 of gzcss
// */
//
//@Aspect
//@Component
//@Order(-100)
//public class DataSourceAspect implements Ordered {
//    protected Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Pointcut("@annotation(com.pinyi.mian.config.MyDataSource)")
//    public void dataSourcePointCut() {
//
//    }
//
//    @Around("dataSourcePointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//
//        MyDataSource ds = method.getAnnotation(MyDataSource.class);
//        if(ds == null){
//            DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
//            logger.debug("set myds is " + DataSourceNames.DEFAULT);
//        }else {
//            DynamicDataSource.setDataSource(ds.name());
//            logger.debug("set myds is " + ds.name());
//        }
//
//        try {
//            return point.proceed();
//        } finally {
//            DynamicDataSource.clearDataSource();
//            logger.debug("clean myds");
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//}