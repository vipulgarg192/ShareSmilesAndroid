package com.cipher.sharesmilesandroid.modals;

import java.io.Serializable;

public class Organisations implements Serializable {

    private String organisationName;
    private int id;

    public Organisations(int i, String organisationName) {
        this.id = i;
        this.organisationName = organisationName;
    }


    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
