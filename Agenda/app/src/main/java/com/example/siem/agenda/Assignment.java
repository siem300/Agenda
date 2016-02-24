package com.example.siem.agenda;

/**
 * Created by siem on 17/02/2016.
 */
public class Assignment {
    private long id;
    private String title;
    private String assignment;
    private String date;


    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getAssignment() {

        return assignment;
    }

    public void setAssignment(String assignment){
        this.assignment = assignment;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date =  date;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String toString(){
        return title;
    }
}
