package org.example;

/*
Token rights data class
includes constructor and getters
* */

public class Token {
    private boolean readRights;
    private boolean addRights;
    private boolean editRights;
    private boolean deleteRights;

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

    public Token(boolean readRights, boolean addRights, boolean editRights, boolean deleteRights) {
        super();
        this.readRights = readRights;
        this.addRights = addRights;
        this.editRights = editRights;
        this.deleteRights = deleteRights;
    }
}
