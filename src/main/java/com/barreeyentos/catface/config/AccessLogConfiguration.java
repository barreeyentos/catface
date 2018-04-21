package com.barreeyentos.catface.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;

/**
 * Configure tomcat logback valve
 */
@Configuration
public class AccessLogConfiguration {

    private static final String LOGBACK_ACCESS_XML = "logback-access.xml";

    /**
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<WebServerFactory> containerCustomizer() {
        return container -> {
            if (container instanceof TomcatServletWebServerFactory) {
                TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) container;
                LogbackValve logbackValve = new LogbackValve();
                logbackValve.setFilename(LOGBACK_ACCESS_XML);
                logbackValve.setQuiet(true);
                containerFactory.addContextValves(logbackValve);
            }
        };
    }
}
