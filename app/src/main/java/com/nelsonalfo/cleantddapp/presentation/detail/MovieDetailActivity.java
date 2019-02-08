package com.nelsonalfo.cleantddapp.presentation.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nelsonalfo.cleantddapp.R;

import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);
    }
}
