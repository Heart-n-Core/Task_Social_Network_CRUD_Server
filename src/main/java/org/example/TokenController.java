package org.example;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
    Loading tokens from tokens file into Hashmap
    Computing Authentication and Authorization results
*/


public class TokenController {
    static private HashMap<String, Token> tokens = new HashMap<>();
    static {
        tokens = readTokenInfo();
    }
    public static void initialize(){}
    public static boolean tokenAuthentication(String tokenString){
            if (tokens.containsKey(tokenString)){
                return true;
        }
        return false;
    }
    public static boolean tokenAuthorization(String tokenString, String rightsType){
            if (tokens.get(tokenString) != null){
                Token token = tokens.get(tokenString);
                switch (rightsType){
                    case "add":return token.hasAddRights();
                    case "delete":return token.hasDeleteRights();
                    case "edit":return token.hasEditRights();
                    case "read":return token.hasReadRights();
                    default:return false;
                }
            }
        return false;
    }
    private static HashMap<String, Token> readTokenInfo(){
        HashMap<String, Token> tokens = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("tokens"));
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()){
                    if (line.charAt(0)!= '#' && line.charAt(0)!= '\n'){
                        String[] parts = line.split(":");
                        if (parts.length > 1){
                            boolean addRights = line.contains("add");
                            boolean readRights = line.contains("read");
                            boolean editRights = line.contains("edit");
                            boolean deleteRights = line.contains("delete");
                            tokens.put(parts[0],new Token(readRights,addRights,editRights,deleteRights));
                        }
                    }
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

}
