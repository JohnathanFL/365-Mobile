package edu.mnstate.jz1652ve.lab9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String KEY = "favoritedBooksKey";

    Book[] books = {
            new Book(R.drawable.c_logo, R.string.c_name, R.string.c_author),
            new Book(R.drawable.cpp_logo, R.string.cpp_name, R.string.cpp_author),
            new Book(R.drawable.d_logo, R.string.d_name, R.string.d_author),
            new Book(R.drawable.js_logo, R.string.js_name, R.string.js_author),
            new Book(R.drawable.python_logo, R.string.python_name, R.string.python_author),
            new Book(R.drawable.rust_logo, R.string.rust_name, R.string.rust_author)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GridView gridView = findViewById(R.id.gridView);
        BooksAdapter adapter = new BooksAdapter(this, books);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, pos, id) -> {
            Book book = this.books[pos];
            book.favorite = !book.favorite;
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final ArrayList<Integer> favoritedBookNames = new ArrayList<>();
        for (Book book : books) {
            if (book.favorite) {
                favoritedBookNames.add(book.name);
            }
        }

        // save that list to outState for later
        outState.putIntegerArrayList(KEY, favoritedBookNames);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Integer> favBookNames = savedInstanceState.getIntegerArrayList(KEY);
        for (int name : favBookNames)
            for(Book book : this.books)
                if (book.name == name) {
                    book.favorite = true;
                    break;
                }
    }
}
