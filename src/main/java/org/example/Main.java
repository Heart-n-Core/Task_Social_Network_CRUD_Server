package org.example;
import com.google.gson.Gson;
import spark.Spark;
public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        TokenController.initialize();

        Spark.get("/hello", (req, res)->"Hello, world");

        Spark.get("/hello/:name", (req, res)->{
            return "Hello, "+ req.params(":name");
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
    }
}