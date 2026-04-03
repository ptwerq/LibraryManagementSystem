package service;

import exception.ItemNotFoundException;
import exception.ItemUnavailableException;
import exception.UserNotFoundException;
import model.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class LoanService {
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    private final Map<Long, LoanRecord> loanRecordMap;
    private final LibraryService libraryService;
    private final UserService userService;

    public LoanService(LibraryService libraryService, UserService userService) {
        this.loanRecordMap = new HashMap<>();
        this.libraryService = libraryService;
        this.userService = userService;
    }

    public void borrowItem(Long userId, Long itemId) {
        LibraryItem item = libraryService.findItemById(itemId);
        User user = userService.findUserById(userId);

        validateBorrowingItem(user, item);

        LoanRecord loanRecord = new LoanRecord(item, user, LoanStatus.ACTIVE, item.getDefaultLoanPeriod());
        loanRecordMap.put(loanRecord.getId(), loanRecord);
    }

    private void validateBorrowingItem(User user, LibraryItem item) {
        if (areNotNull(user, item)) {
            long count = loanRecordMap.values().stream()
                    .filter(r -> r.getUserId().equals(user.getId()))
                    .filter(r -> !r.getStatus().equals(LoanStatus.RETURNED))
                    .count();
            if (count == 3) {
                throw new ItemUnavailableException();
            }
        }
        else throw new RuntimeException();
    }

    public void returnItem(Long userId, Long itemId) {
        LibraryItem item = libraryService.findItemById(itemId);
        User user = userService.findUserById(userId);

        if (areNotNull(user, item)) {
            LoanRecord loanRecord = loanRecordMap.values().stream()
                    .filter(r -> r.getUserId().equals(user.getId()))
                    .filter(r -> r.getItemId().equals(item.getId()))
                    .filter(r -> r.getStatus().equals(LoanStatus.ACTIVE))
                    .findFirst()
                    .orElseThrow(ItemNotFoundException::new);
            loanRecord.setReturnDate();
            loanRecord.setStatus(LoanStatus.RETURNED);
        }
        else throw new RuntimeException();
    }

    public void startOverdueChecker() {
        executorService.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            for (LoanRecord loan : loanRecordMap.values()) {
                if (loan.getStatus().equals(LoanStatus.ACTIVE) && now.isAfter(now.plusDays(loan.getDueDays()))) {
                    loan.setStatus(LoanStatus.OVERDUE);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private boolean areNotNull(User user, LibraryItem item) {
        return item != null || user != null;
    }

    public void filterLoans(Predicate<LoanRecord> p) {
        loanRecordMap.values().stream()
                .filter(p)
                .forEach(System.out::println);
    }

    public Map<Long, LoanRecord> getCopyOfLoansMap() {
        return new HashMap<>(loanRecordMap);
    }
}
