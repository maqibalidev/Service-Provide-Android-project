package com.example.tamir_project.Models;

public class Models_nearby_service {

    String  name,work_disc,offerd_by_txt,date,price,location,profession;
int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork_disc() {
        return work_disc;
    }

    public void setWork_disc(String work_disc) {
        this.work_disc = work_disc;
    }

    public String getOfferd_by_txt() {
        return offerd_by_txt;
    }

    public void setOfferd_by_txt(String offerd_by_txt) {
        this.offerd_by_txt = offerd_by_txt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Models_nearby_service(String name, String work_disc, String offerd_by_txt, String date, String price, String location, String profession, int id) {
        this.name = name;
        this.work_disc = work_disc;
        this.offerd_by_txt = offerd_by_txt;
        this.date = date;
        this.price = price;
        this.location = location;
        this.profession = profession;
        this.id = id;
    }
    public Models_nearby_service() {
    }
}
