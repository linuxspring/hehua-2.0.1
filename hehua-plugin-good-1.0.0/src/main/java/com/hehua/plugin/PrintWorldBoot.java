package com.hehua.plugin;

import com.hehua.core.PluginBootStrap;
import org.springframework.context.ConfigurableApplicationContext;

public class PrintWorldBoot implements PluginBootStrap {

    @Override
    public void boot(ConfigurableApplicationContext context) {
        System.out.println("world");
    }
}
