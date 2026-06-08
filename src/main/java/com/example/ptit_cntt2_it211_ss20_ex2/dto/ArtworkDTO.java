package com.example.ptit_cntt2_it211_ss20_ex2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkDTO {
    private Long id;
    private String title;
    private String description;
    private boolean isPublished;
    private String owner;
}
