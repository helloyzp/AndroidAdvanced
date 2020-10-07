package com.example.javaadvanced.ui.RecyclerView.RecyclerViewDecoration;

public class Star {

    private String name;
    private String groundName;

    public Star(String name, String groundName) {
        this.name = name;
        this.groundName = groundName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }
}
