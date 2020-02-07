/*
 * Simple program which displays a list of languages, each with an image, name, and description.
 *  Each list item may be clicked to go to a page devoted to that language.
 *
 *  Author: Johnathan Lee
 *  Due: 02/07/2020
 *
 */

package edu.mnstate.assign4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Displays a closer view of a single language.
 * MUST be entered through an intent that provides "image", "name", and "desc" integers, each of
 * which must correspond to a resource in this project.
 */
public class LangDisplay extends AppCompatActivity {
    /// The lang's logo
    ImageView langImg;

    TextView
            /// The lang's name
            langName,
            /// The lang's description
            langDesc;

    /**
     * Takes the resourceIDs from the intent that got us here and uses them to set the lang stuff.
     * @param savedInstanceState Android stuff.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_display);

        langImg = findViewById(R.id.langImg);
        langName = findViewById(R.id.langName);
        langDesc = findViewById(R.id.langDesc);

        Intent intent = getIntent();

        TypedArray ar = getResources().obtainTypedArray(R.array.langs);
        langImg.setImageDrawable(ar.getDrawable(intent.getIntExtra("image", -1)));
        langName.setText(ar.getString(intent.getIntExtra("name", -1)));
        langDesc.setText(ar.getString(intent.getIntExtra("desc", -1)));
    }
}
