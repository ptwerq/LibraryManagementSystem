package model;

import util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoanRecord {
    private final Long id;
    private final Long itemId;
    private final Long userId;
    private final LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private final int dueDays;
    private LoanStatus status;

    public LoanRecord(LibraryItem item, User user, LoanStatus status, int dueDays) {
        this.id = IdGenerator.getIdForClass(LoanRecord.class);
        this.itemId = item.getId();
        this.userId = user.getId();
        this.borrowDate = LocalDateTime.now();
        this.status = status;
        this.dueDays = dueDays;
    }

    // remove borrow method, proper remove method, scheduled overdue check

    public void setReturnDate() {
        this.returnDate = LocalDateTime.now();
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

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoanRecord that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanRecord{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDays=" + dueDays +
                ", status=" + status +
                '}';
    }
}
