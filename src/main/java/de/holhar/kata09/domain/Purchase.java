package de.holhar.kata09.domain;

import java.util.Map;

/**
 * @author hharms
 */
public class Purchase {

    private Map<Item, Integer> itemsMap;
    private double total;

    public Purchase(Map<Item, Integer> itemsMap, double total) {
        this.itemsMap = itemsMap;
        this.total = total;
    }

    public Map<Item, Integer> getItemsMap() {
        return itemsMap;
    }

    public double getTotal() {
        return total;
}

    private void setTotal(double total) {
        this.total = total;
}

    public void addToTotal(double amount) {
        setTotal(total + amount);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "itemsMap=" + itemsMap +
                ", total=" + total +
                '}';
    }
}
