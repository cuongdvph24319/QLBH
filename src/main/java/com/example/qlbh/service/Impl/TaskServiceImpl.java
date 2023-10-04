package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Task;
import com.example.qlbh.entity.TaskHistory;
import com.example.qlbh.model.TaskRequest;
import com.example.qlbh.repository.TaskHistoryRepository;
import com.example.qlbh.repository.TaskRepository;
import com.example.qlbh.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task create(TaskRequest taskRequest) {
        Task taskNew = new Task(taskRequest);
        TaskHistory taskHistory = new TaskHistory(taskNew);
        taskHistory.setMethod("create");
        taskHistoryRepository.save(taskHistory);
        return taskRepository.save(taskNew);
    }

    @Override
    public Task update(String name, TaskRequest taskRequest) {
        Task task = taskRepository.getTaskByName(name);
        task.taskUpdate(taskRequest);
        TaskHistory taskHistory = new TaskHistory(task);
        taskHistory.setMethod("update");
        taskHistoryRepository.save(taskHistory);
        return taskRepository.save(task);
    }


}
