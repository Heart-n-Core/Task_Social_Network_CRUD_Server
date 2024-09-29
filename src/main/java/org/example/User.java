package org.example;

import java.util.ArrayList;

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
        String response = "Nickname: "+nickname + " Name:" + name + " Age:" + age + " Friends:" + friends.toString();
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
