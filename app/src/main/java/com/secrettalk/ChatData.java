
package com.secrettalk;

public class ChatData {

    private String content ;
    private String name ;
    private String img = null;
    private String sendTime ;
    private String sid, dept;
    private int viewType ;

    public ChatData(){

    }

    public ChatData(String content, String name , String sendTime, int viewType, String sid, String dept) {
        this.content = content;
        this.name = name;
        this.sendTime = sendTime;
        this.viewType = viewType;
        this.sid = sid;
        this.dept = dept;
    }

    public ChatData(String content, String name, String img, String sendTime, int viewType, String sid, String dept) {
        this.content = content;
        this.name = name;
        this.img = img;
        this.sendTime = sendTime;
        this.viewType = viewType;
        this.sid = sid;
        this.dept = dept;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSendTime() { return sendTime; }

    public void setSendTime(String sendTime) { this.sendTime = sendTime; }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


}