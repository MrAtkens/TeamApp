package com.example.teamapp.models.Auth;


import com.example.teamapp.models.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {

    private String username;
    private String password;
    @ManyToMany
    private Collection<UserEntity> friends;

    public UserEntity(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
