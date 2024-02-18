package com.ecommerceSolutions.Services;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateWishlistDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.WishlistResponseDto;
import com.ecommerceSolutions.Exceptions.UserDoesNotExistWithEmail;
import com.ecommerceSolutions.Exceptions.WishlistIsNotExistWithId;

import java.util.List;

public interface WishlistService {
    WishlistResponseDto createWishlist(CreateWishlistDto createWishlistDto) throws Exception;

    List<WishlistResponseDto> getAllWishlist() throws UserDoesNotExistWithEmail;

    String deleteWishlistById(Long id) throws UserDoesNotExistWithEmail, WishlistIsNotExistWithId;
}
