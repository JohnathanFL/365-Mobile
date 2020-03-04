package edu.mnstate.lab18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {

    EmpHelper empHelper;
    private SQLiteDatabase db;
    EditText empName;

    public static void message(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empName = findViewById(R.id.name);
        empHelper = new EmpHelper(this);


    }

    public void addEmp(View v) {
        String ename = empName.getText().toString();
        db = empHelper.getWritableDatabase();
        ContentValues cVals = new ContentValues();

        cVals.put(empHelper.EMPNAME, ename);
        long id = db.insert(empHelper.TABLE_NAME, null, cVals);
        if(id > 0)
            MainActivity.message(this, "Success!");
        else
            MainActivity.message(this, "No go.");
    }
}
