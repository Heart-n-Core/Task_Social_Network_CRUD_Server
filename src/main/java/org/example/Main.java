package org.example;
import com.google.gson.Gson;
import spark.Spark;

import java.util.ArrayList;

import static spark.Spark.before;

/*

.___  ___.      ___       __  .__   __.
|   \/   |     /   \     |  | |  \ |  |
|  \  /  |    /  ^  \    |  | |   \|  |
|  |\/|  |   /  /_\  \   |  | |  . `  |
|  |  |  |  /  _____  \  |  | |  |\   |
|__|  |__| /__/     \__\ |__| |__| \__|

Loads users and tokens from appropriate files

Runs HTTP server and handles endpoint requests using Spark framework

Possible HTTP requests:
POST single user(at least nickname required)
GET single user(nickname required)
GET all users
PUT single user(at least nickname of existing required; overwrites only fields that were given)
DELETE single user(nickname required)

For authentication and authorization, token with sufficient rights is required in HTTP request Authentication header

 */

public class Main {
    public static void main(String[] args) {
        //Load and setup users
        UserController userController = new UserController();
        userController.readUserData();

        //Load and setup tokens
        TokenController.initialize();

        before((request, response) -> {
            // Set Content-Security-Policy header
            response.header("Content-Security-Policy", "connect-src 'self' http://localhost:4567;");
        });

        Spark.post("/users", ((request, response) -> {
            String authHeaderToken = request.headers("Authorization");
            if (authHeaderToken == null) {
                response.status(401);
                return "Null Authorization Token";
            }
            if (TokenController.tokenAuthentication(authHeaderToken)) {
                if (TokenController.tokenAuthorization(authHeaderToken, "add")){
                    response.type("application/json");
                    if (request.body().isEmpty()){
                        response.status(400);
                        return "Empty user information";
                    }
                    User user = new User("errored","mistake", "infinity", new ArrayList<>());
                    try{
                        user = new Gson().fromJson(request.body(), User.class);
                    }catch (Exception e){
                        response.status(400);
                        return "Invalid user information";
                    };
                    if(user.getNickname()==null)return "Empty nickname";
                    if(user.getNickname().isEmpty())return "Empty nickname";
                    if (userController.checkNicknameAvailable(user.getNickname())) {
                        userController.addUser(user);
                        return "User added successfully";
                    }else{
                        return "User nickname already exists; try again with another nickname";
                    }
                }else {
                    response.status(403);
                    return "Invalid privileges";
                }
            }else{
                response.status(401);
                return "Invalid Authorization Token";
            }
        }));

        Spark.get("/users", ((request, response) -> {
            response.type("application/json");
            String authHeaderToken = request.headers("Authorization");
            if (authHeaderToken == null) {
                response.status(401);
                return "Null Authorization Token";
            }
            if (TokenController.tokenAuthentication(authHeaderToken)) {
                if (TokenController.tokenAuthorization(authHeaderToken, "read")){
                    return new Gson().toJsonTree(userController.getUsers());
                }else {
                    response.status(403);
                    return "Invalid privileges";
                }
            }else{
                response.status(401);
                return "Invalid Authorization Token";
            }
        }));

        Spark.get("/users/:nickname", ((request, response) -> {
            response.type("application/json");
            String authHeaderToken = request.headers("Authorization");
            if (authHeaderToken == null) {
                response.status(401);
                return "Null Authorization Token";
            }
            if (TokenController.tokenAuthentication(authHeaderToken)) {
                if (TokenController.tokenAuthorization(authHeaderToken, "read")){
                    String userName = request.params(":nickname");
                    if (!userController.checkNicknameAvailable(userName)){
                        return new Gson().toJsonTree(userController.getUser(userName));
                    }else {
                        return "There is no user with the specified nickname";
                    }
                }else {
                    response.status(403);
                    return "Invalid privileges";
                }
            }else{
                response.status(401);
                return "Invalid Authorization Token";
            }
        }));

        Spark.delete("/users/:nickname", ((request, response) -> {
            response.type("application/json");
            String authHeaderToken = request.headers("Authorization");
            if (authHeaderToken == null) {
                response.status(401);
                return "Null Authorization Token";
            }
            if (TokenController.tokenAuthentication(authHeaderToken)) {
                if (TokenController.tokenAuthorization(authHeaderToken, "delete")){
                    String userName = request.params(":nickname");
                    if (!userController.checkNicknameAvailable(userName)){
                        userController.removeUser(userName);
                        return "User removed successfully";
                    }else {
                        return "Deletion failed; There is no user with the specified nickname";
                    }
                }else {
                    response.status(403);
                    return "Invalid privileges";
                }
            }else{
                response.status(401);
                return "Invalid Authorization Token";
            }
        }));

        Spark.put("/users", ((request, response) -> {
            response.type("application/json");
            String authHeaderToken = request.headers("Authorization");
            if (authHeaderToken == null) {
                response.status(401);
                return "Null Authorization Token";
            }
            if (TokenController.tokenAuthentication(authHeaderToken)) {
                if (TokenController.tokenAuthorization(authHeaderToken, "edit")){
                    User inputUser = new Gson().fromJson(request.body(), User.class);
                    return new Gson().toJsonTree(userController.editUser(inputUser));
                }else {
                    response.status(403);
                    return "Invalid privileges";
                }
            }else{
                response.status(401);
                return "Invalid Authorization Token";
            }
        }));
    }
}