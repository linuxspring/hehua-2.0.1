package com.hehua.core;

import org.springframework.context.ConfigurableApplicationContext;

public interface PluginBootStrap {
    void boot(ConfigurableApplicationContext context);
}