package cz.cvut.fel.ts1.shop;

public class EmptyCartException extends Throwable {
    public EmptyCartException(String message) {
        super(message);
    }
}