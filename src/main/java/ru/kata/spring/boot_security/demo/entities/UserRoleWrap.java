package ru.kata.spring.boot_security.demo.entities;

import java.util.ArrayList;
import java.util.List;

public class UserRoleWrap {
    private User user;
    private List<Role> roleList;

    public UserRoleWrap(){}
    public UserRoleWrap(User user, List<Role> roleList) {
        this.user = user;
        this.roleList = roleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserRoleWrap{" +
                "user=" + user +
                ", roleList=" + roleList +
                '}';
    }
}
