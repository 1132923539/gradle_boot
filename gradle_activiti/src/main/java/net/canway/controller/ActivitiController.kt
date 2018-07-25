package net.canway.controller

import net.canway.service.ActivitiService
import org.activiti.engine.*
import org.activiti.engine.repository.Deployment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.MediaType
import java.io.InputStream

/**
 * @author eltons
 */

@RestController
class ActivitiController {
    @Autowired
    private val leaveservice: ActivitiService? = null
    @Autowired
    private val rep: RepositoryService? = null
    @Autowired
    internal var runservice: RuntimeService? = null
    @Autowired
    internal var formservice: FormService? = null
    @Autowired
    internal var identityservice: IdentityService? = null
    @Autowired
    internal var taskservice: TaskService? = null
    @Autowired
    internal var histiryservice: HistoryService? = null


    /**
     * 通过上传的工作流文件部署工作流
     *
     * @param uploadfile
     * @param request
     * @return
     */
    @GetMapping("/uploadworkflow")
    fun fileupload(@RequestParam uploadfile: MultipartFile, request: HttpServletRequest): String {
        try {
            val filename = uploadfile.originalFilename
            val `is` = uploadfile.inputStream
            rep!!.createDeployment().addInputStream(filename, `is`).deploy()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "success"
    }

    /**
     * 部署默认流程
     *
     * @return
     */
    @GetMapping("/deploydefult")
    fun deployAct(): String {
        leaveservice!!.deploy()
        return "部署成功"
    }

    /**
     * 查询已经部署的流程
     *
     * @return
     */
    @GetMapping(value = "/queryActList", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun queryActList(): List<Deployment> {
        return leaveservice!!.findDeploymentList()
    }

    /**
     * 开启一个简单流程，需传入流程id
     *
     * @param processId
     * @return
     */
    @GetMapping(value = "/startProcess/{processId}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun startWorkfolw(@PathVariable processId: String): String {
        return leaveservice!!.startProcess(processId)
    }

    //根据任务id执行任务
    @GetMapping(value = "/startProcess/{taskId}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun completeTask(@PathVariable taskId: String): String {
        leaveservice!!.dealTask(taskId)
        return "success"
    }
}
