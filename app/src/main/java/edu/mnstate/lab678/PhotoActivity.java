package edu.mnstate.lab678;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity {
    private static final String TAG = "Lab678-PhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Log.d(TAG, "onCreate: Started PhotoActivity");

        findViewById(R.id.backBtn).setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), MainActivity.class)));

    }
}
