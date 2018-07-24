package net.canway.service;

import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

//import org.apache.commons.lang.StringUtils;

/**
 * @author eltons
 */
@Service
public class ActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署预先创建的流程图
     */
    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("bpmn/leave.bpmn")
                .name("请求单流程2")
                .category("办公类别")
                .deploy();
    }


    /**
     * 指定一个流程id并开启一个流程
     *
     * @param processId
     */
    public String startProcess(String processId) {
        String processDefikey = processId;
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefikey);
        return "success";
    }

    /**
     * 部署流程定义,通过用户上传的流程bpmn文件来部署流程
     */
    public void saveNewDeploye(File file, String filename) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            repositoryService.createDeployment()
                    .name(filename)
                    .addZipInputStream(zipInputStream)
                    .deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据taskId执行任务
     */
    public void dealTask(String taskId) {
        processEngine.getTaskService().complete(taskId);
    }

    /**
     * 查询部署对象信息，对应表（act_re_deployment）
     */
    public List<Deployment> findDeploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .orderByDeploymenTime().asc()
                .list();
        return list;
    }

    /**
     * 查询流程定义的信息，对应表（act_re_procdef）
     */
    public List<ProcessDefinition> findProcessDefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()
                .list();
        return list;
    }


    /**
     * 使用部署对象ID，删除流程定义
     */
    public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }


    /**
     * 使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
     */
    public List<Task> findTaskListByName(String name) {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(name)
                .orderByTaskCreateTime().asc()
                .list();
        return list;
    }

    /**
     * 使用任务ID，获取当前任务节点中对应的Form key中的连接的值
     */
    public String findTaskFormKeyByTaskId(String taskId) {
        TaskFormData formData = formService.getTaskFormData(taskId);
        String url = formData.getFormKey();
        return url;
    }

//    /**
//     * 使用任务ID，查找请假单ID，从而获取请假单信息
//     */
//    public String findLeaveBillByTaskId(String taskId) {
//
//        Task task = taskService.createTaskQuery()
//                .taskId(taskId)
//                .singleResult();
//
//        String processInstanceId = task.getProcessInstanceId();
//        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//        String buniness_key = pi.getBusinessKey();
//        String id = "";
//        if (StringUtils.isNotBlank(buniness_key)) {
//            id = buniness_key.split("\\.")[1];
//        }
//        return id;
//    }

    /**
     * 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注
     */
    public List<Comment> findCommentByTaskId(String taskId) {
        List<Comment> list = new ArrayList<Comment>();
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();

        list = taskService.getProcessInstanceComments(processInstanceId);
        return list;
    }


    /**
     * 获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
     */
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return pd;
    }


}
