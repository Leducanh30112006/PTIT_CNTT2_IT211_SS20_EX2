package com.example.ptit_cntt2_it211_ss20_ex2.repository;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
}
