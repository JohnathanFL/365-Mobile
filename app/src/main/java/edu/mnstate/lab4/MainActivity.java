package edu.mnstate.lab4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView lblGreeting;
    private EditText txtFirstName;
    private Button btnGreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblGreeting = findViewById(R.id.lblGreeting);
        txtFirstName = findViewById(R.id.txtFirstName);
        btnGreet = findViewById(R.id.btnGreet);

        /*btnGreet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tmp = getResources().getString(R.string.hello_there);
                lblGreeting.setText(tmp + " " + txtFirstName.getText());
            }

        });*/
        btnGreet.setOnClickListener(v -> {
            String tmp = getResources().getString(R.string.hello_there);
            lblGreeting.setText(tmp + " " + txtFirstName.getText());
        });

    }

    public void showGreeting(View v) {
//        String tmp = getResources().getString(R.string.hello_there);
//        lblGreeting.setText(tmp + " " + txtFirstName.getText());
    }
}
