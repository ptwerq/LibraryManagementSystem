package model;

public interface Borrowable {
    void borrow(User user);
    void returnItem();
    boolean isAvailable();
}
