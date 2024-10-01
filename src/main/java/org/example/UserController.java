package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import com.google.gson.Gson;

/*
    Loading Users from users.json file into Hashmap
    CRUD actions for user's Hashmap
 */

public class UserController {
    private HashMap<String, User> users;
    public UserController() {
        users = new HashMap<>();
    }

    public void readUserData() {
        HashMap<String, User> usersLoad = new HashMap<>();
        System.out.println("Reading users.json");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.json"));
            String file = "";
            String line = reader.readLine();
            while (line != null) {
                file += line;
                line = reader.readLine();
            }
            file = file.substring(1, file.length() - 2);
            String[] parts = file.split("},");
            for (String part : parts) {
                part += "}";
                System.out.println(part);
                User user = new Gson().fromJson(part, User.class);
                System.out.println(user.toString());
                usersLoad.put(user.getNickname(), user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("JSON File not found");
            System.out.println("JSON File not found");
            System.out.println("JSON File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        users = usersLoad;
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
