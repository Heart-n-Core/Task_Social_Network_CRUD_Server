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
    public User editUser(User inputUser) {
        try {
            if (inputUser.getNickname() == null) {
                throw new RuntimeException("User nickname cannot be null");

        }
            User outUser = users.get(inputUser.getNickname());

            if(outUser == null) {
                throw new RuntimeException("User not found");
            }
            if(inputUser.getName() != null) {
                outUser.setName(inputUser.getName());
            }
            if (inputUser.getAge() != null) {
                outUser.setAge(inputUser.getAge());
            }
            if (inputUser.getFriends() != null) {
                outUser.setFriends(inputUser.getFriends());
            }
            users.put(outUser.getNickname(), outUser); //Save
            return outUser;
        } catch (RuntimeException e) {
            throw new RuntimeException("Editing user failed", e);
        }
    }
}
