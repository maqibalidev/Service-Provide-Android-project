package com.example.tamir_project.Models;

public class Model_Chats {
    String name,disc,time,worker_id,image;
String noti;

    public Model_Chats(String name, String disc, String time, String worker_id, String image, String noti) {
        this.name = name;
        this.disc = disc;
        this.time = time;
        this.worker_id = worker_id;
        this.image = image;
        this.noti = noti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String isNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }

    public Model_Chats() {
    }
}
