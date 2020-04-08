package edu.mnstate.jz1652ve.lab23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        try {
            InputStream is = this.getAssets().open("studentdata.json");

            // My C-senses are tingling
            int size = is.available();
            Log.d(TAG, "onCreate: Size: " + size);
            byte[] buff = new byte[size];
            is.read(buff);
            is.close();

            String jsonData = new String(buff, StandardCharsets.UTF_8);
            Toast.makeText(this, "jsonData" + jsonData, Toast.LENGTH_SHORT).show();
            JSONArray ar = new JSONObject(jsonData).getJSONArray("studentList");


            String eax = "";
            for(int i = 0; i < ar.length(); i++) {
                JSONObject stu = (JSONObject)ar.get(i);

                int id = stu.getInt("id");
                String name = stu.getString("name");
                eax += id + " " + name + "\n";
                Toast.makeText(this, "TMP: " + eax, Toast.LENGTH_LONG).show();
            }

            textView.setText(eax);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
