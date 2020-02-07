package edu.mnstate.assign4;

import android.content.Context;
import android.content.res.TypedArray;
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
    public void setup(int id) {
        TypedArray ids = getResources().obtainTypedArray(R.array.langs);
        Log.d(TAG, "setup");
        // ids is packed as groups of 3 integer ids. Thus we need to mul by 3 to get the start
        // of our little slice of it
        // There's probably a better way of doing this, but at the moment, I just want to get
        // something working.
        id *= 3;

        Log.d(TAG, "setup: Desc was " + ids.getString(id + 2));


        img.setImageDrawable(ids.getDrawable(id + 0));
        name.setText(ids.getString(id + 1));
        desc.setText(ids.getString(id + 2));
    }
}


public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {
    // Only needed because LangAdapter can't use getResources directly... for some reason....
    int size;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new RowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.row.setup(position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    void setup(int size) {
        this.size = size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RowView row;
        ViewHolder(@NonNull RowView itemView) {
            super(itemView);
            row = itemView;
        }
    }
}
