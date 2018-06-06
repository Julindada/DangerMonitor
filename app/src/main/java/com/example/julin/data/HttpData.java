package com.example.julin.data;

import java.util.List;

public class HttpData {
    private int ID;
    private String Name;
    private List<Point> Nodes;

    public int getID(){
         return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }

    public List<Point> getNodes(){
        return Nodes;
    }
    public void setName(List<Point> Nodes){
        this.Name = Name;
    }

}
