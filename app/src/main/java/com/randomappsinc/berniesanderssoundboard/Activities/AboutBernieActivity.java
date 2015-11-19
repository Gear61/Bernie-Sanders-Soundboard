package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.randomappsinc.berniesanderssoundboard.Adapters.FollowAdapter;
import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 11/18/15.
 */
public class AboutBernieActivity extends StandardActivity {
    @Bind(R.id.about_bernie) ExpandableTextView aboutBernie;
    @Bind(R.id.links) ListView links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_bernie);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String summary = getString(R.string.about_bernie_summary);
        String source = getString(R.string.source);
        String feelTheBernLink = "<a href=\\\"http://feelthebern.org/who-is-bernie-sanders/\\\">FeelTheBern.org</a>";
        Spanned bernieSummaryFull = Html.fromHtml(summary + "<br><br>" + source + feelTheBernLink);

        aboutBernie.setText(bernieSummaryFull);
        links.setAdapter(new FollowAdapter(this));
    }

    @OnItemClick(R.id.links)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String followUrl = getResources().getStringArray(R.array.follow_links)[position];
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(followUrl)));
    }
}
