package com.example.qlbh.service;



import com.example.qlbh.entity.Task;
import com.example.qlbh.model.TaskRequest;
import com.example.qlbh.model.TaskResult;

import java.util.List;


public interface TaskService {
    List<Task> getAllTask();

    Object create(TaskRequest taskRequest);

    TaskResult update(String name, TaskRequest taskRequest);
}
