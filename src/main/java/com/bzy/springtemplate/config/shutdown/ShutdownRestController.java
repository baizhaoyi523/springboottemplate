package com.bzy.springtemplate.config.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ShutdownRestController implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    @PostMapping("admin/shutdown")
    public void shutdown() {
        if (context == null) {
            log.warn("cannot shutdown because context is null");
            return;
        }
        context.close();
    }

    @GetMapping("test")
    public String test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "test";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
