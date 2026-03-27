package service;

import exception.ValidationException;
import model.User;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final Map<Long, User> userMap;

    public UserService() {
        this.userMap = new HashMap<>();
    }

    public void registerUser(User user) {
        if (user == null || userMap.containsKey(user.getId())) {
            throw new ValidationException();
        }
        userMap.put(user.getId(), user);
    }

    public void printAllUsers() {
        userMap.values()
                .forEach(System.out::println);
    }

    public User findUserById(Long userId) {
        return userMap.get(userId);
    }

    private List<User> getAllUsersSorted(Comparator<User> comparator) {
        return userMap.values().stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public String toString() {
        return "Users = " +
                "userMap=" + userMap +
                '}';
    }
}
