package org.example;

import java.util.ArrayList;

public class TokenController {
    static private ArrayList<Token> tokens = new ArrayList<>();
    static {
        //All rights token
        tokens.add(new Token("apXDHN7DV4MV9lrTTouIEzGs0uyaDmau",true,true,true,true));

        //Read only token
        tokens.add(new Token("x442xeKaqwspZ98drRBziGOoTyPgx4N8",true,false,false,false));
    }
//    private Token getTocen(){}
    public static void initialize(){}
    public static boolean tokenAuthentication(String tokenString){
        for (Token token : tokens) {
            if (token.getToken().equals(tokenString)){
                return true;
            }
        }
        return false;
    }
    public static boolean tokenAuthorization(String tokenString, String rightsType){
        for (Token token : tokens) {
            if (token.getToken().equals(tokenString)){
                switch (rightsType){
                    case "add":return token.hasAddRights();
                    case "delete":return token.hasDeleteRights();
                    case "edit":return token.hasEditRights();
                    case "read":return token.hasReadRights();
                    default:return false;
                }
            }
        }
        return false;
    }
}
