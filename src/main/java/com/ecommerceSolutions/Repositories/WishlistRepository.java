package com.ecommerceSolutions.Repositories;

import com.ecommerceSolutions.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query(value = "select * from wishlists where wishlists.user_id = :userId" , nativeQuery = true)
    public List<Wishlist> findWishlistsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT w.title FROM Wishlists w WHERE w.user_id = :userId AND w.title = :wishlistName" , nativeQuery = true)
    public String findWishlistTitlesByUserIdAndName(@Param("userId") Long userId, @Param("wishlistName") String wishlistName);

}
