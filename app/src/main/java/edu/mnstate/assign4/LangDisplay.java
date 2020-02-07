package edu.mnstate.assign4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LangDisplay extends AppCompatActivity {
    ImageView langImg;
    TextView langName, langDesc;

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
