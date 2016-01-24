package com.randomappsinc.berniesanderssoundboard.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public class SoundbitesManager {
    public static final String SOUNDBITES_PATH = "Soundbites";
    public static final String RINGTONE_TYPE = "Bernie Sanders Ringtone";
    public static final String NOTIFICATION_TONE_TYPE = "Bernie Sanders Notification Tone";
    public static final String RINGTONE_PATH = "BernieSandersRingtone.ogg";
    public static final String NOTIFICATION_TONE_PATH = "BernieSandersNotificationTone.ogg";

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

    public void setTone(String soundbite, String type) {
        String oggName = type.equals(RINGTONE_TYPE) ? RINGTONE_PATH : NOTIFICATION_TONE_PATH;
        String assetsFilePath = SOUNDBITES_PATH + "/" + soundbite + ".mp3";

        File newSoundFile = new File(Environment.getExternalStorageDirectory().getPath() + "/BernieSoundboard", oggName);
        if (newSoundFile.exists()) {
            newSoundFile.delete();
        }
        // Try to get contents of .mp3 file to copy over
        InputStream fis;
        try {
            fis = context.getAssets().open(assetsFilePath);
        }
        catch (IOException e) {
            return;
        }

        try {
            byte[] readData = new byte[1024];
            FileOutputStream fos = new FileOutputStream(newSoundFile);
            int i = fis.read(readData);
            while (i != -1) {
                fos.write(readData, 0, i);
                i = fis.read(readData);
            }
            fos.close();
        }
        catch (IOException io) {
            return;
        }

        // Set up MediaPlayer to get file duration in ms
        MediaPlayer mp = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/BernieSoundboard/" + oggName));

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, newSoundFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, type);
        values.put(MediaStore.MediaColumns.SIZE, newSoundFile.length());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/ogg");
        values.put(MediaStore.Audio.Media.ARTIST, "Bernie Sanders");
        values.put(MediaStore.Audio.Media.DURATION, mp.getDuration());
        values.put(MediaStore.Audio.Media.IS_RINGTONE, type.equals(RINGTONE_TYPE));
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, type.equals(NOTIFICATION_TONE_TYPE));
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        // Remove all previous incarnations of the Bernie Sanders tone
        String where = MediaStore.MediaColumns.TITLE + " = ?";
        String[] args = new String[] {type};
        context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, where, args);

        // Insert new tone into the database
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(newSoundFile.getAbsolutePath());
        Uri newUri = context.getContentResolver().insert(uri, values);
        if (type.equals(RINGTONE_TYPE)) {
            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
        }
        else if (type.equals(NOTIFICATION_TONE_TYPE)) {
            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, newUri);
        }
    }
}
