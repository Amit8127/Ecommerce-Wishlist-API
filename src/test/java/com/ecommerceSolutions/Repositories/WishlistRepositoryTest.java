package com.ecommerceSolutions.Repositories;

import com.ecommerceSolutions.Config.AuthRepository;
import com.ecommerceSolutions.Models.User;
import com.ecommerceSolutions.Models.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishlistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AuthRepository authRepository;

    @Test
    void findWishlistsByUserId() {

        User user = new User("user", "testing@gmail.com", "password");

        Wishlist wishlist1 = new Wishlist(user, "Wishlist1");
        Wishlist wishlist2 = new Wishlist(user, "Wishlist2");
        Wishlist wishlist3 = new Wishlist(user, "Wishlist3");


        entityManager.persist(user);
        entityManager.persist(wishlist1);
        entityManager.persist(wishlist2);
        entityManager.persist(wishlist3);
        entityManager.flush();

        // When
        List<Wishlist> result = wishlistRepository.findWishlistsByUserId(user.getId());

        // Then
        assertEquals(3, result.size());
        assertTrue(result.contains(wishlist1));
        assertTrue(result.contains(wishlist2));
        assertTrue(result.contains(wishlist3));
    }

    @Test
    void findWishlistTitlesByUserIdAndName() {
        // Given
        User user = new User("user", "testing1@gmail.com", "password");
        String wishlistName = "WishlistName";
        Wishlist wishlist = new Wishlist(wishlistName);
        wishlist.setUser(user);

        entityManager.persist(user);
        entityManager.persist(wishlist);
        entityManager.flush();

        // When
        String result = wishlistRepository.findWishlistTitlesByUserIdAndName(user.getId(), wishlistName);

        // Then
        assertEquals(wishlistName, result);

        // When
        String wishlistTitle = wishlistRepository.findWishlistTitlesByUserIdAndName(user.getId(), wishlistName);

        // Then
        assertNotNull(wishlistTitle);
        // Add additional assertions based on your requirements
    }
}