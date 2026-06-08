package com.example.ptit_cntt2_it211_ss20_ex2.repository;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
