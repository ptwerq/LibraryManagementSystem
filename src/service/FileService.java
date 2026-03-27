package service;

import java.io.FileWriter;
import java.io.IOException;

public class FileService {
    private final FileWriter userWriter;
    private final FileWriter itemsWriter;
    private final FileWriter loanWriter;
    private LoanService loanService;
    private LibraryService libraryService;
    private UserService userService;

    public FileService(LoanService loanService, LibraryService libraryService, UserService userService) throws IOException {
        this.userWriter = new FileWriter("user.txt", true);
        this.itemsWriter = new FileWriter("items.txt", true);
        this.loanWriter = new FileWriter("loans.txt", true);
        this.loanService = loanService;
        this.userService = userService;
        this.libraryService = libraryService;
    }

    public void saveAll() {
        writeUserToFile();
        writeLoanToFile();
        writeItemToFile();
    }

    private void writeUserToFile(){
        try {
            userWriter.write(userService.toString());
            userWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLoanToFile(){
        try {
            loanWriter.write(loanService.toString());
            loanWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeItemToFile() {
        try {
            itemsWriter.write(libraryService.toString());
            itemsWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
