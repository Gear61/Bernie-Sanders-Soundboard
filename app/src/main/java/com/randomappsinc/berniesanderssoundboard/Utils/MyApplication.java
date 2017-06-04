package com.randomappsinc.berniesanderssoundboard.Utils;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;

import java.io.File;

/**
 * Created by Alex Chiou on 11/17/15.
 */
public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule())
               .with(new IoniconsModule());
        instance = this;

        createExternalDirectory();
    }

    public static void createExternalDirectory() {
        // Create external storage directory if it doesn't exist
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File ourDirectory = new File(android.os.Environment.getExternalStorageDirectory(), "BernieSoundboard");
            if (!ourDirectory.exists()) {
                ourDirectory.mkdirs();
            }
        }
    }

    public static MyApplication instance;

    public static MyApplication get() {
        return instance;
    }
}

