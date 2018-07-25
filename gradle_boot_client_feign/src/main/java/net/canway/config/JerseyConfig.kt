package net.canway.config

import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.spring.SpringComponentProvider
import org.glassfish.jersey.server.spring.scope.RequestContextFilter
import org.springframework.stereotype.Component

import javax.ws.rs.ApplicationPath


/**
 * 配置jersay
 */
@Component
@ApplicationPath("rest")
open class JerseyConfig : ResourceConfig() {
    init {
        register(RequestContextFilter::class.java)
        packages("net.canway.controller")
        register(JacksonFeature::class.java)
        register(RequestContextFilter::class.java)
        //        register(LoggingFilter.class);
        register(SpringComponentProvider::class.java)
    }
}
