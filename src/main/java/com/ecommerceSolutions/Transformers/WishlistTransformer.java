package com.ecommerceSolutions.Transformers;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateWishlistDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.WishlistResponseDto;
import com.ecommerceSolutions.Models.Wishlist;

public class WishlistTransformer {

    public  static Wishlist wishlistDtoToWishlist(CreateWishlistDto createWishlistDto) {
        return Wishlist.builder()
                .title(createWishlistDto.getTitle())
                .build();
    }

    public static WishlistResponseDto wishlistToWishlistDto (Wishlist wishlist) {
        return WishlistResponseDto.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .title(wishlist.getTitle())
                .build();
    }
}
