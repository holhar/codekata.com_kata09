package de.holhar.kata09.dto;

/**
 * @author hharms
 */
public class CheckoutResponse {

    private boolean success;
    private String message;

    private double total;

    public CheckoutResponse(boolean success, String message, double total) {
        this.success = success;
        this.message = message;
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public double getTotal() {
        return total;
    }
}
