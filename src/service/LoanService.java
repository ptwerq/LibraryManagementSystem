package service;

import exception.ItemNotFoundException;
import exception.ItemUnavailableException;
import exception.UserNotFoundException;
import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class LoanService {
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

        LoanRecord loanRecord = new LoanRecord(item, user, LoanStatus.ACTIVE, item.getDefaultLoanPeriod()); // TODO: fix
        loanRecordMap.put(loanRecord.getId(), loanRecord);
    }

    private void validateBorrowingItem(User user, LibraryItem item) {
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        long count = loanRecordMap.values().stream()
                .filter(r -> r.getUserId().equals(user.getId()))
                .filter(r -> !r.getStatus().equals(LoanStatus.RETURNED))
                .count();
        if (count == 3) {
            throw new ItemUnavailableException();
        }
    }

    public void returnItem(Long userId, Long itemId) {
        LibraryItem item = libraryService.findItemById(itemId);
        User user = userService.findUserById(userId);
        validateReturningItem(user, item);
        LoanRecord loanRecord = loanRecordMap.values().stream()
                .filter(r -> r.getUserId().equals(user.getId()))
                .filter(r -> r.getItemId().equals(item.getId()))
                .filter(r -> r.getStatus().equals(LoanStatus.ACTIVE))
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
        loanRecord.setReturnDate();
        loanRecord.setStatusToReturnItem();
    }

    private void validateReturningItem(User user, LibraryItem item) {
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    public void filterLoans(Predicate<LoanRecord> p) {
        loanRecordMap.values().stream()
                .filter(p)
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Loans = "
                + loanRecordMap +
                '}';
    }
}
