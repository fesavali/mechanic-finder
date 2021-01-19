package com.felohscodes.onroadmech.Models;

public class CompanyRegModel {
    public String pService;
    public String cName;
    public String license;
    public String phone;
    public String location;
    public String cService;
    public String cService1;

    public CompanyRegModel(String pService, String cName, String license, String phone, String location, String service, String service1) {
        this.pService = pService;
        this.cName = cName;
        this.license = license;
        this.phone = phone;
        this.location = location;
        this.cService = service;
        this.cService1 = service1;
    }

    public String getpService() {
        return pService;
    }

    public void setpService(String pService) {
        this.pService = pService;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getcService() {
        return cService;
    }

    public void setcService(String service) {
        cService = service;
    }

    public String getcService1() {
        return cService1;
    }

    public void setcService1(String service1) {
        cService1 = service1;
    }

}
