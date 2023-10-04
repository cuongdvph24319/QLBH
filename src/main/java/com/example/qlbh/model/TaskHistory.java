package com.example.qlbh.entity;


import com.example.qlbh.model.TaskRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private String priority;

    @Column(name = "pic")
    private String pic;

    @Column(name = "owner")
    private String owner;

    @CreatedBy
    @Column(name = "createdby")
    private String createdBy;

    @CreatedDate
    @Column(name = "createddate")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updatedby")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updateddate")
    private LocalDateTime updatedDate;

    @Column(name = "method")
    private String method;

    public TaskHistory(Task task) {
        this.setId(task.getId());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setPriority(task.getPriority());
        this.setPic(task.getPic());
        this.setOwner(task.getOwner());
        this.setCreatedBy(task.getCreatedBy());
        this.setCreatedDate(task.getCreatedDate());
        this.setUpdatedBy(task.getUpdatedBy());
        this.setUpdatedDate(task.getUpdatedDate());
    }
}