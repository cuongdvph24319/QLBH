package com.example.qlbh.repository;

import com.example.qlbh.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {
    @Query(value = "select id from Relation ")
    List<Integer> getAllId();
}
