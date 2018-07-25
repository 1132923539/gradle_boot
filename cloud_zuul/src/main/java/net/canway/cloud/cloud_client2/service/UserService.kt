package net.canway.cloud.cloud_client2.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService {

    @Autowired
    private val restTemplate: RestTemplate? = null


    //由于前面设置的服务发现主机不注册自己因此无法通过ribbon调用服务主机的接口
    //        String user = restTemplate.getForObject("http://enruke-server/getUser", String.class);
    val user: String?
        get() = restTemplate!!.getForObject<String>("http://client1/getUser", String::class.java)
}
