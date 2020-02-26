package edu.mnstate.lab12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DuaActivity extends AppCompatActivity {
    private static final String TAG = "DuaActivity";


    RecyclerView list;
    TextView key1, key2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);

        list = findViewById(R.id.list);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);

        Intent caller = getIntent();
        Bundle bundle = caller.getExtras();
        MyAdapter adapter = new MyAdapter(bundle.getStringArrayList("prodNames"));

        if(adapter.products == null) {
            adapter.products = new ArrayList<>();
        }
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));


        key1.setText(bundle.getString("key1", "No key1 passed"));
        key2.setText("" + bundle.getBoolean("key2", true));

    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        ArrayList<String> products;

        public MyAdapter(ArrayList<String> products) {

            super();
            this.products = products;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder: ");
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.template, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            Log.d(TAG, "onBindViewHolder: pos " + position);
            holder.text.setText(products.get(position));
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: " + this.products.size());
            return this.products.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView text;

            public Holder(@NonNull View itemView) {
                super(itemView);
                this.text = itemView.findViewById(R.id.prodName);
            }
        }
    }
}
