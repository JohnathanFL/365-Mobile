/**
 * Major Project 1 - TODO App.
 * Johnathan Lee
 * Due 02/23/20
 */
package edu.mnstate.majorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Show more detail on a task.
 * Must be intent-d with the task's ID.
 */
public class TaskDetailActivity extends AppCompatActivity {
    private static final String TAG = "TaskDetailActivity";

    TextView taskName, taskDesc;
    ImageView taskComplete;

    TodoTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskName = findViewById(R.id.taskName);
        taskDesc = findViewById(R.id.taskDesc);
        taskComplete = findViewById(R.id.taskComplete);


        TodoApp app = (TodoApp)getApplication();

        long taskID = getIntent().getLongExtra("taskID", -1);
        task = app.allTasks.get(taskID);
        Log.d(TAG, "taskID: " + taskID);
        taskName.setText(String.format("%s\n%04d-%02d-%02d", task.name, task.year, task.month, task.day));
        taskDesc.setText(task.desc);
        taskComplete.setImageResource(task.complete ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        taskComplete.setOnClickListener(v -> {
            task.complete = !task.complete;
            taskComplete.setImageResource(task.complete ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(this, DailyActivity.class);

        go.putExtra("year", task.year);
        go.putExtra("month", task.month);
        go.putExtra("day", task.day);
        startActivity(go);
    }
}
