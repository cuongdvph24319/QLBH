package com.example.qlbh.entity;

import com.example.qlbh.model.AccountRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accouts")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "relation_id")
    private Relation relation;

    public void loadAccountRequestC(AccountRequest accountRequest) {
        this.setMa(accountRequest.getMa());
        this.setTen(accountRequest.getTen());
        this.setEmail(accountRequest.getEmail());
        this.setMatKhau(accountRequest.getMatKhau());
        this.setRelation(Relation.builder().id(accountRequest.getId()).build());
    }

    public void loadAccountRequestU(AccountRequest accountRequest) {
        this.setTen(accountRequest.getTen());
        this.setEmail(accountRequest.getEmail());
        this.setMatKhau(accountRequest.getMatKhau());
        this.setRelation(Relation.builder().id(accountRequest.getId()).build());
    }
}
