package org.example;

import java.util.Collection;
import java.util.HashMap;

public class UserController {
    private HashMap<String, User> users;
    public UserController() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getNickname(), user);
    }
    public boolean checkNicknameAvailable(String nickname) {
        return !users.containsKey(nickname);
    }
    public User getUser(String nickname) {
        return users.get(nickname);
    }
    public Collection<User> getUsers() {
        return users.values();
    }
    public void removeUser(String nickname) {
        users.remove(nickname);
    }
}
