package com.example.sudokuapp;

public class GridValue {
    public StringBuilder dummy_data,dummy_original;
    String level,time,status;
    public GridValue(StringBuilder current_data,StringBuilder original_data,String head,String time,String status)
    {
        this.dummy_data =current_data;
        this.dummy_original=original_data;
        this.level=head;
        this.time=time;
        this.status=status;
    }

    public GridValue(String head,String time,String status){
        this.level=head;
        this.time=time;
        this.status=status;
    }
    public StringBuilder setDummyData(){return this.dummy_data;}
    public StringBuilder setDummyOriginal(){return this.dummy_original;}
    public String setLevel(){return this.level;}
    public String setTime(){return this.time;}
    public String setStatus(){return this.status;}
    public StringBuilder getDummyData(){return this.dummy_data;}
    public StringBuilder getDummyOriginal(){
        return this.dummy_original;
    }
    public String getLevel(){return this.level;}
    public String getTime(){return  this.time;}
    public String getStatus(){return this.status;}


}
