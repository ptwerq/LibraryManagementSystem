package model;

import exception.ItemUnavailableException;
import exception.ValidationException;
import java.time.LocalDate;

public abstract class LibraryItem implements Borrowable {
    private static Long ItemIdCounter;

    private LoanRecord currentLoan;
    private final Long id;
    private String title;
    private int year;
    private Genre genre;

    public LibraryItem(String title, int year, String inputToChooseGenre) {
        setTitle(title);
        setYear(year);
        setGenre(inputToChooseGenre);
        this.id = ItemIdCounter++;
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

    public void setGenre(String inputToChooseGenre) {
      this.genre = switch(inputToChooseGenre.toUpperCase().trim()) {
          case "FANTASY" -> Genre.FANTASY;
          case "HISTORY" -> Genre.HISTORY;
          case "SCIENCE" -> Genre.SCIENCE;
          case "PROGRAMMING" -> Genre.PROGRAMMING;
          case "FICTION" -> Genre.FICTION;
          case "OTHER" -> Genre.OTHER;
          default -> throw new ValidationException();
      };
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
