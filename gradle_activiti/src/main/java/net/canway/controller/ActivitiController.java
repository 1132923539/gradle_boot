package net.canway.controller;

import net.canway.service.ActivitiService;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

/**
 * @author eltons
 */

@RestController
public class ActivitiController {
    @Autowired
    private ActivitiService leaveservice;
    @Autowired
    private RepositoryService rep;
    @Autowired
    RuntimeService runservice;
    @Autowired
    FormService formservice;
    @Autowired
    IdentityService identityservice;
    @Autowired
    TaskService taskservice;
    @Autowired
    HistoryService histiryservice;


    /**
     * 通过上传的工作流文件部署工作流
     *
     * @param uploadfile
     * @param request
     * @return
     */
    @GetMapping("/uploadworkflow")
    public String fileupload(@RequestParam MultipartFile uploadfile, HttpServletRequest request) {
        try {
            MultipartFile file = uploadfile;
            String filename = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            rep.createDeployment().addInputStream(filename, is).deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 部署默认流程
     *
     * @return
     */
    @GetMapping("/deploydefult")
    public String deployAct() {
        leaveservice.deploy();
        return "部署成功";
    }

    /**
     * 查询已经部署的流程
     *
     * @return
     */
    @GetMapping(value = "/queryActList", produces = MediaType.APPLICATION_JSON)
    public List<Deployment> queryActList() {
        List<Deployment> deploymentList = leaveservice.findDeploymentList();
        return deploymentList;
    }

    /**
     * 开启一个简单流程，需传入流程id
     *
     * @param processId
     * @return
     */
    @GetMapping(value = "/startProcess/{processId}", produces = MediaType.APPLICATION_JSON)
    public String startWorkfolw(@PathVariable String processId) {
        return leaveservice.startProcess(processId);
    }

    //根据任务id执行任务
    @GetMapping(value = "/startProcess/{taskId}", produces = MediaType.APPLICATION_JSON)
    public String completeTask(@PathVariable String taskId) {
        leaveservice.dealTask(taskId);
        return "success";
    }
}
