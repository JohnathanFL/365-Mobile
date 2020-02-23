package edu.mnstate.majorproject1;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TodoApp extends Application {
    private static final String TAG = "TodoApp";

    public HashMap<Long, TodoTask> allTasks = new HashMap<>();
    public HashMap<Long, ArrayList<TodoTask>> tasks = new HashMap<>();


    /**
     * Sugar for normal getDay where you can offload the Calendar creation to someone else.
     * Never use this for adding a new task. Only for getting existing tasks.
     * @param year The year of the tasklist
     * @param month The month of the tasklist. NOTE THAT THIS IS ZERO-BASED. FEBRUARY IS 1, NOT 2
     *              ^ Guess who messed up for an hour straight?
     * @param day The day of the tasklist
     * @return The tasks that must be completed by year-month-day
     */
    public ArrayList<TodoTask> getDay(int year, int month, int day) {
        // Why go through the trouble here, you might ask?
        // Because Java's date library is so stupid that a "bit" of bit-twiddling ended up being
        // easier to get working.
        long key =
                (year & 0b111111111111) // Year may only have the first 12 bits (to ~4096 AD)
                | (month << 12) // Month only needs 4 bits (12 months)
                | (day << 15) // Day only needs 5 bits (31 days max)
        ;

        // Since apparently even a simple .putIfAbsent is locked down to an API level
        // That's Java for ya
        if(this.tasks.containsKey(key))
            return this.tasks.get(key);
        else {
            Log.d(TAG, "getDay: Created new list for " + year + '-' + month + '-' + day);
            this.tasks.put(key, new ArrayList<>());
            return this.tasks.get(key);
        }
    }

    /**
     * This should be used instead of getDay.add for adding tasks.
     * @param task The new task to add.
     * @return The Task's .id field
     */
    public long addTask(TodoTask task) {
        this.allTasks.put(task.id, task);
        this.getDay(task.year, task.month, task.day).add(task);
        return task.id;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        // Remember: months are 0-indexed, but days aren't.
        // Go figure.
        // This one's only for testing. Keep removed for production.
        // this.addTask(new TodoTask(2020, 1, 23, "MP1", "Do the major project"));
    }


}
