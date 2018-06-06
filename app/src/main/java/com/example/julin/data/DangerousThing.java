package com.example.julin.data;

public class DangerousThing {
    private int ID;
    private String Name;
    private String Species;
    private boolean aBoolean;

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

    public String getSpecies(){
        return Species;
    }
    public void setSpecies(String Species){
        this.Species = Species;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public boolean getaBoolean() {
        return aBoolean;
    }

    public String getNameAndSpecies(){return Name+" : "+Species;}

}
