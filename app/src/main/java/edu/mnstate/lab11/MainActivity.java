package edu.mnstate.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    EditText searchTxt, loadTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchTxt = findViewById(R.id.searchText);
        loadTxt = findViewById(R.id.loadText);
    }


    public void showContacts(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }
    public void showDial(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:707012223333"));
        startActivity(intent);
    }
    public void showSearch(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, searchTxt.getText().toString());
        startActivity(intent);
    }

    public void showLoad(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(loadTxt.getText().toString()));
        startActivity(intent);
    }

    public void showSpeak(View v) {
        Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
        startActivity(intent);
    }
}
