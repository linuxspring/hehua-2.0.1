package com.hehua.plugin.test;

import com.hehua.core.example.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private HelloWorld helloWorld;

    //@Scheduled(fixedRate = 6)
    //@Scheduled(cron="*/6 * * * * ?")
    //@Scheduled(cron="*/6 * * * * ?")
    @Scheduled(cron= "${scheduled.cron}")
    protected void printHello() {
        helloWorld.printHello();
    }
}
