package com.ecommerceSolutions.Exceptions;

public class WishlistIsNotExistWithId extends Exception{
    public WishlistIsNotExistWithId(Long id) {
        super("Wishlist is not Exist with Id : " + id);
    }
}
