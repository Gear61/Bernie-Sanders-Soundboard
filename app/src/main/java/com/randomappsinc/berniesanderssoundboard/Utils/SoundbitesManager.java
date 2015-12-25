package com.randomappsinc.berniesanderssoundboard.Utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbitesManager {
    public static final String SOUNDBITES_PATH = "Soundbites";

    private List<String> soundbites;
    private MediaPlayer player;
    private Context context;
    private static SoundbitesManager instance;

    public static SoundbitesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized SoundbitesManager getSync() {
        if (instance == null) {
            instance = new SoundbitesManager();
        }
        return instance;
    }

    private SoundbitesManager() {
        this.context = MyApplication.get().getApplicationContext();
        List<String> soundbites = new ArrayList<>();
        try {
            String[] files = context.getAssets().list(SOUNDBITES_PATH);
            for (String file : files) {
                soundbites.add(file.substring(0, file.length() - 4));
            }
        }
        catch(IOException ignored) {}
        this.soundbites = soundbites;
        this.player = new MediaPlayer();
    }

    public List<String> getAllSoundbites() {
        return soundbites;
    }

    private List<String> getFavoritedSoundbites() {
        List<String> favoritedSoundbites = new ArrayList<>();
        favoritedSoundbites.addAll(PreferencesManager.get().getFavoritedSoundbites());
        Collections.sort(favoritedSoundbites);
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

    public void playSoundbite(String soundbite) {
        player.reset();
        String filePath = SOUNDBITES_PATH + "/" + soundbite + ".mp3";
        try {
            AssetFileDescriptor fileDescriptor = context.getAssets().openFd(filePath);
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            player.prepare();
            player.start();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void silence() {
        player.reset();
    }

    public void playRandomSoundbite() {
        Random random = new Random();
        int soundbiteIndex = random.nextInt(soundbites.size());
        playSoundbite(soundbites.get(soundbiteIndex));
    }
}
