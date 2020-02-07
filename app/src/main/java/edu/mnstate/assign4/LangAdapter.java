/*
 * Simple program which displays a list of languages, each with an image, name, and description.
 *  Each list item may be clicked to go to a page devoted to that language.
 *
 *  Author: Johnathan Lee
 *  Due: 02/07/2020
 *
 */

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

/**
 * Each row itself
 */
class RowView extends FrameLayout {
    private static final String TAG = "RowView";

    /// The logo of that lang
    ImageView img;
    TextView
            /// The name of the lang
            name,
            /// The description of the lang
            desc;

    /**
     * Setup the handles to all layout elements
     * @param context Android stuff
     */
    public RowView(@NonNull Context context) {
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(context, R.layout.item_template, this);
        img = findViewById(R.id.langImg);
        name = findViewById(R.id.langName);
        desc = findViewById(R.id.langDesc);
    }


    /**
     * Set text/images for the current position.
     * @param id The position of the current element in the entire list
     * @param us The activity we're in. Needed for getting context and resources
     */
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

/**
 * Recycler adapter for a typed array of groups of (img, name, desc).
 * Gets its data from R.arrays.langs.
 */
public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {
    /// The number of elements in R.arrays.langs
    /// Only needed because LangAdapter can't use getResources directly... for some reason....
    int size;
    /// Needed to pass on to the RowView
    MainActivity us;


    /**
     * Glorified ViewHolder constructor.
     * @param parent Android stuff
     * @param viewType Mooooore android stuff
     * @return A new ViewHolder. That's it.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new RowView(parent.getContext()));
    }

    /**
     * Setup a new row
     * @param holder The holder.... for the view...
     * @param position The absolute position we're at in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.row.setup(position, this.us);
    }

    /**
     * @return length of R.arrays.langs / 3
     */
    @Override
    public int getItemCount() {
        return size;
    }

    /**
     * Essentially a secondary constructor since we can't directly pass things in the other one.
     * @param size R.arrays.langs / 3
     * @param us The main activity
     */
    void setup(int size, MainActivity us) {
        this.size = size;
        this.us = us;
    }

    /**
     * Holds each row
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        RowView row;
        ViewHolder(@NonNull RowView itemView) {
            super(itemView);
            row = itemView;
        }
    }
}
