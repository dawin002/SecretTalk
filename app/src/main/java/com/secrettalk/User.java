package com.secrettalk;

import java.util.ArrayList;

public class User {
    public String dept;
    public String email;
    public String gender;
    public String id;
    public String name;
    public String nickname;
    public String phonenumber;
    public Boolean iscertified;
    public String studentid;
    public Boolean isbanned;
    public Integer ban;
    public Long bandate;
    public String password;
    public ArrayList<String> chatList;

    public User() {
    }

    public User(String dept, String email, String gender, String id, String name, String nickname, String phonenumber, Boolean iscertified, String studentid, Boolean isbanned, Integer ban, Long bandate, ArrayList<String> chatList) {
        this.dept = dept;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phonenumber = phonenumber;
        this.iscertified = iscertified;
        this.studentid = studentid;
        this.isbanned = isbanned;
        this.ban = ban;
        this.bandate =bandate;
        this.chatList = chatList;
    }



    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Boolean getIscertified() {
        return iscertified;
    }

    public void setIscertified(Boolean iscertified) {
        this.iscertified = iscertified;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public Boolean getIsbanned() {
        return isbanned;
    }

    public void setIsbanned(Boolean isbanned) {
        this.isbanned = isbanned;
    }

    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    public Long getBandate() {
        return bandate;
    }

    public void setBandate(Long bandate) {
        this.bandate = bandate;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public ArrayList<String> getChatList() {
        return chatList;
    }

    public void setChatList(ArrayList<String> chatList) {
        this.chatList = chatList;
    }
}
