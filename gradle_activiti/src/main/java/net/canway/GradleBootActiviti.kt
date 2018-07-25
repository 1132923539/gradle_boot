package net.canway

import org.activiti.spring.boot.SecurityAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
@EnableDiscoveryClient
open class GradleBootActiviti

fun main(args: Array<String>) {
    SpringApplication.run(GradleBootActiviti::class.java, *args)
}

