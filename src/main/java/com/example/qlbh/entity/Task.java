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
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
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

    public Task(TaskRequest taskRequest) {
        this.setName(taskRequest.getName());
        this.setDescription(taskRequest.getDescription());
        this.setPriority(taskRequest.getPriority());
        this.setPic(taskRequest.getPic());
        this.setOwner(taskRequest.getOwner());
    }

    public void taskUpdate(TaskRequest taskRequest) {
        this.setDescription(taskRequest.getDescription());
        this.setPriority(taskRequest.getPriority());
        this.setPic(taskRequest.getPic());
        this.setOwner(taskRequest.getOwner());
    }
}
