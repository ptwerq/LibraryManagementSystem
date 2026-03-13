package model;

import exception.ValidationException;

import java.time.LocalDateTime;

public class LoanRecord {
    private final LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LoanStatus status;

    public LoanRecord(LibraryItem item, User user, String inputToSetStatus) {
        Long itemId = item.getId(); // TODO: should be class fields
        Long userId = user.getId();
        this.borrowDate = LocalDateTime.now();
        // returnDate // TODO: shouldn't be set here, should be a class field set when returned
        setStatus(inputToSetStatus);
    }

    // return(ReturnDate)
    // markAsOverdue()

    public void setStatus(String inputToSetStatus) { // remove
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
    } // remove


}
