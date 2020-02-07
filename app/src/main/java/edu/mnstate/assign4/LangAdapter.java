package edu.mnstate.assign4;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


class RowView extends FrameLayout {
    private static final String TAG = "RowView";

    ImageView img;
    TextView name, desc;

    public RowView(@NonNull Context context) {
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(context, R.layout.item_template, this);
        img = findViewById(R.id.langImg);
        name = findViewById(R.id.langName);
        desc = findViewById(R.id.langDesc);
    }

    // Even in Java I can't escape the realities of pointer arithmetic
    public void setup(int id, MainActivity us) {

        TypedArray ids = getResources().obtainTypedArray(R.array.langs);
        Log.d(TAG, "setup");
        // ids is packed as groups of 3 integer ids. Thus we need to mul by 3 to get the start
        // of our little slice of it
        // There's probably a better way of doing this, but at the moment, I just want to get
        // something working.
        id *= 3;

        Drawable img = ids.getDrawable(id + 0);
        String name = ids.getString(id + 1);
        String desc = ids.getString(id + 2);


        this.img.setImageDrawable(img);
        this.name.setText(name);
        this.desc.setText(desc);

        // Because java's real stupid about passing things into lambdas
        Integer passableId = id;
        setOnClickListener( v -> {
            Log.d(TAG, "setup: clicked " + this.toString());
            Intent mover = new Intent(us.getBaseContext(), LangDisplay.class);
            mover.putExtra("image", passableId + 0);
            mover.putExtra("name", passableId + 1);
            mover.putExtra("desc", passableId + 2);
            us.startActivity(mover);
        });
    }
}


public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {
    // Only needed because LangAdapter can't use getResources directly... for some reason....
    int size;
    MainActivity us;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new RowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.row.setup(position, this.us);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    void setup(int size, MainActivity us) {
        this.size = size;
        this.us = us;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RowView row;
        ViewHolder(@NonNull RowView itemView) {
            super(itemView);
            row = itemView;
        }
    }
}
