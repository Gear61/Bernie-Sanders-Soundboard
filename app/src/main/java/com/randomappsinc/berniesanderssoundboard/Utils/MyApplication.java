package com.randomappsinc.berniesanderssoundboard.Utils;

import android.app.Application;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.randomappsinc.berniesanderssoundboard.R;

import java.io.File;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
        instance = this;

        // Create external storage directory if it doesn't exist
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File Dir = new File(android.os.Environment.getExternalStorageDirectory(), "BernieSoundboard");
            if (!Dir.exists()) {
                if (!Dir.mkdirs()) {
                    Toast.makeText(getApplicationContext(), R.string.external_fail, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static MyApplication instance;

    public static MyApplication get() {
        return instance;
    }
}

