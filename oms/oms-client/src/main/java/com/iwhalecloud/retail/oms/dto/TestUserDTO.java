package com.iwhalecloud.retail.oms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestUserDTO implements Serializable {


    private Integer id;
    private String username;
    private String password;
    private int age;

    @Override
    public String toString() {
        return "TestUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
