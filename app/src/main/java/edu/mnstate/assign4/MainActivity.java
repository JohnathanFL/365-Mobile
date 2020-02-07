package edu.mnstate.assign4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.langList);
        list.setLayoutManager(new LinearLayoutManager(this));

        LangAdapter adapter = new LangAdapter();
        // Since the adapter can't see resources on its own
        // Must be /3 because it's in groups of 3
        adapter.setup(getResources().getIntArray(R.array.langs).length / 3);

        list.setAdapter(adapter);
    }
}
