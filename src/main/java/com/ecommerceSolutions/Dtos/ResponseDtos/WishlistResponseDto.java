package com.ecommerceSolutions.Dtos.ResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDto {
    private Long id;
    private Long userId;
    private String title;

    public WishlistResponseDto(String title) {
        this.title = title;
    }
}
