package edu.mnstate.majorproject1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * View a calendar
 */
public class CalendarActivity extends AppCompatActivity {
    int curYear, curMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
