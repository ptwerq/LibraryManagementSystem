import model.Book;
import model.Genre;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());
        String author = scanner.nextLine();
        int pages = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, year, genre, author, pages);
        Book book2 = new Book(title, year, genre, author, pages);
        System.out.println(book.getId());
        System.out.println(book2.getId());
    }
}


// Hello\n
// 2020\n
// Horror\n