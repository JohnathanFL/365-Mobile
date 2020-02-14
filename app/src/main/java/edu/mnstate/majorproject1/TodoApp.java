package edu.mnstate.majorproject1;

import android.app.Application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TodoApp extends Application {
    HashMap<Calendar, ArrayList<TodoTask>> tasks = new HashMap<>();
}
