package edu.mnstate.majorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * View all tasks assigned to a certain day
 */
public class DailyActivity extends AppCompatActivity {
    private static final String TAG = "DailyActivity";
    RecyclerView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        list = findViewById(R.id.taskList);

        Intent cause = getIntent();
        int year = cause.getIntExtra("year", 0);
        int month = cause.getIntExtra("month", 0);
        int day = cause.getIntExtra("day", 0);

        getSupportActionBar().setTitle(getResources().getString(R.string.tasksFor) + " " + year + "-" + (month + 1) + "-" + day);

        Log.d(TAG, "onCreate: Got to Daily@" + year + '-' + month + '-' + day);

        TodoApp myApp = (TodoApp)getApplication();
        ArrayList<TodoTask> tasks = myApp.getDay(year, month, day);
        Log.d(TAG, "onCreate: Size was " + tasks.size());

        list.setAdapter(new TaskAdapter(tasks));
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
        ArrayList<TodoTask> tasks;

        TaskAdapter(ArrayList<TodoTask> tasks) {
            super();
            this.tasks = tasks;
            notifyDataSetChanged();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_template, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TodoTask task = this.tasks.get(position);
            holder.taskName.setText(task.name);
            holder.taskDesc.setText(task.desc);
            holder.complete.setImageResource(task.complete ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);

            holder.holder.setOnClickListener(v -> {
                Intent go = new Intent(holder.taskName.getContext(), TaskDetailActivity.class);
                go.putExtra("taskID", this.tasks.get(position).id);

                startActivity(go);

            });
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: " + this.tasks.size());
            return this.tasks.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View holder;
            ImageView complete;
            TextView taskName;
            TextView taskDesc;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.holder = itemView;
                this.complete = itemView.findViewById(R.id.taskComplete);
                this.taskName = itemView.findViewById(R.id.taskName);
                this.taskDesc = itemView.findViewById(R.id.taskDesc);
            }
        }
    }
}
