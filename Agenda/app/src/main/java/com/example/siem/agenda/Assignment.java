package com.example.siem.agenda;

/**
 * Created by siem on 17/02/2016.
 */
public class Assignment {
    private long id;
    private String assignment;

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

    public String toString(){
        return assignment;
    }
}
