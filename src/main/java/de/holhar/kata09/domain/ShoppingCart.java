package de.holhar.kata09.domain;

import java.util.List;

/**
 * @author hharms
 */
public class ShoppingCart {

    private List<Item> items;

    // Jackson needs it
    public ShoppingCart() {
    }

    public ShoppingCart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "items=" + items +
                '}';
    }
}
