package edu.mnstate.jz1652ve.assign6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JobList extends Fragment {
    private static final String TAG = "JobList";
    static final String FRAG_TAG = "JobList";

    View me;
    EditText searchDesc;
    ImageButton searchBtn;

    JobAdapter adapter;
    RecyclerView jobList;


    Retrofit retrofit;
    GithubJobsInterface api;

    public JobList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://jobs.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = this.retrofit.create(GithubJobsInterface.class);
    }

    public void searchFor(final String text) {
        Toast.makeText(getContext(), "Searching...", Toast.LENGTH_SHORT).show();
        this.api.getPositions(text)
                .enqueue(new Callback<List<GithubJobsInterface.Position>>() {
                    @Override
                    public void onResponse(Call<List<GithubJobsInterface.Position>> call, Response<List<GithubJobsInterface.Position>> response) {
                        if(response.body() != null) {
                            JobList.this.adapter.jobs = response.body();

                            for(GithubJobsInterface.Position pos : JobList.this.adapter.jobs) {
                                Log.d(TAG, "  Job: " + pos.title);
                            }
                        } else {
                            JobList.this.adapter.jobs.clear();
                            Toast.makeText(JobList.this.getContext(), "No jobs matching '" + text + "'", Toast.LENGTH_SHORT).show();
                        }
                        JobList.this.adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<GithubJobsInterface.Position>> call, Throwable t) {
                        Toast.makeText(JobList.this.getContext(), "Failed to get jobs matching '" + text + "'", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void openURL(String str) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(str));
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.me = inflater.inflate(R.layout.fragment_job_list, container, false);
        searchDesc = this.me.findViewById(R.id.searchDesc);
        jobList = this.me.findViewById(R.id.jobList);
        adapter = new JobAdapter();
        jobList.setAdapter(adapter);
        jobList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchBtn = this.me.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(v -> searchFor(this.searchDesc.getText().toString()));

        return this.me;
    }

    public class JobAdapter extends RecyclerView.Adapter<JobAdapter.Holder> {
        public List<GithubJobsInterface.Position> jobs;

        @NonNull
        @Override
        public JobAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(getLayoutInflater().inflate(R.layout.item_job, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull JobAdapter.Holder holder, int position) {
            GithubJobsInterface.Position job = this.jobs.get(position);

            holder.jobTitle.setText(job.title);
            holder.jobType.setText(job.type);
            holder.jobComp.setText(job.company);

            holder.me.setOnClickListener(v -> openURL(job.url));
        }

        @Override
        public int getItemCount() {
            return jobs == null ? 0 : jobs.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            View me;
            TextView jobTitle, jobComp, jobType;

            public Holder(@NonNull View itemView) {
                super(itemView);

                this.me = itemView;
                this.jobTitle = itemView.findViewById(R.id.jobTitle);
                this.jobComp = itemView.findViewById(R.id.jobComp);
                this.jobType = itemView.findViewById(R.id.jobType);
            }
        }
    }
}
