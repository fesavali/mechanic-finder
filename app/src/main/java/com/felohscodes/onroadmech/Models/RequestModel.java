package com.felohscodes.onroadmech.Models;

public class RequestModel {
    public String garage;
    public String service;
    public String phone;

    public RequestModel() {
    }

    public RequestModel(String garage, String service, String phone) {
        this.garage = garage;
        this.service = service;
        this.phone = phone;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
