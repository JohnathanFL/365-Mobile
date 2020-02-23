/**
 * Major Project 1 - TODO App.
 * Johnathan Lee
 * Due 02/23/20
 */
package edu.mnstate.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Create a new activity, then go to that day's view.
 */
public class AddTaskActivity extends AppCompatActivity {
    private static final String TAG = "AddTaskActivity";
    EditText taskName, taskDesc;
    DatePicker taskDate;

    Button taskSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        TodoApp app = (TodoApp)getApplication();

        taskName = findViewById(R.id.taskName);
        taskDesc = findViewById(R.id.taskDesc);
        taskDate = findViewById(R.id.taskDate);
        taskSubmit = findViewById(R.id.taskSubmit);

        //SpinnerAdapter adapter = new SimpleAdapter(this, new ArrayList<HashMap<String, Integer>>(){{add(app.assignees);}}, SIMPLE_);

        taskName.setOnEditorActionListener((p, q, r) -> {
            taskSubmit.setEnabled(taskName.getText().length() != 0);
            return false;
        });

        taskSubmit.setOnClickListener(v -> {
            int year = taskDate.getYear();
            int month = taskDate.getMonth();
            int day = taskDate.getDayOfMonth();


            app.addTask(new TodoTask(year, month, day, taskName.getText().toString(), taskDesc.getText().toString()));

            Intent go = new Intent(this, DailyActivity.class);
            go.putExtra("year", year);
            go.putExtra("month", month);
            go.putExtra("day", day);
            startActivity(go);
            finish();
        });
    }


}
