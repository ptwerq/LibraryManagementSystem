package model;

import exception.ValidationException;

import java.time.LocalDateTime;

public class LoanRecord {
    private final LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LoanStatus status;

    public LoanRecord(LibraryItem item, User user, String inputToSetStatus) {
        Long itemId = item.getId();
        Long userId = user.getId();
        this.borrowDate = LocalDateTime.now();
        // returnDate
        setStatus(inputToSetStatus);
    }

    public void setStatus(String inputToSetStatus) {
        this.status = switch (inputToSetStatus.toUpperCase().trim()) {
            case "ACTIVE" -> LoanStatus.ACTIVE;
            case "RETURNED" -> LoanStatus.RETURNED;
            case "OVERDUE" -> LoanStatus.OVERDUE;
            default -> throw new ValidationException();
        };
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }


}
