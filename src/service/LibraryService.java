package service;

import exception.ValidationException;
import model.LibraryItem;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LibraryService {

    private final Map<Long, LibraryItem> libraryItemMap;

    public LibraryService() {
        this.libraryItemMap = new HashMap<>();
    }

    public void addItem(LibraryItem item) {
        if (item == null || libraryItemMap.containsKey(item.getId())) {
            throw new ValidationException();
        }
        libraryItemMap.put(item.getId(), item);
    }

    public LibraryItem findItemById(Long itemId) {
        return libraryItemMap.get(itemId);
    }

    public void deleteItem(Long itemId) {
        libraryItemMap.remove(itemId);
    }

    public void printAllItems() {
        libraryItemMap.values()
                .forEach(System.out::println);
    }

    public void getAllItemsSorted(Comparator<LibraryItem> comparator) {
        libraryItemMap.values().stream()
                .sorted(comparator)
                .forEach(System.out::println);

    }

    public Map<Long, LibraryItem> getCopyOfItemMap() {
        return new HashMap<>(libraryItemMap);
    }
}
