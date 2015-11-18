package com.randomappsinc.berniesanderssoundboard.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbiteManager {
    private List<String> soundbites;
    private static SoundbiteManager instance;

    public static SoundbiteManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized SoundbiteManager getSync() {
        if (instance == null) {
            instance = new SoundbiteManager();
        }
        return instance;
    }

    private SoundbiteManager() {
        List<String> soundbites = new ArrayList<>();
        soundbites.add("Healthcare");
        soundbites.add("Free skoo");
        this.soundbites = soundbites;
    }

    public List<String> getAllSoundbites() {
        return soundbites;
    }

    public List<String> getFavoritedSoundbite() {
        List<String> favoritedSoundbites = new ArrayList<>();
        favoritedSoundbites.addAll(PreferencesManager.get().getFavoritedSoundbites());
        return favoritedSoundbites;
    }

    public List<String> getSoundbiteMatches (String constraint) {
        return soundbites;
    }
}
