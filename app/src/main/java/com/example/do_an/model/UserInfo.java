package com.example.do_an.model;

public class UserInfo {
    String Phone;
    String Name;
    String Birthday;
    String Sex;
    String Address;

    public UserInfo() {
    }

    public UserInfo(String phone, String name, String birthday, String sex, String address) {
        Phone = phone;
        Name = name;
        Birthday = birthday;
        Sex = sex;
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}