package com.example.ptit_cntt2_it211_ss20_ex2.repository;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.TokenSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {
    Optional<TokenSession> findByRefreshTokenValue(String refreshTokenValue);
    List<TokenSession> findAllByAccountId(Long accountId);
}
