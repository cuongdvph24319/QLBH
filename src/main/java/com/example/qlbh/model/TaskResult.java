package com.example.qlbh.model;

import com.example.qlbh.entity.Task;
import lombok.Getter;

@Getter
public class TaskResult {
    private final String message;
    private Task task;

    public TaskResult(String error, Task task) {
        this.message = error;
        this.task = task;
    }

    public TaskResult(String error) {
        this.message = error;
    }

}
