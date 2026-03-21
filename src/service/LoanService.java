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

    public void borrowItem(Long userId, Long itemId) {
        LibraryItem item = libraryService.findItemById(itemId);
        User user = findUserById(userId);

        validateBorrowingItem(user, item);

        item.borrow(user);
        LoanRecord loanRecord = new LoanRecord(item, user, LoanStatus.ACTIVE, item.getDefaultLoanPeriod());
        loanRecordMap.put(loanRecord.getRecordId(), loanRecord);
    }

    private void validateBorrowingItem(User user, LibraryItem item) {
        if (item == null) {
            throw new ItemNotFoundException();
        }
        if (!item.isAvailable()) {
            throw new ItemUnavailableException();
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

    public void validateReturningItem(Long userId, Long itemId) { // refactor
        LibraryItem item = libraryService.findItemById(itemId);
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

    private User findUserById(Long userId) {
        return userService.getUserMap().get(userId);
    } // refactor
}
