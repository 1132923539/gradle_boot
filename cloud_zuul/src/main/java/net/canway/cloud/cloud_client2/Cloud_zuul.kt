package net.canway.cloud.cloud_client2

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
open class Cloud_zuul

fun main(args: Array<String>) {
    SpringApplication.run(Cloud_zuul::class.java, *args)
}


