package edu.mnstate.majorproject1;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class TodoTask {
    // The task's ID in the master array.
    long id;
    static long lastId = 0;

    boolean complete;
    // Remember: In true, inscrutable java fashion, months are 0-indexed, but not days or years.
    int year, month, day;
    String name;
    String desc;

    public TodoTask(int year, int month, int day, String name, String desc) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.name = name;
        this.desc = desc;
        this.complete = false;

        this.id = lastId++;
    }


    public TodoTask(int year, int month, int day, String name, String desc, boolean complete) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.complete = complete;
        this.name = name;
        this.desc = desc;

        this.id = lastId++;
    }
}
