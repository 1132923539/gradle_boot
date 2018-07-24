package net.canway.gradle_boot_client;

import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradleBootClientApplicationTests {

    @Autowired
    ProcessEngine processEngine;

    @Test
    public void contextLoads() {
    }

    /**
     * 删除部署信息
     */
    @Test
    public void deleteDeployment() {
        String deploymentId = "1";
        // 第二个参数代表级联操作
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
        // 删除所有相关的activiti信息
    }
}
