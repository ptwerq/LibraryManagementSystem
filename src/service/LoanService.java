package service;

import exception.ItemNotFoundException;
import exception.ItemUnavailableException;
import exception.UserNotFoundException;
import model.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LoanService {
    private final Map<Long, LoanRecord> loanRecordMap;
    private final LibraryService libraryService;
    private final UserService userService;

    public LoanService(LibraryService libraryService, UserService userService) {
        this.loanRecordMap = new HashMap<>();
        this.libraryService = libraryService;
        this.userService = userService;
    }

    public void validateBorrowingItem(Long userId, Long itemId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (!item.isAvailable()) {
            throw new ItemUnavailableException();
        }
        User user = findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        long count = loanRecordMap.values().stream()
                .filter(r -> r.getUserId().equals(userId))
                .filter(r -> !r.getStatus().equals(LoanStatus.RETURNED))
                .count();
        if (count == 3) {
            throw new ItemUnavailableException();
        }
        item.borrow(user);
        LoanRecord loanRecord = new LoanRecord(item, user, LoanStatus.ACTIVE, item.getDefaultLoanPeriod());
        loanRecordMap.put(loanRecord.getRecordId(), loanRecord);
    }

    public void validateReturningItem(Long userId, Long itemId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (item.isAvailable()) {
            throw new ItemUnavailableException();
        }
        User user = findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        LoanRecord loanRecord = loanRecordMap.values().stream()
                .filter(r -> r.getUserId().equals(userId))
                .filter(r -> r.getItemId().equals(itemId))
                .filter(r -> r.getStatus().equals(LoanStatus.ACTIVE))
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
        loanRecord.setReturnDate();
        item.returnItem();
    }

    private LibraryItem findItemById(Long itemId) {
        return libraryService.getLibraryItemMap().get(itemId);
    }

    private User findUserById(Long userId) {
        return userService.getUserMap().get(userId);
    }
}
