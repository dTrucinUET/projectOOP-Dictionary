package com.example.dictionary;

public class Student {
    private String id;
    private String name;
    private String passWord;
    private String phoneNumber;
    private String address;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public Student(String id, String name, String passWord, String phoneNumber, String address, String email, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private String dateOfBirth;
}
