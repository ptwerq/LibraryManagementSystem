package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import exception.EmptyFileException;
import model.LibraryItem;
import model.LoanRecord;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class FileService {
    private final Gson gson;
    private final LoanService loanService;
    private final LibraryService libraryService;
    private final UserService userService;

    public FileService(LoanService loanService, LibraryService libraryService, UserService userService){
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.loanService = loanService;
        this.userService = userService;
        this.libraryService = libraryService;
    }
    
    public void saveAllData() {
        writeToFile("users.json", userService.getCopyOfUsersMap());
        writeToFile("loans.json", loanService.getCopyOfLoansMap());
        writeToFile("items.json", libraryService.getCopyOfItemMap());
        System.out.println("Your data has been saved");
    }

    public void loadAllData() {
        Type userType = new TypeToken<Map<Long, User>>(){}.getType();
        Map<Long, User> users = readFromFile("users.json", userType);
        if (users == null) {
            throw new EmptyFileException();
        }

        Type loanType = new TypeToken<Map<Long, LoanRecord>>(){}.getType();
        Map<Long, LoanRecord> loans = readFromFile("loans.json", loanType);
        if (loans == null) {
            throw new EmptyFileException();
        }

        Type itemType = new TypeToken<Map<Long, LibraryItem>>(){}.getType();
        Map<Long, LibraryItem> items = readFromFile("items.json", itemType);
        if (items == null) {
            throw new EmptyFileException();
        }

    }

    private void writeToFile(String fileName, Object data) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(data, fileWriter);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private <T> T readFromFile(String fileName, Type type) {
        try (FileReader fileReader = new FileReader(fileName)) {
            return gson.fromJson(fileReader, type);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
