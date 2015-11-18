package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.berniesanderssoundboard.Adapters.SoundbitesAdapter;
import com.randomappsinc.berniesanderssoundboard.R;
import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends StandardActivity {
    @Bind(R.id.search_input) EditText searchInput;
    @Bind(R.id.soundbites) ListView soundbites;
    @Bind(R.id.no_soundbites) View noSoundbites;
    @Bind(R.id.favorites_toggle) CheckBox favoritesToggle;

    private SoundbitesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new SoundbitesAdapter(this, noSoundbites);
        soundbites.setAdapter(adapter);
    }

    @OnClick(R.id.clear_search)
    public void clearSearch(View view) {
        searchInput.setText("");
    }

    @OnClick(R.id.favorites_toggle)
    public void toggleFavorites(View view) {
        adapter.filterSoundbites(searchInput.getText().toString(), favoritesToggle.isChecked());
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        adapter.filterSoundbites(s.toString(), favoritesToggle.isChecked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
