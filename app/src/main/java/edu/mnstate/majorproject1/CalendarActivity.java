package edu.mnstate.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


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

        // Learned how to actually set the ActionBar's view itself from here
        // https://www.tutorialspoint.com/how-to-create-custom-actionbar-in-android
        // You'd think these two together would be redundant, but welcome to android, I guess?
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().getCustomView().findViewById(R.id.addTaskBtn).setOnClickListener(v -> {
            Intent go = new Intent(this, AddTaskActivity.class);
            startActivity(go);
        });
    }
}
