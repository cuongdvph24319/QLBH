package com.example.qlbh.repository;

import com.example.qlbh.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task getTaskByName(String name);

    boolean existsByName(String name);
}
