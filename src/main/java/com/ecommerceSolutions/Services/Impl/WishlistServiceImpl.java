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
import com.ecommerceSolutions.Services.WishlistService;
import com.ecommerceSolutions.Transformers.WishlistTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AuthRepository authRepository;

    /**
     * Creates a new wishlist for the authenticated user.
     *
     * @param createWishlistDto The DTO containing wishlist creation details.
     * @return The created wishlist.
     * @throws WishlistAlreadyExistWithThisTitle If a wishlist with the same title already exists for the user.
     * @throws UserDoesNotExistWithEmail         If the authenticated user does not exist in the system.
     */

    @Override
    public WishlistResponseDto createWishlist(CreateWishlistDto createWishlistDto) throws WishlistAlreadyExistWithThisTitle, UserDoesNotExistWithEmail {

        // Retrieve the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        // Find the user by email
        Optional<User> userOpt = authRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            throw new UserDoesNotExistWithEmail(email);
        }

        User user = userOpt.get();

        // Check if a wishlist with the same title already exists for the user
        String isWishlistPresent = wishlistRepository.findWishlistTitlesByUserIdAndName(user.getId(), createWishlistDto.getTitle());
        if(isWishlistPresent != null) {
            throw new WishlistAlreadyExistWithThisTitle(createWishlistDto.getTitle());
        }

        // Transform DTO to Wishlist entity and save to the repository
        Wishlist wishlist = WishlistTransformer.wishlistDtoToWishlist(createWishlistDto);
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);

        return WishlistTransformer.wishlistToWishlistDto(wishlist);
    }

    /**
     * Retrieves all wishlists for the authenticated user.
     *
     * @return List of wishlists for the user.
     * @throws UserDoesNotExistWithEmail If the authenticated user does not exist in the system.
     */
    @Override
    public List<WishlistResponseDto> getAllWishlist() throws UserDoesNotExistWithEmail {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        // Retrieve user details from the repository using the user's email
        Optional<User> userOpt = authRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            throw new UserDoesNotExistWithEmail(email);
        }

        // Retrieve all wishlists for the user
        User user = userOpt.get();
        List<Wishlist> userAllWishlists = wishlistRepository.findWishlistsByUserId(user.getId());

        List<WishlistResponseDto> responseDtoList = new ArrayList<>();

        for(Wishlist wishlist : userAllWishlists ) {
            responseDtoList.add(WishlistTransformer.wishlistToWishlistDto(wishlist));
        }

        return responseDtoList;
    }

    /**
     * Delete wishlist by id of the authenticated user.
     *
     * @return The success message.
     * @throws WishlistIsNotExistWithId If wishlist is not exist in the database.
     * @throws UserDoesNotExistWithEmail If the authenticated user does not exist in the system.
     */
    @Override
    public String deleteWishlistById(Long id) throws UserDoesNotExistWithEmail, WishlistIsNotExistWithId {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        // Retrieve user details from the repository using the user's email
        Optional<User> userOpt = authRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            throw new UserDoesNotExistWithEmail(email);
        }

        // Retrieve the wishlist by its ID
        Optional<Wishlist> wishlistOpt = wishlistRepository.findById(id);
        if(wishlistOpt.isEmpty()) {
            throw new WishlistIsNotExistWithId(id);
        }

        // Check if the authenticated user owns the wishlist before deleting
        User user = userOpt.get();
        if (wishlistOpt.get().getUser().equals(user)) {
            wishlistRepository.deleteById(id);
            return "Wishlist Deleted Successfully";
        } else {
            return "Access denied";
        }
    }
}
