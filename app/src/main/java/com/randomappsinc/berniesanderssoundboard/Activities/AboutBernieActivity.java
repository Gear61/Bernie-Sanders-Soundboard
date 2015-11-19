package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.randomappsinc.berniesanderssoundboard.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 11/18/15.
 */
public class AboutBernieActivity extends StandardActivity {
    @Bind(R.id.about_bernie) ExpandableTextView aboutBernie;

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
    }

    @OnClick({R.id.website, R.id.reddit, R.id.facebook, R.id.twitter, R.id.youtube, R.id.google_plus})
    public void openLink(View view) {
        String link = null;
        switch (view.getId()) {
            case R.id.website:
                link = getString(R.string.website_link);
                break;
            case R.id.reddit:
                link = getString(R.string.reddit_link);
                break;
            case R.id.facebook:
                link = getString(R.string.facebook_link);
                break;
            case R.id.twitter:
                link = getString(R.string.twitter_link);
                break;
            case R.id.youtube:
                link = getString(R.string.youtube_link);
                break;
            case R.id.google_plus:
                link = getString(R.string.google_plus_link);
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }
}
