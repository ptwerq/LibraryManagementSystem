package model;

import exception.ValidationException;
import util.IdGenerator;

public class Magazine extends LibraryItem {
    private final long issueNumber;
    private String publisher;

    public Magazine(String title, int year, Genre genre, String publisher) {
        super(title, year, genre);
        setPublisher(publisher);
        this.issueNumber = IdGenerator.getIdForClass(Magazine.class);
    }

    @Override
    public int getDefaultLoanPeriod() {
        return 7;
    }

    public void setPublisher(String publisher) {
        if (publisher == null || publisher.isBlank()) {
            throw new ValidationException();
        }
        this.publisher = publisher;
    }
    public String getPublisher() {
        return publisher;
    }

    public long getIssueNumber() {
        return issueNumber;
    }
}
