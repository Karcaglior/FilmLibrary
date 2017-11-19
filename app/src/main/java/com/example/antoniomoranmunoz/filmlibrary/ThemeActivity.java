package com.example.antoniomoranmunoz.filmlibrary;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

public class ThemeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        RelativeLayout sunLayout = (RelativeLayout)findViewById(R.id.sunLayout);
        RelativeLayout moonLayout = (RelativeLayout)findViewById(R.id.moonLayout);
        final ConstraintLayout constraintTheme = (ConstraintLayout)findViewById(R.id.constraintTheme);

        sunLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.SunTheme);
                constraintTheme.setBackgroundColor(Color.parseColor("#000000"));
                setContentView(R.layout.activity_theme);
                finish();

            }
        });

        moonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.MoonTheme);
                constraintTheme.setBackgroundColor(Color.parseColor("#ffffff"));
                setContentView(R.layout.activity_theme);
                finish();

            }
        });

    }


}
