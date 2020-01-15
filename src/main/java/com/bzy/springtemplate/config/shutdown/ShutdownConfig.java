package com.bzy.springtemplate.config.shutdown;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.time.Instant.now;

@Configuration
@RequiredArgsConstructor
public class ShutdownConfig {

    private final ShutdownProperties shutdownProperties;

    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown(shutdownProperties);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdown());
        return tomcat;
    }

    @Slf4j
    private static class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

        private volatile Connector connector;
        private ShutdownProperties shutdownProperties;

        public GracefulShutdown(ShutdownProperties shutdownProperties) {
            this.shutdownProperties = shutdownProperties;
        }

        @Override
        public void customize(Connector connector) {
            this.connector = connector;
        }

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            shutdownProperties.setEntireServiceEnabled(false);
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    log.info("shutdown start");
                    log.info("active count: " + threadPoolExecutor.getActiveCount());
                    if (threadPoolExecutor.getActiveCount() <= 1) {
                        threadPoolExecutor.awaitTermination(1, TimeUnit.NANOSECONDS);
                    } else {
                        threadPoolExecutor.shutdown();
                    }
                    log.info("shutdown end");
                    int waitSeconds = 120;
                    Instant start = now();
                    while (true) {
                        log.info("active count: " + threadPoolExecutor.getActiveCount());
                        if (threadPoolExecutor.getActiveCount() <= 1) {
                            threadPoolExecutor.awaitTermination(1, TimeUnit.NANOSECONDS);
                            break;
                        }
                        if (now().isAfter(start.plusSeconds(waitSeconds))) {
                            log.info("force shutdown");
                            threadPoolExecutor.awaitTermination(1, TimeUnit.NANOSECONDS);
                            break;
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }
                    log.info("shutdown success");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

