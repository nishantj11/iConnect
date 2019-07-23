package com.example.priyank.final_project;

public class Listdata {
    public String aname;
    public Long acontact;
    public String aemail;

    public Listdata(){}

    public Listdata(String aname,Long acontact,String aemail){
        this.acontact = acontact;
        this.aemail = aemail;
        this.aname =aname;
    }
    public String getAname() {
        return aname;
    }

    public void setAname(String name) {
        this.aname = name;
    }

    public Long getAcontact() {
        return acontact;
    }

    public void setAcontact(Long contact) {
        this.acontact = contact;
    }

    public String getAemail() {
        return aemail;
    }

    public void setAemail(String email) {
        this.aemail = email;
    }
}
