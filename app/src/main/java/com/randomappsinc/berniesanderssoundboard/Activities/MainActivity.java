package com.randomappsinc.berniesanderssoundboard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.berniesanderssoundboard.Adapters.SoundbitesAdapter;
import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.PreferencesManager;
import com.randomappsinc.berniesanderssoundboard.Utils.SoundbitesManager;
import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
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

        if (PreferencesManager.get().isFirstTimeUser()) {
            PreferencesManager.get().rememberWelcome();
            new MaterialDialog.Builder(this)
                    .title(R.string.welcome)
                    .content(R.string.ask_for_help)
                    .positiveText(android.R.string.yes)
                    .show();
        }
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

    @OnItemClick(R.id.soundbites)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        SoundbitesManager.get().playSoundbite(adapter.getItem(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.silence).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_volume_off)
                        .colorRes(R.color.white)
                        .actionBarSize());
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.silence:
                SoundbitesManager.get().silence();
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
