package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Task;
import com.example.qlbh.entity.TaskHistory;
import com.example.qlbh.model.TaskRequest;
import com.example.qlbh.model.TaskResult;
import com.example.qlbh.repository.TaskHistoryRepository;
import com.example.qlbh.repository.TaskRepository;
import com.example.qlbh.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Resource(name = "taskRepository")
    TaskRepository taskRepository;

    @Resource(name = "taskHistoryRepository")
    TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Object create(TaskRequest taskRequest) {
        Task taskNew = new Task(taskRequest);
        if (taskRepository.existsByName(taskNew.getName())) {
            return new TaskResult("Name này đã tồn tại");
        }
        taskRepository.save(taskNew);
        TaskHistory taskHistory = new TaskHistory(taskNew);
        taskHistory.setMethod("create");
        taskHistoryRepository.save(taskHistory);
        return new TaskResult("Thêm mới thành công", taskNew);
    }

    @Override
    public TaskResult update(String name, TaskRequest taskRequest) {
        Task task = taskRepository.getTaskByName(name);
        if (task == null) {
            return new TaskResult("Name này không tồn tại");
        }
        task.taskUpdate(taskRequest);
        TaskHistory taskHistory = new TaskHistory(task);
        taskHistory.setMethod("update");
        taskHistoryRepository.save(taskHistory);
        return new TaskResult("Cập nhật thành công", task);
    }


}
