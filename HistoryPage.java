package com.example.sudokuapp;


import androidx.appcompat.app.AppCompatActivity;


import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HistoryPage extends AppCompatActivity {
    private SoundManager soundManager;
    TextView easy,medium,hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        soundManager = SoundManager.getInstance(this);
        if (soundManager.isSoundOn()) {
            soundManager.playSound();
        }

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

        loadFragment(new EasyHistory("Easy"));
        highlightOption(easy);


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EasyHistory("Easy"));
                highlightOption(easy);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EasyHistory("Med"));
                highlightOption(medium);

            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EasyHistory("Hard"));
                highlightOption(hard);
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
    private void highlightOption(TextView textView) {
        int normalTextColor = ContextCompat.getColor(this, R.color.normalText);
        int highlightedTextColor = ContextCompat.getColor(this, R.color.highlightedText);
        easy.setTextColor(normalTextColor);
        medium.setTextColor(normalTextColor);
        hard.setTextColor(normalTextColor);
        textView.setTextColor(highlightedTextColor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }


}
