package net.canway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
open class GradleBootClientApplication

fun main(args: Array<String>) {
    SpringApplication.run(GradleBootClientApplication::class.java, *args)
}

