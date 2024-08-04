package com.example.tamir_project.Models;

public class Model_Chatting {

    String msg,time,user_id;
int seen;

    public Model_Chatting(String msg, String time, String user_id, int seen) {
        this.msg = msg;
        this.time = time;
        this.user_id = user_id;
        this.seen = seen;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public Model_Chatting() {
    }
}
