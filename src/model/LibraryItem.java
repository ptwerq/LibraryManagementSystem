package model;

import exception.ValidationException;
import util.IdGenerator;

import java.time.LocalDate;

public abstract class LibraryItem {

    private final Long id;
    private String title;
    private int year;
    private Genre genre;

    public LibraryItem(String title, int year, Genre genre) {
        setTitle(title);
        setYear(year);
        this.genre = genre;
        this.id = IdGenerator.getIdForClass(LibraryItem.class);
    }

    public abstract int getDefaultLoanPeriod();

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

    public Genre getGenre() {
        return genre;
    }

}
