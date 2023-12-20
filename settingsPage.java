//package com.example.sudokuapp;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//public class settingsPage extends AppCompatActivity {
//
//    private SoundManager soundManager;
//        Switch darkmode;
//        SharedPreferences sharedPreferences;
//        Switch musicSwitch;
//        MediaPlayer mediaPlayer;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_settings_page);
//
//            soundManager = SoundManager.getInstance(this);
//            if (soundManager.isSoundOn()) {
//                soundManager.playSound();
//            }
//            ImageView bBack=findViewById(R.id.back);
//            bBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent backMainPage=new Intent(settingsPage.this,MainMenu.class);
//                    overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_right);
//                    startActivity(backMainPage);
//                }
//            });
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//            // Initialize ActionBar
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.hide();
//            }
//
//            darkmode=findViewById(R.id.darkmode);
//
//            darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
//                    if(b)
//                    {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    }
//                    else{
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    }
//                }
//            });
//
//
//            TextView instructionTextView = findViewById(R.id.howtoplay);
//            instructionTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Open the InstructionActivity when the TextView is clicked
//                    Intent intent = new Intent(settingsPage.this, Instruction.class);
//                    startActivity(intent);
//                }
//            });
//
//            soundManager=SoundManager.getInstance(this);
//            musicSwitch = findViewById(R.id.soundon);
//            mediaPlayer = MediaPlayer.create(this, R.raw.music);
//
//            musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        // The Switch is on; start playing music
//
//                    playMusic();
//                    } else {
//                        // The Switch is off; stop playing music
//                        stopMusic();
//                    }
//                }
//            });
//        }
//
//        private void playMusic() {
//            if (!mediaPlayer.isPlaying()) {
//                mediaPlayer.start();
//            }
//        }
//
//        private void stopMusic() {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
//                mediaPlayer.seekTo(0); // Rewind to the beginning
//            }
//        }
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            mediaPlayer.release(); // Release the MediaPlayer resources
//        }
//
//
//
//
//    }

package com.example.sudokuapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sudokuapp.view.sudokugrid.GameGrid;
import com.example.sudokuapp.view.sudokugrid.SudokuCell;

public class settingsPage extends AppCompatActivity {

    private Switch timerSwitch;
    private Switch darkmode;

    SharedPreferences sharedPreferences,dSP;

    private SoundManager soundManager;
    private Switch soundEffectSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        ImageView bBack=findViewById(R.id.back);


        timerSwitch = findViewById(R.id.timeron);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean timerState = sharedPreferences.getBoolean("timerState", false);
        timerSwitch.setChecked(timerState);

        timerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("timerState", isChecked);
            editor.apply();
        });


        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMainPage=new Intent(settingsPage.this,MainMenu.class);
                overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_right);
                startActivity(backMainPage);
            }
        });
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        darkmode=findViewById(R.id.darkmode);

        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SudokuCell gg=new SudokuCell(getApplicationContext());


                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });


        TextView instructionTextView = findViewById(R.id.howtoplay);
        instructionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the InstructionActivity when the TextView is clicked
                Intent intent = new Intent(settingsPage.this, Instruction.class);
                startActivity(intent);
            }
        });


        soundManager = SoundManager.getInstance(this);
        soundEffectSwitch = findViewById(R.id.soundon); // Use the ID of your sound effect switch
        soundEffectSwitch.setChecked(soundManager.isSoundOn());

        soundEffectSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            soundManager.setSoundOn(isChecked);
            if (isChecked) {
                soundManager.playSound();
            } else {
                soundManager.stopSound();
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }



}