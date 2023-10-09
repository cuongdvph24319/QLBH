package com.example.qlbh.controller;

import com.example.qlbh.entity.Task;
import com.example.qlbh.model.TaskRequest;
import com.example.qlbh.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    @Resource(name = "taskService")
    TaskService taskService;

    @GetMapping("index")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(
            @RequestBody TaskRequest taskRequest
    ) {
        try {
            return ResponseEntity.ok(taskService.create(taskRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Lỗi");
    }

    @PutMapping("update/{name}")
    public ResponseEntity<Object> update(
            @PathVariable("name") String name,
            @RequestBody TaskRequest taskRequest
    ) {
        try {
            return ResponseEntity.ok(taskService.update(name, taskRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Lỗi");
    }

}
