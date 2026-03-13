package model;

import exception.ItemUnavailableException;
import exception.ValidationException;
import util.IdGenerator;

import java.time.LocalDate;

public abstract class LibraryItem implements Borrowable {

    private final long id;
    private LoanRecord currentLoan;
    private String title;
    private int year;
    private Genre genre;

    public LibraryItem(String title, int year, Genre genre) {
        setTitle(title);
        setYear(year);
        this.genre = genre;
        this.id = IdGenerator.getIdForClass(LibraryItem.class);
    }

    @Override
    public void borrow(User user) {
        if (isAvailable()) {
            currentLoan = new LoanRecord(this, user, "active");
        }
        else {
            throw new ItemUnavailableException();
        }

    }

    @Override
    public void returnItem() {
        if (!isAvailable()) {
            currentLoan.setStatus("returned");
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Genre getItemType() {
        return genre;
    }

    @Override
    public String toString() {
        return "LibraryItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                '}';
    }
}
