package edu.mnstate.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * View all tasks assigned to a certain day
 */
public class DailyActivity extends AppCompatActivity {
    private static final String TAG = "DailyActivity";
    Calendar day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent cause = getIntent();
        int year = cause.getIntExtra("year", 0);
        int month = cause.getIntExtra("month", 0);
        int day = cause.getIntExtra("day", 0);
        this.day = Calendar.getInstance();
        this.day.set(year, month, day);

        Log.d(TAG, "onCreate: Got to Daily@" + year + '-' + month + '-' + day);
    }
}
