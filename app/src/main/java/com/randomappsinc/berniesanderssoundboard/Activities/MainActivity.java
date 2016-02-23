package com.randomappsinc.berniesanderssoundboard.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.randomappsinc.berniesanderssoundboard.Adapters.SoundbitesAdapter;
import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.FormUtils;
import com.randomappsinc.berniesanderssoundboard.Utils.MyApplication;
import com.randomappsinc.berniesanderssoundboard.Utils.PreferencesManager;
import com.randomappsinc.berniesanderssoundboard.Utils.SoundbitesManager;
import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnTextChanged;

public class MainActivity extends StandardActivity {
    public static final int WRITE_EXTERNAL_CODE = 1;

    @Bind(R.id.parent) View parent;
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
                    .content(R.string.intro_text)
                    .positiveText(android.R.string.yes)
                    .show();
        }
    }

    @OnClick(R.id.clear_search)
    public void clearSearch() {
        searchInput.setText("");
    }

    @OnClick(R.id.favorites_toggle)
    public void toggleFavorites() {
        adapter.filterSoundbites(searchInput.getText().toString(), favoritesToggle.isChecked());
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        adapter.filterSoundbites(s.toString(), favoritesToggle.isChecked());
    }

    @OnItemClick(R.id.soundbites)
    public void onItemClick(int position) {
        SoundbitesManager.get().playSoundbite(adapter.getItem(position));
    }

    @OnItemLongClick(R.id.soundbites)
    public boolean setNewTone(int position) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(this)) {
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
        else if (!permissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            processMissingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_CODE);
        }
        else {
            showToneDialog(adapter.getItem(position));
        }
        return true;
    }

    private boolean permissionGranted(String permission) {
        int currentStatus = ContextCompat.checkSelfPermission(this, permission);
        return currentStatus == PackageManager.PERMISSION_GRANTED;
    }

    private void processMissingPermission(final String permission, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.write_external_explanation)
                    .positiveText(android.R.string.yes)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            requestPermission(permission, requestCode);
                        }
                    })
                    .show();
        }
        else {
            requestPermission(permission, requestCode);
        }
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyApplication.createExternalDirectory();
                }
                else {
                    processMissingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_CODE);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showToneDialog(final String soundbite) {
        new MaterialDialog.Builder(this)
                .title(R.string.set_as)
                .items(R.array.tone_types)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                SoundbitesManager.get().setTone(soundbite, SoundbitesManager.RINGTONE_TYPE);
                                showSnackbar(getString(R.string.ringtone_success));
                                break;
                            case 1:
                                SoundbitesManager.get().setTone(soundbite, SoundbitesManager.NOTIFICATION_TONE_TYPE);
                                showSnackbar(getString(R.string.notification_tone_success));
                        }
                        return true;
                    }
                })
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .show();
    }

    public void showSnackbar(String message) {
        FormUtils.showSnackbar(parent, message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.random_soundbite).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_random)
                        .colorRes(R.color.white)
                        .actionBarSize());
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
            case R.id.random_soundbite:
                SoundbitesManager.get().playRandomSoundbite();
                return true;
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
