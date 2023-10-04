package com.example.qlbh.repository;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByMa(String ma);

    @Query(value = "select ma from Account")
    List<String> getAllCode();

    @Query(value = "select ma from Accounta", nativeQuery = true)
    List<String> getError();

    Account findAccountByMa(String ma);

    Page<Account> findAll(Specification<AccountDTO> specification, Pageable pageable);

    @Query(value = "Select a.ma, a.ten, a.matKhau, a.email, a.relation.tenNQH, a.relation.hoTen from Account a " +
            "join Relation r on a.relation.id = r.id where 1 = 1" +
            " and (:ma is null or a.ma = :ma)" +
            " and (:ten is null or a.ten like %:ten%)" +
            " and (:hoten is null or a.relation.hoTen like %:hoten%)")
    Page<Account> getAll(@Param("ma") String ma, @Param("ten") String ten, @Param("hoten") String hoten, Pageable pageable);


    @Query(value = "SELECT a.id, a.relation_id, a.ma, a.ten, matkhau, email\n" +
            "\tFROM public.accouts a join relation r on a.relation_id = r.id where true\n" +
            "\tand (:ma is null or a.ma = :ma)\n" +
            "\tand (:ten is null or a.ten like %:ten%)\n" +
            "\tand (:hoten is null or r.ho_ten like %:hoten%)\n" +
            "\t;", nativeQuery = true)
    Page<Account> getAllN(@Param("ma") String ma, @Param("ten") String ten, @Param("hoten") String hoten, Pageable pageable);
}
