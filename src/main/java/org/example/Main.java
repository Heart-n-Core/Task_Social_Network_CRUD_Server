package org.example;
import com.google.gson.Gson;
import spark.Spark;
public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        TokenController.initialize();

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
                    User user = new Gson().fromJson(request.body(), User.class);
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