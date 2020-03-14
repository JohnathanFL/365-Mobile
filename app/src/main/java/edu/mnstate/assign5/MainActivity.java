package edu.mnstate.assign5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    InputFragment inputFrag;
    DisplayFragment displayFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputFrag = (InputFragment)getSupportFragmentManager().findFragmentById(R.id.inputFrag);
        displayFrag = (DisplayFragment)getSupportFragmentManager().findFragmentById(R.id.displayFrag);
        inputFrag.consumer = displayFrag;

        displayFrag.setAll(PriceConsumer.GPU.Intel, 8, 1, 0, 0);
    }
}
