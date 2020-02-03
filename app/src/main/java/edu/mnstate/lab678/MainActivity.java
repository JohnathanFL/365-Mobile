package edu.mnstate.lab678;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lab678-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started App");

        findViewById(R.id.btnShowPhoto).setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), PhotoActivity.class)));
    }
}
