package net.canway.controller;

import net.canway.service.DocmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocmentService docmentService;

    @GetMapping(value = "/queryAll", produces = MediaType.APPLICATION_JSON)
    public List query() {
        return docmentService.queryAll();
    }

    @PostMapping(value = "/addDocument", produces = MediaType.APPLICATION_JSON)
    public String addDocument() {
        return docmentService.addDocument().toString();
    }

    @GetMapping(value = "/deleteDocument/{title}", produces = MediaType.APPLICATION_JSON)
    public int deleteByTitle(@PathVariable String title) {
        return docmentService.deleteBook();
    }

    @PostMapping(value = "/queryByItem", produces = MediaType.APPLICATION_JSON)
    public List queryByItem() {
        return docmentService.queryByItem();
    }
}
