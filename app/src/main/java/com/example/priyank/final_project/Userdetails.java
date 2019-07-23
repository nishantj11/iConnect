package com.example.priyank.final_project;

public class Userdetails {
    private String name;
    private String email;
    private Long contact;

    public Userdetails()
    {}
    public Userdetails(String name,String email,Long contact)
    {
        this.name =name;
        this.contact=contact;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}