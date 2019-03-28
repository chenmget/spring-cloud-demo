package com.iwhalecloud.retail.oms.entity;

import lombok.Data;

@Data
public class UserCopyTest {

    private int id;
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
