package com.example.ptit_cntt2_it211_ss20_ex2.repository;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
