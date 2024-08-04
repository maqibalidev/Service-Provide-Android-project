package com.example.tamir_project.Models;

public class Model_fixedWorker {

    int id ,star_1,star_2,star_3,star_4,star_5;
    String name,disc,prof,reviews_count,image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Model_fixedWorker(int id,String image, int star_1, int star_2, int star_3, int star_4, int star_5, String name, String disc, String prof, String reviews_count) {
        this.image = image;
        this.id=id;
        this.star_1 = star_1;
        this.star_2 = star_2;
        this.star_3 = star_3;
        this.star_4 = star_4;
        this.star_5 = star_5;
        this.name = name;
        this.disc = disc;
        this.prof = prof;
        this.reviews_count = reviews_count;

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

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(String reviews_count) {
        this.reviews_count = reviews_count;
    }


}
