package com.example.tamir_project.Models;

public class Model_mytasks {

    String days;
    String accepted_by_txt;
    String accepted_by_name;
    String budget_txt;
    String budget_price;
    String location_txt;
    String title;
    String status;
    String completetion_time;
    String  payemnt_status;

    String location;
    String worker_id;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAccepted_by_txt() {
        return accepted_by_txt;
    }

    public void setAccepted_by_txt(String accepted_by_txt) {
        this.accepted_by_txt = accepted_by_txt;
    }

    public String getAccepted_by_name() {
        return accepted_by_name;
    }

    public void setAccepted_by_name(String accepted_by_name) {
        this.accepted_by_name = accepted_by_name;
    }

    public String getBudget_txt() {
        return budget_txt;
    }

    public void setBudget_txt(String budget_txt) {
        this.budget_txt = budget_txt;
    }

    public String getBudget_price() {
        return budget_price;
    }

    public void setBudget_price(String budget_price) {
        this.budget_price = budget_price;
    }

    public String getLocation_txt() {
        return location_txt;
    }

    public void setLocation_txt(String location_txt) {
        this.location_txt = location_txt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletetion_time() {
        return completetion_time;
    }

    public void setCompletetion_time(String completetion_time) {
        this.completetion_time = completetion_time;
    }

    public String getPayemnt_status() {
        return payemnt_status;
    }

    public void setPayemnt_status(String payemnt_status) {
        this.payemnt_status = payemnt_status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public Model_mytasks(String days, String accepted_by_txt, String accepted_by_name, String budget_txt, String budget_price, String location_txt, String title, String status, String completetion_time, String payemnt_status, String location, String worker_id) {
        this.days = days;
        this.accepted_by_txt = accepted_by_txt;
        this.accepted_by_name = accepted_by_name;
        this.budget_txt = budget_txt;
        this.budget_price = budget_price;
        this.location_txt = location_txt;
        this.title = title;
        this.status = status;
        this.completetion_time = completetion_time;
        this.payemnt_status = payemnt_status;
        this.location = location;
        this.worker_id = worker_id;
    }

    public Model_mytasks() {
    }


}
