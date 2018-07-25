package net.canway.service

import org.activiti.engine.*
import org.activiti.engine.form.TaskFormData
import org.activiti.engine.repository.Deployment
import org.activiti.engine.repository.DeploymentBuilder
import org.activiti.engine.repository.ProcessDefinition
import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Comment
import org.activiti.engine.task.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.io.File
import java.io.FileInputStream
import java.util.ArrayList
import java.util.zip.ZipInputStream

//import org.apache.commons.lang.StringUtils;

/**
 * @author eltons
 */
@Service
class ActivitiService {

    @Autowired
    private val repositoryService: RepositoryService? = null

    @Autowired
    private val runtimeService: RuntimeService? = null

    @Autowired
    private val taskService: TaskService? = null

    @Autowired
    private val formService: FormService? = null

    @Autowired
    private val historyService: HistoryService? = null

    @Autowired
    private val processEngine: ProcessEngine? = null

    /**
     * 部署预先创建的流程图
     */
    fun deploy() {
        val repositoryService = processEngine!!.repositoryService
        val deployment = repositoryService.createDeployment()
        deployment.addClasspathResource("bpmn/leave.bpmn")
                .name("请求单流程2")
                .category("办公类别")
                .deploy()
    }


    /**
     * 指定一个流程id并开启一个流程
     *
     * @param processId
     */
    fun startProcess(processId: String): String {
        val runtimeService = processEngine!!.runtimeService
        val pi = runtimeService.startProcessInstanceByKey(processId)
        return "success"
    }

    /**
     * 部署流程定义,通过用户上传的流程bpmn文件来部署流程
     */
    fun saveNewDeploye(file: File, filename: String) {
        try {
            val zipInputStream = ZipInputStream(FileInputStream(file))
            repositoryService!!.createDeployment()
                    .name(filename)
                    .addZipInputStream(zipInputStream)
                    .deploy()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 根据taskId执行任务
     */
    fun dealTask(taskId: String) {
        processEngine!!.taskService.complete(taskId)
    }

    /**
     * 查询部署对象信息，对应表（act_re_deployment）
     */
    fun findDeploymentList(): List<Deployment> {
        return repositoryService!!.createDeploymentQuery()
                .orderByDeploymenTime().asc()
                .list()
    }

    /**
     * 查询流程定义的信息，对应表（act_re_procdef）
     */
    fun findProcessDefinitionList(): List<ProcessDefinition> {
        return repositoryService!!.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()
                .list()
    }


    /**
     * 使用部署对象ID，删除流程定义
     */
    fun deleteProcessDefinitionByDeploymentId(deploymentId: String) {
        repositoryService!!.deleteDeployment(deploymentId, true)
    }


    /**
     * 使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
    </Task> */
    fun findTaskListByName(name: String): List<Task> {
        return taskService!!.createTaskQuery()
                .taskAssignee(name)
                .orderByTaskCreateTime().asc()
                .list()
    }

    /**
     * 使用任务ID，获取当前任务节点中对应的Form key中的连接的值
     */
    fun findTaskFormKeyByTaskId(taskId: String): String {
        val formData = formService!!.getTaskFormData(taskId)
        return formData.formKey
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
    fun findCommentByTaskId(taskId: String): List<Comment> {
        var list: List<Comment> = ArrayList()
        val task = taskService!!.createTaskQuery()
                .taskId(taskId)
                .singleResult()
        val processInstanceId = task.processInstanceId

        list = taskService.getProcessInstanceComments(processInstanceId)
        return list
    }


    /**
     * 获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
     */
    fun findProcessDefinitionByTaskId(taskId: String): ProcessDefinition {
        val task = taskService!!.createTaskQuery()
                .taskId(taskId)
                .singleResult()
        val processDefinitionId = task.processDefinitionId
        return repositoryService!!.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef
                .processDefinitionId(processDefinitionId)
                .singleResult()
    }


}
