package com.example.qlbh.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    private String name;

    private String description;

    private String priority;

    private String pic;

    private String owner;
}
