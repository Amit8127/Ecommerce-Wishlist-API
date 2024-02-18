package com.ecommerceSolutions.Controllers;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateWishlistDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.WishlistResponseDto;
import com.ecommerceSolutions.Exceptions.UserDoesNotExistWithEmail;
import com.ecommerceSolutions.Exceptions.WishlistIsNotExistWithId;
import com.ecommerceSolutions.Models.Wishlist;
import com.ecommerceSolutions.Services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWishlist_Success() throws Exception {
        // Arrange
        CreateWishlistDto createWishlistDto = new CreateWishlistDto("New List");
        WishlistResponseDto expectedResult = new WishlistResponseDto("New List");

        when(wishlistService.createWishlist(any(CreateWishlistDto.class))).thenReturn(expectedResult);

        // Act
        ResponseEntity<?> responseEntity = wishlistController.createWishlist(createWishlistDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(wishlistService, times(1)).createWishlist(createWishlistDto);
    }

    @Test
    void createWishlist_Exception() throws Exception {
        // Arrange
        CreateWishlistDto createWishlistDto = new CreateWishlistDto("New List");
        String errorMessage = "Error message";

        when(wishlistService.createWishlist(any(CreateWishlistDto.class))).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> responseEntity = wishlistController.createWishlist(createWishlistDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(wishlistService, times(1)).createWishlist(createWishlistDto);
    }

    @Test
    void getWishList_Success() throws UserDoesNotExistWithEmail {
        // Arrange
        List<WishlistResponseDto> expectedResult = Arrays.asList(new WishlistResponseDto("New List"));

        when(wishlistService.getAllWishlist()).thenReturn(expectedResult);

        // Act
        ResponseEntity<?> responseEntity = wishlistController.getWishList();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(wishlistService, times(1)).getAllWishlist();
    }

    @Test
    void getWishList_Exception() throws UserDoesNotExistWithEmail {
        // Arrange
        String errorMessage = "Error message";

        when(wishlistService.getAllWishlist()).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> responseEntity = wishlistController.getWishList();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(wishlistService, times(1)).getAllWishlist();
    }

    @Test
    void deleteWishlistById_Success() throws UserDoesNotExistWithEmail, WishlistIsNotExistWithId {
        // Arrange
        Long wishlistId = 1L;
        String expectedResult = "Wishlist deleted successfully";

        when(wishlistService.deleteWishlistById(wishlistId)).thenReturn(expectedResult);

        // Act
        ResponseEntity<?> responseEntity = wishlistController.deleteWishlistById(wishlistId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(wishlistService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    void deleteWishlistById_Exception() throws UserDoesNotExistWithEmail, WishlistIsNotExistWithId {
        // Arrange
        Long wishlistId = 1L;
        String errorMessage = "Error message";

        when(wishlistService.deleteWishlistById(wishlistId)).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> responseEntity = wishlistController.deleteWishlistById(wishlistId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(wishlistService, times(1)).deleteWishlistById(wishlistId);
    }
}