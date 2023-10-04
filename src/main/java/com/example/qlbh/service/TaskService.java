package com.example.qlbh.service;



import com.example.qlbh.entity.Task;
import com.example.qlbh.model.TaskRequest;

import java.util.List;

public interface TaskService {
    List<Task> getAllTask();

    Task create(TaskRequest taskRequest);

    Task update(String name, TaskRequest taskRequest);
}
