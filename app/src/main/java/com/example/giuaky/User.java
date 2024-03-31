package com.example.giuaky;

public class User {
    private int id;
    private String mattrc,matsau;

    public User(int id, String mattrc, String matsau) {
        this.id = id;
        this.mattrc = mattrc;
        this.matsau = matsau;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMattrc() {
        return mattrc;
    }

    public void setMattrc(String mattrc) {
        this.mattrc = mattrc;
    }

    public String getMatsau() {
        return matsau;
    }

    public void setMatsau(String matsau) {
        this.matsau = matsau;
    }
}
