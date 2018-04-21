package com.barreeyentos.catface.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().authorizeRequests()
                //
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                // Anyone can access the urls
                .antMatchers("/v1/**").permitAll()
                //
                .and().formLogin().disable().csrf().disable().logout().disable();
    }
}