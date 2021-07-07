package com.example.mechfinder;

public class companyGetter {
    public String uid;
    public String phone;
    public String company;
    public String servic;
    public String servic1;
    public String servic2;
    public String servic3;
    public String servic4;

    public companyGetter(String uid, String phone, String company, String servic, String servic1, String servic2, String servic3, String servic4) {
        this.uid = uid;
        this.phone = phone;
        this.company = company;
        this.servic = servic;
        this.servic1 = servic1;
        this.servic2 = servic2;
        this.servic3 = servic3;
        this.servic4 = servic4;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getServic() {
        return servic;
    }

    public void setServic(String servic) {
        this.servic = servic;
    }

    public String getServic1() {
        return servic1;
    }

    public void setServic1(String servic1) {
        this.servic1 = servic1;
    }

    public String getServic2() {
        return servic2;
    }

    public void setServic2(String servic2) {
        this.servic2 = servic2;
    }

    public String getServic3() {
        return servic3;
    }

    public void setServic3(String servic3) {
        this.servic3 = servic3;
    }

    public String getServic4() {
        return servic4;
    }

    public void setServic4(String servic4) {
        this.servic4 = servic4;
    }

}
