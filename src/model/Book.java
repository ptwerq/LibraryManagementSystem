package model;

import exception.ValidationException;

public class Book extends LibraryItem {
    public static long IsbnCounter;

    private String author;
    private int pages;
    private final long isbn;

    public Book(String title, int year, Genre genre, String author, int pages) {
        super(title, year, genre);
        setAuthor(author);
        setPages(pages);
        this.isbn = IsbnCounter++;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new ValidationException();
        }
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        if (pages <= 0) {
            throw new ValidationException();
        }
        this.pages = pages;
    }

    public long getIsbn() {
        return isbn;
    }

}
