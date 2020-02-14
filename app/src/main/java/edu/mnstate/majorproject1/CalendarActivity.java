package edu.mnstate.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;


public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    
    CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        cal = findViewById(R.id.calendarView);

        cal.setOnDateChangeListener((v, year, month, day) -> {
            Log.d(TAG, "onCreate: Date changed to " + year + '-' + month + '-' + day);
            Intent moveToDaily = new Intent(this, DailyActivity.class);
            moveToDaily.putExtra("year", year);
            moveToDaily.putExtra("month", month);
            moveToDaily.putExtra("day", day);
            startActivity(moveToDaily);
        });
    }
}
