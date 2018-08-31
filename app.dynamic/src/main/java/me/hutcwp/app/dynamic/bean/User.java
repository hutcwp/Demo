package me.hutcwp.app.dynamic.bean;

/**
 * Created by Administrator on 2018/1/15.
 */

public class User {

    private int id;
    private String phone;
    private String username;
    private String password;

    public User(){

    }

    public User(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public User(int id, String phone, String username, String password) {
        this.id = id;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
