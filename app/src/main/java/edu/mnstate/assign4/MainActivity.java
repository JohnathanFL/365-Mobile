/*
Simple login screen test, themed to look like an MSUM app.

Johnathan Lee

Due 01/31/20
*/

package edu.mnstate.assign4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText usernameField, passwordField;

    /**
     * Setup our app
     * @param savedInstanceState Some android voodoo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginBtn);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Validate the usernameField === "GUEST" and passwordField === "PASS"
             * If it validates, display a Toat ("SUCCESS")
             * @param v What view was clicked?
             */
            @Override
            public void onClick(View v) {
                Log.println(Log.DEBUG, "loginBtn.onClick", "user: " + usernameField.getText() + " | pass: " + passwordField.getText());
                if(usernameField.getText().toString().equals("GUEST") && passwordField.getText().toString().equals("PASS")) {
                    Toast.makeText(getApplicationContext(), R.string.sucess, Toast.LENGTH_LONG).show();
                    Log.println(Log.DEBUG, "loginBtn.onClick", "Authenticated!");
                }
            }
        });
    }

    public void onClickLogin(View v) {
        if(usernameField.getText().toString().equals("GUEST") && passwordField.getText().toString().equals("PASS")) {
            Toast.makeText(getApplicationContext(), R.string.sucess, Toast.LENGTH_LONG).show();
        }
    }


}
