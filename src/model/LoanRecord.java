package model;

import util.IdGenerator;

import java.time.LocalDateTime;

public class LoanRecord {
    private final LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private final int dueDays;
    private LoanStatus status;
    private Long recordId;
    private Long itemId;
    private Long userId;

    public LoanRecord(LibraryItem item, User user, LoanStatus status, int dueDays) {
        this.itemId = item.getId();
        this.userId = user.getId();
        this.borrowDate = LocalDateTime.now();
        this.status = status;
        this.dueDays = dueDays;
        this.recordId = IdGenerator.getIdForClass(LoanRecord.class);
    }

    public void setReturnDate() {
        this.returnDate = LocalDateTime.now();
        if (isOverdue()) {
            setStatus(LoanStatus.OVERDUE);
        }
        else {
            setStatus(LoanStatus.RETURNED);
        }
    }

    private boolean isOverdue() {
        return LocalDateTime.now().isAfter(LocalDateTime.now().plusDays(dueDays));
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
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

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getDueDays() {
        return dueDays;
    }

    public Long getRecordId() {
        return recordId;
    }
}
