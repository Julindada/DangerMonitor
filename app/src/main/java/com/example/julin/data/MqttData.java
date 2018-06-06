package com.example.julin.data;

public class MqttData {

    private int id;
    private Float data;

    public int getMqttID(){
        return id;
    }
    public void setMqttID(int id){
        this.id = id;
    }

    public Float getData(){
        return data;
    }
    public void setData(Float data){
        this.data = data;
    }

}
