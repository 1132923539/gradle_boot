package net.canway.controller

import net.canway.service.DocmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import javax.ws.rs.core.MediaType

@RestController
class DocumentController {

    @Autowired
    private val docmentService: DocmentService? = null

    @GetMapping(value = "/queryAll", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun query(): List<*> {
        return docmentService!!.queryAll()
    }

    @PostMapping(value = "/addDocument", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun addDocument(): String {
        return docmentService!!.addDocument().toString()
    }

    @GetMapping(value = "/deleteDocument/{title}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun deleteByTitle(@PathVariable title: String): Int {
        return docmentService!!.deleteBook()
    }

    @PostMapping(value = "/queryByItem", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun queryByItem(): List<*> {
        return docmentService!!.queryByItem()
    }
}
