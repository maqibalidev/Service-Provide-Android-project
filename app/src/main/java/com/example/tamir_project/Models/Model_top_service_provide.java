package com.example.tamir_project.Models;

public class Model_top_service_provide {

    String name,profession,location,image;
    int star_1,star_2,star_3,star_4,star_5,val,id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStar_1() {
        return star_1;
    }

    public void setStar_1(int star_1) {
        this.star_1 = star_1;
    }

    public int getStar_2() {
        return star_2;
    }

    public void setStar_2(int star_2) {
        this.star_2 = star_2;
    }

    public int getStar_3() {
        return star_3;
    }

    public void setStar_3(int star_3) {
        this.star_3 = star_3;
    }

    public int getStar_4() {
        return star_4;
    }

    public void setStar_4(int star_4) {
        this.star_4 = star_4;
    }

    public int getStar_5() {
        return star_5;
    }

    public void setStar_5(int star_5) {
        this.star_5 = star_5;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Model_top_service_provide(String name, String profession, String location, String image, int val,int id) {
        this.name = name;
        this.profession = profession;
        this.location = location;
        this.image = image;
        this.val=val;
        this.id=id;
    }
    public Model_top_service_provide() {
    }
}
