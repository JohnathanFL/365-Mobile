package edu.mnstate.majorproject1;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class TodoTask {
    boolean complete;
    int year, month, day;
    // Never intended to be seen and thus not a resource
    // Only here to tell me if something was left uninitialized
    String name = "@@FILLER_NAME@@";
    String desc = "@@FILLER_DESC@@";

    public TodoTask(int year, int month, int day, String name, String desc) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.name = name;
        this.desc = desc;
        this.complete = false;
    }


    public TodoTask(int year, int month, int day, String name, String desc, boolean complete) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.complete = complete;
        this.name = name;
        this.desc = desc;
    }
}
