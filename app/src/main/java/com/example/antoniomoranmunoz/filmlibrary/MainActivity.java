package com.example.antoniomoranmunoz.filmlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    public static final String SECTION_LIBRARY = "Library";
    public static final String SECTION_CINEMA = "Cinema";
    public static final String SECTION_PEOPLE = "People";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        RelativeLayout libraryBtn = (RelativeLayout)findViewById(R.id.libraryBtn);
        RelativeLayout cinemaBtn = (RelativeLayout)findViewById(R.id.cinemaBtn);
        RelativeLayout peopleBtn = (RelativeLayout)findViewById(R.id.peopleBtn);

        libraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailSection(MainActivity.SECTION_LIBRARY);
            }
        });

        cinemaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailSection(MainActivity.SECTION_CINEMA);
            }
        });

        peopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailSection(MainActivity.SECTION_PEOPLE);
            }
        });
    }

    public void loadDetailSection(String sectionTitle) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
