package com.hehua.plugin.test;

import com.hehua.core.PluginBootStrap;
import org.springframework.context.ConfigurableApplicationContext;

public class PrintWorldBoot implements PluginBootStrap {

    @Override
    public void boot(ConfigurableApplicationContext context) {
        System.out.println("world");
    }
}
