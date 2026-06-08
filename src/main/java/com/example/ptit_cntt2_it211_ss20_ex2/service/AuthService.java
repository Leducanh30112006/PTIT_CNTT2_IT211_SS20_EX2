package com.example.ptit_cntt2_it211_ss20_ex2.service;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Account;
import com.example.ptit_cntt2_it211_ss20_ex2.domain.TokenSession;
import com.example.ptit_cntt2_it211_ss20_ex2.dto.JwtAuthenticationResponse;
import com.example.ptit_cntt2_it211_ss20_ex2.dto.LoginRequest;
import com.example.ptit_cntt2_it211_ss20_ex2.dto.RefreshTokenRequest;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.AccountRepository;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.TokenSessionRepository;
import com.example.ptit_cntt2_it211_ss20_ex2.security.CustomUserDetails;
import com.example.ptit_cntt2_it211_ss20_ex2.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AccountRepository accountRepository;
    private final TokenSessionRepository tokenSessionRepository;

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        TokenSession tokenSession = new TokenSession();
        tokenSession.setAccount(account);
        tokenSession.setRefreshTokenValue(refreshToken);
        tokenSession.setExpired(false);
        tokenSession.setRevoked(false);
        tokenSessionRepository.save(tokenSession);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    public Optional<JwtAuthenticationResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return tokenSessionRepository.findByRefreshTokenValue(refreshTokenRequest.getRefreshToken())
                .filter(tokenSession -> !tokenSession.isRevoked() && !tokenSession.isExpired())
                .map(tokenSession -> {
                    Account account = tokenSession.getAccount();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(account), null, new CustomUserDetails(account).getAuthorities());
                    String newAccessToken = tokenProvider.generateAccessToken(authentication);
                    return new JwtAuthenticationResponse(newAccessToken, refreshTokenRequest.getRefreshToken());
                });
    }

    public void logout(String username) {
        Account account = accountRepository.findByUsername(username).orElse(null);
        if (account == null) return;

        tokenSessionRepository.findAllByAccountId(account.getId()).stream()
                .filter(token -> !token.isRevoked() && !token.isExpired())
                .forEach(token -> {
                    token.setRevoked(true);
                    tokenSessionRepository.save(token);
                });
    }
}
