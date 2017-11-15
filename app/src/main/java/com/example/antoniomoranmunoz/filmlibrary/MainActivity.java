package com.example.antoniomoranmunoz.filmlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.antoniomoranmunoz.filmlibrary.Helper.LocaleHelper;

import java.util.Locale;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_TITLE = "extra.item.title";
    public static final String SECTION_LIBRARY = "Library";
    public static final String SECTION_CINEMA = "Cinema";
    public static final String SECTION_PEOPLE = "People";

    TextView libraryText;
    TextView cinemaText;
    TextView peopleText;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        RelativeLayout libraryBtn = (RelativeLayout)findViewById(R.id.libraryBtn);
        RelativeLayout cinemaBtn = (RelativeLayout)findViewById(R.id.cinemaBtn);
        RelativeLayout peopleBtn = (RelativeLayout)findViewById(R.id.peopleBtn);

        libraryText = (TextView)findViewById(R.id.libraryText);
        cinemaText = (TextView)findViewById(R.id.cinemaText);
        peopleText = (TextView)findViewById(R.id.peopleText);

        Paper.init(this);

        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language", "en");

        updateView((String)Paper.book().read("language"));


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

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);

        Resources resources = context.getResources();
        libraryText.setText(resources.getString(R.string.library));
        cinemaText.setText(resources.getString(R.string.cinema));
        peopleText.setText(resources.getString(R.string.people));
    }

    public void loadDetailSection(String sectionTitle) {
        Intent intent = new Intent(MainActivity.this, CinemaActivity.class);
        intent.putExtra(MainActivity.EXTRA_ITEM_TITLE, sectionTitle);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.language_en) {
            Paper.book().write("language", "en");
            updateView((String)Paper.book().read("language"));
        } else if(item.getItemId() == R.id.language_ja) {
            Paper.book().write("language", "ja");
            updateView((String)Paper.book().read("language"));
        } else if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
