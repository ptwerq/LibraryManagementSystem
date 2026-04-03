import exception.ValidationException;
import model.*;
import service.FileService;
import service.LibraryService;
import service.LoanService;
import service.UserService;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService libraryService = new LibraryService();
        UserService userService = new UserService();
        LoanService loanService = new LoanService(libraryService, userService);
        FileService fileService = new FileService(loanService, libraryService, userService);
        loanService.startOverdueChecker();

        boolean isRunnning = true;

        while(isRunnning) {
            System.out.println("\n====== Library Management System ======");
            System.out.println("1. Add book");
            System.out.println("2. Add magazine");
            System.out.println("3. Register user");
            System.out.println("4. Show all items");
            System.out.println("5. Show available items");
            System.out.println("6. Borrow item");
            System.out.println("7. Return item");
            System.out.println("8. Search by title");
            System.out.println("9. Search by genre");
            System.out.println("10. Show users");
            System.out.println("11. Show active loans");
            System.out.println("12. Save data");
            System.out.println("13. Load data");
            System.out.println("14. Exit\n");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the following data: " +
                            "title, " +
                            "year, " +
                            "genre, " +
                            "author, " +
                            "amount of pages");
                    System.out.println("Available genres:" + Arrays.toString(Genre.values()) + "\n");

                    String title = scanner.nextLine();
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
                    String author = scanner.nextLine();
                    int pages = scanner.nextInt();
                    scanner.nextLine();

                    Book book = new Book(title, year, genre, author, pages);
                    libraryService.addItem(book);
                    System.out.println("The book has been added: " + book);
                }
                case 2 -> {
                    System.out.println("Enter the following data: " +
                            "title, " +
                            "year, " +
                            "genre, " +
                            "publisher" + "\n");

                    String title = scanner.nextLine();
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
                    String publisher = scanner.nextLine();

                    Magazine magazine = new Magazine(title, year, genre, publisher);
                    libraryService.addItem(magazine);
                    System.out.println("The magazine has been added: " + magazine);
                }
                case 3 -> {
                    System.out.println("Enter your " +
                            "name, " +
                            "email, " +
                            "role");
                    System.out.println("Available roles: " + Arrays.toString(UserRole.values()) + "\n");

                    String name = scanner.nextLine();
                    String email = scanner.nextLine();
                    UserRole role = UserRole.valueOf(scanner.nextLine().toUpperCase());

                    User user = new User(name, email, role);
                    userService.registerUser(user);
                    System.out.println("The user has been added: " + user);
                }
                case 4 -> {
                    libraryService.printAllItems();
                }
                case 5 -> {
                    loanService.filterLoans(p -> p.getStatus().equals(LoanStatus.RETURNED));
                }
                case 6 -> {
                    System.out.println("Enter the id of user");
                    Long userId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Enter the id of item");
                    Long itemId = scanner.nextLong();
                    scanner.nextLine();
                    loanService.borrowItem(userId, itemId);
                    System.out.println("Success!");
                }
                case 7 -> {
                    System.out.println("Enter the id of user");
                    Long userId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Enter the id of item");
                    Long itemId = scanner.nextLong();
                    scanner.nextLine();
                    loanService.returnItem(userId, itemId);
                    System.out.println("Success!");
                }
                case 8 -> {
                    libraryService.getAllItemsSorted(Comparator.comparing(LibraryItem::getTitle));
                }
                case 9 -> {
                    libraryService.getAllItemsSorted(Comparator.comparing(LibraryItem::getGenre));
                }
                case 10 -> {
                    userService.printAllUsers();
                }
                case 11 -> {
                    loanService.filterLoans(p -> p.getStatus().equals(LoanStatus.ACTIVE));
                }
                case 12 -> {
                    fileService.saveAllData();
                }
                case 13 -> {
                    fileService.loadAllData();
                }
                case 14 -> {
                    isRunnning = false;
                }
                default -> throw new ValidationException();
            }
        }

    }
}