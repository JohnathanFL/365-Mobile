package edu.mnstate.majorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {
    private static final String TAG = "TaskDetailActivity";

    TextView taskName, taskDesc;
    ImageView taskComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskName = findViewById(R.id.taskName);
        taskDesc = findViewById(R.id.taskDesc);
        taskComplete = findViewById(R.id.taskComplete);


        TodoApp app = (TodoApp)getApplication();

        long taskID = getIntent().getLongExtra("taskID", -1);
        TodoTask task = app.allTasks.get(taskID);
        Log.d(TAG, "taskID: " + taskID);
        taskName.setText(task.name);
        taskDesc.setText(task.desc);
        taskComplete.setImageResource(task.complete ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);

    }
}
