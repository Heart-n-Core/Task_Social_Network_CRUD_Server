package org.example;

import java.util.ArrayList;

/*
Main data unit class - User
includes constructor, getters and setters
* */


public class User {
    private String nickname;
    private String name;
    private String age;
    private ArrayList<String> friends;

    public User(String nickname, String name, String age, ArrayList<String> friends) {
        super();
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.friends = friends;
    }

    @Override
    public String toString() {
        String response = "";
        response += "Nickname: " + nickname + "\n";
        if (name != null) {
            response += "Name: " + name + "\n";
        }
        if (age != null) {
            response += "Age: " + age + "\n";
        }
        if (friends != null) {
            response += "Friends: " + friends + "\n";
        }
        response +="\n";
        return response;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

}
