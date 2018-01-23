package com.barreeyentos.catface.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
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
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return container -> {
			if (container instanceof TomcatEmbeddedServletContainerFactory) {
				TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;
				LogbackValve logbackValve = new LogbackValve();
				logbackValve.setFilename(LOGBACK_ACCESS_XML);
				logbackValve.setQuiet(true);
				containerFactory.addContextValves(logbackValve);
			}
		};
	}
}
