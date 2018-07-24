package net.canway.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;


/**
 * 配置jersay
 */
@Component
@ApplicationPath("rest")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(RequestContextFilter.class);
        packages("net.canway.controller");
        register(JacksonFeature.class);
        register(RequestContextFilter.class);
//        register(LoggingFilter.class);
        register(SpringComponentProvider.class);
    }
}
