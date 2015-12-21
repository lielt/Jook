package com.backend.entities;

import com.backend.enums.Level;

public class Admin extends com.backend.entities.User
{
    private Level Level;

    public Admin(String ID, String firstName, String lastName, String phone, String cellPhone, String email, String street, String num, String city, String applicationPassword, boolean block, com.backend.enums.Level level) throws Exception {
        super(ID, firstName, lastName, phone, cellPhone, email, street, num, city, applicationPassword, block);
        Level = level;
    }

    public Level getLevel() {
        return Level;
    }

    public void setLevel(Level level) {
        Level = level;
    }


}
