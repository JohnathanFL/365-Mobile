package edu.mnstate.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] empNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empNames = getResources().getStringArray(R.array.empNames);
        ListView listView = findViewById(R.id.empListView);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.emp_label, empNames);

        listView.setAdapter(adapter);
    }
}
