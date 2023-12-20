package com.example.sudokuapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class SoundManager {
    private static final String PREF_NAME = "SoundPrefs";
    private static final String PREF_KEY_SOUND_ON = "soundOn";

    private static SoundManager instance;
    private final MediaPlayer mediaPlayer;
    private final SharedPreferences preferences;

    private SoundManager(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
        return instance;
    }

    public void playSound() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopSound() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    public boolean isSoundOn() {
        return preferences.getBoolean(PREF_KEY_SOUND_ON, false);
    }

    public void setSoundOn(boolean isSoundOn) {
        preferences.edit().putBoolean(PREF_KEY_SOUND_ON, isSoundOn).apply();
    }

    public void release() {
        mediaPlayer.release();
        instance = null;
    }
}
