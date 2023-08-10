package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.service.AccountService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;



    @Override
    public Account create(AccountRequest accountRequest) {
        Account account = new Account();
        account.loadAccountRequestC(accountRequest);
        return accountRepository.save(account);
    }

    @Override
    public Account update(String ma, AccountRequest accountRequest) {
        Account account = accountRepository.findAccountByMa(ma);
        account.loadAccountRequestU(accountRequest);
        return accountRepository.save(account);
    }

    @Override
    public Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable) {

        Specification<AccountDTO> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (maAccount != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("ma"), maAccount
                ));
            }
            if (tenAccount != null) {
                predicates.add(criteriaBuilder.like(
                        root.get("ten"), "%" + tenAccount + "%"
                ));
            }
            if (tenNQH != null) {
                predicates.add(criteriaBuilder.like(
                        root.get("relation").get("hoTen"), "%" + tenNQH + "%"
                ));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };

        Page<Account> accounts = accountRepository.findAll(specification, pageable);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : accounts.getContent()
        ) {
            accountDTOS.add(new AccountDTO(account));
        }
        accountDTOS = accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
        return new PageImpl<>(accountDTOS, pageable, accounts.getTotalElements());

    }
}
