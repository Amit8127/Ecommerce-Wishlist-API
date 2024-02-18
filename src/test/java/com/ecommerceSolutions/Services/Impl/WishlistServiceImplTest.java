package com.ecommerceSolutions.Services.Impl;

import com.ecommerceSolutions.Config.AuthRepository;
import com.ecommerceSolutions.Dtos.RequestDtos.CreateWishlistDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.WishlistResponseDto;
import com.ecommerceSolutions.Exceptions.UserDoesNotExistWithEmail;
import com.ecommerceSolutions.Exceptions.WishlistAlreadyExistWithThisTitle;
import com.ecommerceSolutions.Exceptions.WishlistIsNotExistWithId;
import com.ecommerceSolutions.Models.User;
import com.ecommerceSolutions.Models.Wishlist;
import com.ecommerceSolutions.Repositories.WishlistRepository;
import com.ecommerceSolutions.Transformers.WishlistTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWishlist() throws WishlistAlreadyExistWithThisTitle, UserDoesNotExistWithEmail {
        // Mocking authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Mocking repository responses
        User mockUser = new User();
        mockUser.setId(1L);
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(mockUser));
        when(wishlistRepository.findWishlistTitlesByUserIdAndName(1L, "Test Wishlist")).thenReturn(null);

        // Mocking transformer
        CreateWishlistDto createWishlistDto = new CreateWishlistDto();
        createWishlistDto.setTitle("Test Wishlist");
        Wishlist mockWishlist = WishlistTransformer.wishlistDtoToWishlist(createWishlistDto);
        mockWishlist.setUser(mockUser);

        // Mocking save operation
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(mockWishlist);

        // Test the service method
        WishlistResponseDto createdWishlist = wishlistService.createWishlist(createWishlistDto);

        // Assertions
        assertNotNull(createdWishlist);
        assertEquals(mockUser.getId(), createdWishlist.getUserId());
        assertEquals("Test Wishlist", createdWishlist.getTitle());

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, times(1)).findWishlistTitlesByUserIdAndName(1L, "Test Wishlist");
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testCreateWishlist() throws WishlistAlreadyExistWithThisTitle, UserDoesNotExistWithEmail {
        // Mocking authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");


        // Mocking repository responses
        User mockUser = new User();
        mockUser.setId(1L);
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(mockUser));
        when(wishlistRepository.findWishlistTitlesByUserIdAndName(1L, "Test Wishlist")).thenReturn(null);

        // Mocking transformer
        CreateWishlistDto createWishlistDto = new CreateWishlistDto();
        createWishlistDto.setTitle("Test Wishlist");
        Wishlist mockWishlist = WishlistTransformer.wishlistDtoToWishlist(createWishlistDto);
        mockWishlist.setUser(mockUser);



        // Mocking save operation
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(mockWishlist);


        // Test the service method
        WishlistResponseDto createdWishlist = wishlistService.createWishlist(createWishlistDto);

        // Assertions
        assertNotNull(createdWishlist);
        assertEquals(mockUser.getId(), createdWishlist.getUserId());
        assertEquals("Test Wishlist", createdWishlist.getTitle());

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, times(1)).findWishlistTitlesByUserIdAndName(1L, "Test Wishlist");
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void getAllWishlist() throws UserDoesNotExistWithEmail {
        // Similar setup as above for authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Mocking repository responses
        User mockUser = new User();
        mockUser.setId(1L);
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(mockUser));

        List<Wishlist> mockWishlists = new ArrayList<>();
        when(wishlistRepository.findWishlistsByUserId(1L)).thenReturn(mockWishlists);

        // Test the service method
        List<WishlistResponseDto> userAllWishlists = wishlistService.getAllWishlist();

        // Assertions
        assertNotNull(userAllWishlists);
        assertEquals(mockWishlists, userAllWishlists);

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, times(1)).findWishlistsByUserId(1L);
    }

    @Test
    void deleteWishlistById() throws UserDoesNotExistWithEmail, WishlistIsNotExistWithId {
        // Similar setup as above for authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Mocking repository responses
        User mockUser = new User();
        mockUser.setId(1L);
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(mockUser));

        Wishlist mockWishlist = new Wishlist();
        mockWishlist.setId(1L);
        mockWishlist.setUser(mockUser);
        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(mockWishlist));

        // Test the service method
        String result = wishlistService.deleteWishlistById(1L);

        // Assertions
        assertEquals("Wishlist Deleted Successfully", result);

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, times(1)).findById(1L);
        verify(wishlistRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWishlistById_UserDoesNotExistWithEmail() {
        // Similar setup as above for authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Mocking repository responses
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.empty());

        // Test the service method and expect an exception
        assertThrows(UserDoesNotExistWithEmail.class, () -> wishlistService.deleteWishlistById(1L));

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, never()).findById(anyLong());
        verify(wishlistRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteWishlistById_WishlistIsNotExistWithId() {
        // Similar setup as above for authentication and user data
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser@example.com");

        // Mocking repository responses
        User mockUser = new User();
        mockUser.setId(1L);
        when(authRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(mockUser));
        when(wishlistRepository.findById(1L)).thenReturn(Optional.empty());

        // Test the service method and expect an exception
        assertThrows(WishlistIsNotExistWithId.class, () -> wishlistService.deleteWishlistById(1L));

        // Verify that repository methods were called
        verify(authRepository, times(1)).findByEmail("testuser@example.com");
        verify(wishlistRepository, times(1)).findById(1L);
        verify(wishlistRepository, never()).deleteById(anyLong());
    }
}