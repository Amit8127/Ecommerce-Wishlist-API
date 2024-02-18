package com.ecommerceSolutions.Controllers;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateWishlistDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.WishlistResponseDto;
import com.ecommerceSolutions.Models.Wishlist;
import com.ecommerceSolutions.Services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing Wishlist operations through API endpoints.
 */
@RestController
@RequestMapping("/api")
public class WishlistController {

    // Autowired instance of WishlistService to handle business logic
    @Autowired
    private final WishlistService wishlistService;

    // Constructor for Dependency Injection of WishlistService
    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * POST endpoint to create a new Wishlist.
     *
     * @param createWishlistDto Request data for creating a new Wishlist.
     * @return ResponseEntity with Wishlist object if successful, or error message if failed.
     */
    @PostMapping("/wishlists")
    public ResponseEntity<?> createWishlist(@RequestBody CreateWishlistDto createWishlistDto) {
        try{
            WishlistResponseDto result = wishlistService.createWishlist(createWishlistDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET endpoint to retrieve all Wishlists.
     *
     * @return ResponseEntity with List of Wishlists if successful, or error message if failed.
     */
    @GetMapping("/wishlists")
    public ResponseEntity<?> getWishList() {
        try{
            List<WishlistResponseDto> result = wishlistService.getAllWishlist();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE endpoint to delete a Wishlist by its ID.
     *
     * @param id ID of the Wishlist to be deleted.
     * @return ResponseEntity with success message if successful, or error message if failed.
     */
    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<?> deleteWishlistById(@PathVariable Long id) {
        try{
            String result = wishlistService.deleteWishlistById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
