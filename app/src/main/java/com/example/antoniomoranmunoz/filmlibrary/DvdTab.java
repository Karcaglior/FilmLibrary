package com.example.antoniomoranmunoz.filmlibrary;

/**
 * Created by antoniomoranmunoz on 21/11/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DvdTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dvd_tab, container, false);
        return rootView;
    }

}
