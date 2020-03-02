package edu.mnstate.lab17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText userName, userPass;
    private Button savePrefBtn, loadPrefBtn;
    private TextView showPrefText;

    SharedPreferences prefs;

    public static final String MY_PREFS = "MyPrefs";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.userPass);
        savePrefBtn = findViewById(R.id.savePrefBtn);
        loadPrefBtn = findViewById(R.id.loadPrefBtn);
        showPrefText = findViewById(R.id.showPrefText);

        prefs = this.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        loadPrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String defUser = "NONE", defPass = "NONE";
                String user = prefs.getString(USERNAME, defUser),
                        pass = prefs.getString(PASSWORD, defPass);

                showPrefText.setText(user + " " + pass);
            }
        });

        savePrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString(),
                        pass = userPass.getText().toString();

                SharedPreferences.Editor ed = prefs.edit();
                ed.putString(USERNAME, user);
                ed.putString(PASSWORD, pass);
                ed.commit();
                Toast.makeText(MainActivity.this, "SAVED PREFERENCES", Toast.LENGTH_LONG).show();

                userName.setText("");
                userPass.setText("");5
            }
        });
    }
}
