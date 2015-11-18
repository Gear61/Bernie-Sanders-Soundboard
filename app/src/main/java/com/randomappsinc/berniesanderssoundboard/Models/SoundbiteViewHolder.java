package com.randomappsinc.berniesanderssoundboard.Models;

import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.berniesanderssoundboard.R;
import com.randomappsinc.berniesanderssoundboard.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbiteViewHolder {
    @Bind(R.id.soundbite_name) public TextView soundbiteName;
    @Bind(R.id.favorite_icon) public IconTextView favoriteIcon;
    @BindColor(R.color.yellow) int yellow;
    @BindColor(R.color.dark_gray) int darkGray;

    @OnClick(R.id.favorite_icon)
    public void toggleFavorite(View view) {
        String soundbite = soundbiteName.getText().toString();
        if (PreferencesManager.get().isSoundbiteFavorited(soundbite)) {
            PreferencesManager.get().unfavoriteSoundbite(soundbite);
        }
        else {
            PreferencesManager.get().favoriteSoundbite(soundbite);
        }
        toggleFavoriteIcon();
    }

    public void toggleFavoriteIcon() {
        String soundbite = soundbiteName.getText().toString();
        if (PreferencesManager.get().isSoundbiteFavorited(soundbite)) {
            favoriteIcon.setTextColor(yellow);
        }
        else {
            favoriteIcon.setTextColor(darkGray);
        }
    }

    public SoundbiteViewHolder(View view) {
        ButterKnife.bind(this, view);
        toggleFavoriteIcon();
    }
}
