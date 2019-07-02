package com.hehua.plugin.system;

import com.hehua.core.PluginBootStrap;
import org.springframework.context.ConfigurableApplicationContext;

public class PrintWorldBoot implements PluginBootStrap {

    @Override
    public void boot(ConfigurableApplicationContext context) {
        System.out.println("world");
    }
}
