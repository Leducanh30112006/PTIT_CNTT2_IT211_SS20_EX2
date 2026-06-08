package com.example.ptit_cntt2_it211_ss20_ex2.service;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Artwork;
import com.example.ptit_cntt2_it211_ss20_ex2.dto.ArtworkDTO;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public List<ArtworkDTO> getArtworks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String currentUsername = authentication.getName();
        boolean isAdmin = roles.contains("ROLE_ADMIN");
        boolean isArtist = roles.contains("ROLE_ARTIST");

        List<Artwork> allArtworks = artworkRepository.findAll();

        return allArtworks.stream()
                .filter(artwork -> {
                    if (isAdmin) {
                        return true; // Admin sees everything
                    } else if (isArtist) {
                        return artwork.isPublished() || (artwork.getOwner() != null && artwork.getOwner().getUsername().equals(currentUsername));
                    }
                    return false; // Default deny
                })
                .map(artwork -> new ArtworkDTO(
                        artwork.getId(),
                        artwork.getTitle(),
                        artwork.getDescription(),
                        artwork.isPublished(),
                        artwork.getOwner() != null ? artwork.getOwner().getUsername() : null
                ))
                .collect(Collectors.toList());
    }
}
