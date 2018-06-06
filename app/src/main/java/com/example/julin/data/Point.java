package com.example.julin.data;

public class Point<T> {
    private DangerousThing Danger;
    private int ID;

    public DangerousThing getDanger(){
        return Danger;
    }
    public void setDanger(DangerousThing Danger){
        this.Danger = Danger;
    }

    public int getNodesID(){
        return ID;
    }
}
