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
        soundbites.add("Break up the banks");
        soundbites.add("When millions of people stand up and fight");
        soundbites.add("Unusual political career");
        this.soundbites = soundbites;
    }

    public List<String> getAllSoundbites() {
        return soundbites;
    }

    private List<String> getFavoritedSoundbites() {
        List<String> favoritedSoundbites = new ArrayList<>();
        favoritedSoundbites.addAll(PreferencesManager.get().getFavoritedSoundbites());
        return favoritedSoundbites;
    }

    public List<String> getSoundbiteMatches(String searchTerm, boolean favoritesMode) {
        if (favoritesMode) {
            List<String> favoritedSoundbites = getFavoritedSoundbites();
            if (searchTerm.isEmpty()) {
                return getFavoritedSoundbites();
            }
            else {
                List<String> favoriteMatches = new ArrayList<>();
                for (String favorite : favoritedSoundbites) {
                    if (favorite.toLowerCase().contains(searchTerm.toLowerCase())) {
                        favoriteMatches.add(favorite);
                    }
                }
                return favoriteMatches;
            }
        }
        else {
            if (searchTerm.isEmpty()) {
                return soundbites;
            }
            else {
                List<String> matches = new ArrayList<>();
                for (String favorite : soundbites) {
                    if (favorite.toLowerCase().contains(searchTerm.toLowerCase())) {
                        matches.add(favorite);
                    }
                }
                return matches;
            }
        }
    }
}
