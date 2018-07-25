package net.canway.cloud.cloud_client2.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class Config {
    @Bean
    @LoadBalanced
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
