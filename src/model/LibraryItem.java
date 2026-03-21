package model;

import exception.ItemUnavailableException;
import exception.ValidationException;
import util.IdGenerator;

import java.time.LocalDate;
import java.util.Comparator;

public abstract class LibraryItem implements Borrowable {
    public static final Comparator<LibraryItem> BY_TITLE = Comparator.comparing(LibraryItem::getTitle);
    public static final Comparator<LibraryItem> BY_ITEM_ID = Comparator.comparing(LibraryItem::getItemId);
    public static final Comparator<LibraryItem> BY_YEAR = Comparator.comparing(LibraryItem::getYear);
    public static final Comparator<LibraryItem> BY_GENRE = Comparator.comparing(LibraryItem::getGenre);

    public abstract int getDefaultLoanPeriod();

    private final Long itemId;
    private LoanRecord currentLoan;
    private String title;
    private int year;
    private Genre genre;

    public LibraryItem(String title, int year, Genre genre) {
        setTitle(title);
        setYear(year);
        this.genre = genre;
        this.itemId = IdGenerator.getIdForClass(LibraryItem.class);
    }

    @Override
    public void borrow(User user) {
        if (isAvailable()) {
            currentLoan.setStatus(LoanStatus.ACTIVE);
        }
        else {
            throw new ItemUnavailableException();
        }

    }

    @Override
    public void returnItem() {
        if (!isAvailable()) {
            currentLoan.setStatus(LoanStatus.RETURNED);
        }
    }

    @Override
    public boolean isAvailable() {
        return this.currentLoan == null || currentLoan.getStatus() == LoanStatus.RETURNED;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new ValidationException();
        }
        this.title = title;
    }

    public void setYear(int year) {
        if (year <= 0 || year > LocalDate.now().getYear()){
            throw new ValidationException();
        }
        this.year = year;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "LibraryItem{" +
                "id=" + itemId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                '}';
    }
}
