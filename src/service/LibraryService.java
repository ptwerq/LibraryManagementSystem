package service;

import exception.ValidationException;
import model.LibraryItem;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryService {

    private final Map<Long, LibraryItem> libraryItemMap;

    public LibraryService() {
        this.libraryItemMap = new HashMap<>();
    }

    public void addItem(LibraryItem item) {
        if (item == null || libraryItemMap.containsKey(item.getItemId())) {
            throw new ValidationException();
        }
        libraryItemMap.put(item.getItemId(), item);
    }

    public void deleteItem(Long itemId) {
        if (itemId == null || !libraryItemMap.containsKey(itemId)) {
            throw new ValidationException();
        }
        libraryItemMap.remove(itemId);
    }

    public void printAllItems() {
        libraryItemMap.values()
                .forEach(System.out::println);
    }

    public List<LibraryItem> sortItemsByTitle() {
        return getAllItemsSorted(LibraryItem.BY_TITLE);
    }

    public List<LibraryItem> sortItemsByItemId() {
        return getAllItemsSorted(LibraryItem.BY_ITEM_ID);
    }

    public List<LibraryItem> sortItemsByYear() {
        return getAllItemsSorted(LibraryItem.BY_YEAR);
    }

    public List<LibraryItem> sortItemsByGenre() {
        return getAllItemsSorted(LibraryItem.BY_GENRE);
    }

    private List<LibraryItem> getAllItemsSorted(Comparator<LibraryItem> comparator) {
        return libraryItemMap.values().stream()
                .sorted(comparator)
                .toList();
    }

    public Map<Long, LibraryItem> getLibraryItemMap() {
        return libraryItemMap;
    }
}
