package com.ecommerceSolutions.Exceptions;

public class WishlistAlreadyExistWithThisTitle extends Exception{
    public WishlistAlreadyExistWithThisTitle(String title) {
        super("Wishlist Already Exist With This Title : " + title);
    }
}
