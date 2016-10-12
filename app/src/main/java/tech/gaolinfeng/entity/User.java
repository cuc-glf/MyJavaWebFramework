package tech.gaolinfeng.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by gaolf on 16/9/19.
 */
public class User {
    @JsonIgnore
    private int id;
    private String name;
    private String phone;
    private String email;
    @JsonIgnore
    private String passwd;
    @JsonIgnore
    private String salt;

    public User(String name, String phone, String email, String passwd) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.passwd = passwd;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
