package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.randomappsinc.berniesanderssoundboard.Adapters.SourcesAdapter;
import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 11/17/15.
 */
public class SourcesActivity extends StandardActivity {
    @Bind(R.id.sources) ListView sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sources.setAdapter(new SourcesAdapter(this));
    }

    @OnItemClick(R.id.sources)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String sourceUrl = getResources().getStringArray(R.array.sources_links)[position];
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl)));
    }
}
