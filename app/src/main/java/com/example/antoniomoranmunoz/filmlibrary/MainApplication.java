package com.example.antoniomoranmunoz.filmlibrary;

import android.app.Application;
import android.content.Context;

import com.example.antoniomoranmunoz.filmlibrary.Helper.LocaleHelper;

/**
 * Created by antoniomoranmunoz on 15/11/17.
 */

public class MainApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
