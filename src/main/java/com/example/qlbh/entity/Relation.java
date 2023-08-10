package com.example.qlbh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "relation")
@Builder
public class Relation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "moi_quan_he")
    private String tenNQH;

    @Column(name = "ho_ten")
    private String hoTen;

    @JsonIgnore
    @OneToMany(mappedBy = "relation", fetch = FetchType.LAZY)
    private List<Account> accountList = new ArrayList<>();
}
