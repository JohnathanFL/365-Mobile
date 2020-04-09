package edu.mnstate.jz1652ve.lab20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "emp.db", TABLE_NAME = "empTable";

    public DatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table empTable (id INTEGER PRIMARY KEY, name TEXT, salary TEXT);");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS empTable;");
        onCreate(db);
    }


    public boolean insert(String s, String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("name", s);
        cvals.put("salary", s1);
        db.insert(TABLE_NAME, null, cvals);
        return true;
    }

    public ArrayList<String> getEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arList = new ArrayList<>();

        Cursor result = db.rawQuery("select * from empTable;", null);
        // moveToNext already only returns true if it was able to move to something, so why an extra 2 lines?
        while (result.moveToNext()) {
            String nameStr = result.getString(result.getColumnIndex("name"));
            String salStr = result.getString(result.getColumnIndex("salary"));
            arList.add(nameStr + " " + salStr);
        }

        return arList;
    }
}

public class MainActivity extends AppCompatActivity {

    EditText name, salary;
    private ListView listView;
    ArrayList<String> empList;
    ArrayAdapter arrayAdapter;
    DatabaseHelper empHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empHelper = new DatabaseHelper(this);
        empList = empHelper.getEmployees();

        name = findViewById(R.id.name);
        salary = findViewById(R.id.salary);
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, empList);
        listView.setAdapter(arrayAdapter);
    }

    public void refreshEmps(View view) {
        empList.clear();
        empList.addAll(empHelper.getEmployees());
        arrayAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    public void addEmp(View view) {
        String nameStr = name.getText().toString(),
                salStr = salary.getText().toString();

        if(!nameStr.isEmpty() && !salStr.isEmpty()) {
            if(empHelper.insert(nameStr, salStr)) {
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
                name.setText("");
                salary.setText("");
            } else {
                Toast.makeText(this, "NOT Inserted", Toast.LENGTH_SHORT).show();
            }
        } else {
            name.setError("Enter Name");
            salary.setError("Enter Salary");
        }
    }
}
