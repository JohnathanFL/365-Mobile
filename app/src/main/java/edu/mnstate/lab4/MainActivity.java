package edu.mnstate.lab4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView lblGreeting;
    private EditText txtFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblGreeting = (TextView)findViewById(R.id.lblGreeting);
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
    }

    public void showGreeting(View v) {
        String tmp = getResources().getString(R.string.hello_there);
        lblGreeting.setText(tmp + " " + txtFirstName.getText());
    }
}
