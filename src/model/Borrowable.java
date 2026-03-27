package model;

public interface Borrowable {
    void borrow();
    void returnItem();
    boolean isAvailable();
}
