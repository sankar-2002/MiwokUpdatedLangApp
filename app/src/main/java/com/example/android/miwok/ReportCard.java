package com.example.android.miwok;  //just an homework project class this is not related to miwok app

public class ReportCard {

    char grade;

    int marks;

    String name;

    private String mname;
    private int mmarks;
    private char mgrade;

    public ReportCard(String name,int marks,char grade) { //constructor

        mname = name;
        mmarks = marks;
        mgrade = grade;

    }

    public String getmname() { //getters..
        return mname;
    }
    public int getmmarks() {
        return mmarks;
    }
    public char getmgrade() {
        return mgrade;
    }

    @Override
    public String toString() {

        return"Student name is " + getmname() + " Marks Obtained " + getmmarks() + " Grade Obtained " + getmgrade();

    }
}
