package tech.gaolinfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by gaolf on 16/9/19.
 */
public class User {
    private int id;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birth;
    private int gender; // 1: male, 2: female
    private String passwd;

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public User(String name, String phone, String email, Date birth, int gender, String passwd) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", gender=" + gender +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
