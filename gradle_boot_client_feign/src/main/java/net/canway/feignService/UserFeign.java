package net.canway.feignService;

import net.canway.pojo.tables.pojos.User;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author eltons
 */
@Component
@FeignClient(value = "GradleBootApplicationServer")
public interface UserFeign {

    @LoadBalanced
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    String queryAllUser();

    @LoadBalanced
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    String queryUserById(@PathVariable("id") Integer id);

    @LoadBalanced
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    String addUser(User user);

    @LoadBalanced
    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    String updateUser(User user);

    @LoadBalanced
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    String deleteUserById(@PathVariable("id") Integer id);

}
