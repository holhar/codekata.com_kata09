package de.holhar.kata09.service;

import de.holhar.kata09.domain.ShoppingCart;

/**
 * @author hharms
 *
 * This service provides functionality to calculate the total of a given shoppingcart.
 */
public interface CheckoutService {

    /**
     *
     * @param shoppingCart represents the goods to be purchased
     * @return total the end price of all given goods in the shopping cart
     */
    double checkout(ShoppingCart shoppingCart);
}
