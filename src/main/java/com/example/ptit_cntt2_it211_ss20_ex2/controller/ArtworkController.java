package com.example.ptit_cntt2_it211_ss20_ex2.controller;

import com.example.ptit_cntt2_it211_ss20_ex2.dto.ArtworkDTO;
import com.example.ptit_cntt2_it211_ss20_ex2.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gallery/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public ResponseEntity<List<ArtworkDTO>> getArtworks() {
        return ResponseEntity.ok(artworkService.getArtworks());
    }
}
