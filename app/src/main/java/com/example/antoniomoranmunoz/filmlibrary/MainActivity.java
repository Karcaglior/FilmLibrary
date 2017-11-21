package com.example.antoniomoranmunoz.filmlibrary;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antoniomoranmunoz.filmlibrary.Helper.LocaleHelper;

import java.util.Locale;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_TITLE = "extra.item.title";
    public static final String SECTION_LIBRARY = "Library";
    public static final String SECTION_CINEMA = "Cinema";
    public static final String SECTION_PEOPLE = "People";

    RelativeLayout libraryBtn;
    RelativeLayout cinemaBtn;
    RelativeLayout peopleBtn;

    TextView libraryText;
    TextView cinemaText;
    TextView peopleText;
    Button loginBtn;
    Button createBtn;

    ConstraintLayout mainLayout;

    SeekBar seekBar;
    boolean success = false;

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

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(255);
        seekBar.setProgress(getBrightness());

        getPermission();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser && success) {
                    setBrightness(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(!success) {
                    Toast.makeText(MainActivity.this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        libraryBtn = (RelativeLayout)findViewById(R.id.libraryBtn);
        cinemaBtn = (RelativeLayout)findViewById(R.id.cinemaBtn);
        peopleBtn = (RelativeLayout)findViewById(R.id.peopleBtn);

        libraryText = (TextView)findViewById(R.id.libraryText);
        cinemaText = (TextView)findViewById(R.id.cinemaText);
        peopleText = (TextView)findViewById(R.id.peopleText);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        createBtn = (Button)findViewById(R.id.createBtn);

        mainLayout = (ConstraintLayout)findViewById(R.id.mainLayout);

        Paper.init(this);

        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language", "en");

        updateView((String)Paper.book().read("language"));


        libraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(intent);
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

    private void setBrightness(int brigthness) {
        if(brigthness<0) {
            brigthness = 0;
        } else if(brigthness > 255) {
            brigthness = 255;
        }

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brigthness);
    }

    private int getBrightness() {
        int brightness = 100;
        try{
            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        }catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return brightness;
    }

    private void getPermission() {
        boolean value;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(getApplicationContext());
            if(value) {
                success = true;
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 1000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if(value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);

        Resources resources = context.getResources();
        libraryText.setText(resources.getString(R.string.library));
        cinemaText.setText(resources.getString(R.string.cinema));
        peopleText.setText(resources.getString(R.string.people));
        loginBtn.setText(resources.getString(R.string.login));
        createBtn.setText(resources.getString(R.string.create));
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
        } else if(item.getItemId() == R.id.action_theme) {
            //Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
            int i = 0;
            if(i%2 == 0) {
                loginBtn.setBackgroundColor(Color.BLACK);
                loginBtn.setTextColor(Color.WHITE);
                createBtn.setBackgroundColor(Color.BLACK);
                createBtn.setTextColor(Color.WHITE);
                libraryBtn.setBackgroundColor(Color.BLACK);
                libraryText.setTextColor(Color.WHITE);
                cinemaBtn.setBackgroundColor(Color.BLACK);
                cinemaText.setTextColor(Color.WHITE);
                peopleBtn.setBackgroundColor(Color.BLACK);
                peopleText.setTextColor(Color.WHITE);
                i++;

            } else {
                loginBtn.setBackgroundColor(Color.WHITE);
                loginBtn.setTextColor(Color.BLACK);
                createBtn.setBackgroundColor(Color.WHITE);
                createBtn.setTextColor(Color.BLACK);
                libraryBtn.setBackgroundColor(Color.WHITE);
                libraryText.setTextColor(Color.BLACK);
                cinemaBtn.setBackgroundColor(Color.WHITE);
                cinemaText.setTextColor(Color.BLACK);
                peopleBtn.setBackgroundColor(Color.WHITE);
                peopleText.setTextColor(Color.BLACK);
                i++;

            }
            //startActivity(intent);
        }

        return true;
    }
}
