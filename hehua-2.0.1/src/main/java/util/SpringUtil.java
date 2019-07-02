package util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * Created by Administrator on 2019/4/22.
 * IntelliJ IDEA 2019 of gzcss
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
        }
        System.out.println("--------获取applicationContext----------");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static String[] controllers(Class<? extends Annotation>  clazz){
        return getApplicationContext().getBeanNamesForAnnotation(clazz);
    }


    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}