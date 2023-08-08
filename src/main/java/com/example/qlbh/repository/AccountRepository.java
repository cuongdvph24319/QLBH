package com.example.qlbh.repository;

import com.example.qlbh.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByMa(String ma);

    Account findAccountByMa(String ma);

}
