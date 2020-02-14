package edu.mnstate.majorproject1;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class TodoTask {
    boolean complete;
    @NonNull
    Calendar when;
    // Never intended to be seen and thus not a resource
    // Only here to tell me if something was left uninitialized
    String name = "@@FILLER_NAME@@";
    String desc = "@@FILLER_DESC@@";

    public TodoTask(@NonNull Calendar when, String name, String desc) {
        this.when = when;
        this.name = name;
        this.desc = desc;
        this.complete = false;
    }


    public TodoTask(@NonNull Calendar when, String name, String desc, boolean complete) {
        this.complete = complete;
        this.when = when;
        this.name = name;
        this.desc = desc;
    }
}
