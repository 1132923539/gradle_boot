package net.canway.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import net.canway.feignService.UserFeign
import net.canway.pojo.tables.pojos.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

import javax.ws.rs.core.MediaType


/**
 * @author eltons
 */

@RestController
@RequestMapping("/user")
class UserControl {

    //    @Autowired
    //    private UserServiceImpl userService;


    @Autowired
    internal var userFeign: UserFeign? = null

    internal var restTemplate = RestTemplate()


    @ApiOperation(value = "获取用户列表", notes = "")
    @GetMapping(value = "", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun findAll(): String {
        return userFeign!!.queryAllUser()
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询用户", notes = "根据url的id来指定查询用户信息")
    @GetMapping(value = "/{id}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun findUserById(@PathVariable("id") id: Int?): String {
        return userFeign!!.queryUserById(id)
    }


    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    //    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")

    @PostMapping(value = "/add", consumes = arrayOf(MediaType.APPLICATION_JSON))
    fun addUser(user: User): String {
        return userFeign!!.addUser(user)
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams(ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer"))//            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PutMapping(value = "/update", consumes = arrayOf(MediaType.APPLICATION_JSON), produces = arrayOf(MediaType.APPLICATION_JSON))
    fun updateUser(user: User): String {
        return userFeign!!.updateUser(user)
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}")
    fun deleteUser(@PathVariable("id") id: Int?): String {

        return userFeign!!.deleteUserById(id)
    }

    @GetMapping(value = "/user1", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun queryAll(): String? {
        return restTemplate.getForObject("http://localhost:7070/user", String::class.java)
    }


}
