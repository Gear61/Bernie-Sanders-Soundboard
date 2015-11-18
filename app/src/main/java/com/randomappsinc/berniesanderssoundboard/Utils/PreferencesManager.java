package com.randomappsinc.berniesanderssoundboard.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class PreferencesManager {
    private static final String FAVORITED_KEY = "favorited";

    private SharedPreferences prefs;
    private static PreferencesManager instance;

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {
        Context context = MyApplication.get().getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void setFavorites(Set<String> favorites) {
        prefs.edit().remove(FAVORITED_KEY).apply();
        prefs.edit().putStringSet(FAVORITED_KEY, favorites).apply();
    }

    private Set<String> getFavorites() {
        return prefs.getStringSet(FAVORITED_KEY, new HashSet<String>());
    }

    public boolean isSoundbiteFavorited(String soundbite) {
        return getFavorites().contains(soundbite);
    }

    public void favoriteSoundbite(String soundbite) {
        Set<String> favorites = getFavorites();
        favorites.add(soundbite);
        setFavorites(favorites);
    }

    public void unfavoriteSoundbite(String soundbite) {
        Set<String> favorites = getFavorites();
        favorites.remove(soundbite);
        setFavorites(favorites);
    }
}
