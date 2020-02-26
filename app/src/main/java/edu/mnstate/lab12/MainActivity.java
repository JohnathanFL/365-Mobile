package edu.mnstate.lab12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> prodNames = new ArrayList<String>() {{
        add("Oaken Bow");
        add("Steel Dagger");
        add("Temerian Sword");
        add("Ash Staff");
    }};

    Button withBundle, withoutBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        withBundle = findViewById(R.id.withBundle);
        withoutBundle = findViewById(R.id.withoutBundle);


        withBundle.setOnClickListener(v -> {
            Intent go = new Intent(this, DuaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key1", "Main activity greeted you with Saluton!");
            bundle.putBoolean("key2", true);
            bundle.putStringArrayList("prodNames", prodNames);
            go.putExtras(bundle);
            startActivity(go);
        });

        withoutBundle.setOnClickListener(v -> {
            Intent go = new Intent(this, DuaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key1", "This is displayed in SecondActivity");
            bundle.putBoolean("key2", false);
            go.putExtras(bundle);
            startActivity(go);
        });
    }
}
