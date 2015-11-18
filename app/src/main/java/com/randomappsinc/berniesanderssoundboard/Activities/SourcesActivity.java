package com.randomappsinc.berniesanderssoundboard.Activities;

import android.os.Bundle;

import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 11/17/15.
 */
public class SourcesActivity extends StandardActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
