package org.example;

public class Token {
    private String token;
    private boolean readRights;
    private boolean addRights;
    private boolean editRights;

    public boolean hasDeleteRights() {
        return deleteRights;
    }

    public boolean hasEditRights() {
        return editRights;
    }

    public boolean hasAddRights() {
        return addRights;
    }

    public boolean hasReadRights() {
        return readRights;
    }

    public String getToken() {
        return token;
    }

    private boolean deleteRights;
    public Token(String token, boolean readRights, boolean addRights, boolean editRights, boolean deleteRights) {
        super();
        this.token = token;
        this.readRights = readRights;
        this.addRights = addRights;
        this.editRights = editRights;
        this.deleteRights = deleteRights;
    }
}
