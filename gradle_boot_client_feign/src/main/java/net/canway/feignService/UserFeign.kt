package net.canway.feignService

import net.canway.pojo.tables.pojos.User
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * @author eltons
 */
@Component
@FeignClient(value = "GradleBootApplicationServer")
interface UserFeign {

    @LoadBalanced
    @RequestMapping(value = "/user", method = arrayOf(RequestMethod.GET))
    fun queryAllUser(): String

    @LoadBalanced
    @RequestMapping(value = "/user/{id}", method = arrayOf(RequestMethod.GET))
    fun queryUserById(@PathVariable("id") id: Int?): String

    @LoadBalanced
    @RequestMapping(value = "/user/add", method = arrayOf(RequestMethod.POST))
    fun addUser(user: User): String

    @LoadBalanced
    @RequestMapping(value = "/user/update", method = arrayOf(RequestMethod.PUT))
    fun updateUser(user: User): String

    @LoadBalanced
    @RequestMapping(value = "/user/{id}", method = arrayOf(RequestMethod.DELETE))
    fun deleteUserById(@PathVariable("id") id: Int?): String

}
