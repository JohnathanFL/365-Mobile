/**
 * Major Project 1 - TODO App.
 * Johnathan Lee
 * Due 02/23/20
 */
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

    /**
     * Make a new task
     * @param year Due year
     * @param month Due month
     * @param day Due day
     * @param name Name of the task (single line)
     * @param desc Description of the task (may be multiline)
     */
    public TodoTask(int year, int month, int day, String name, String desc) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.name = name;
        this.desc = desc;
        this.complete = false;

        this.id = lastId++;
    }
}
