package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EasyMediumHard extends AppCompatActivity {
    Button beasy,bmedium,bhard;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_medium_hard);

        soundManager = SoundManager.getInstance(this);
        if (soundManager.isSoundOn()) {
            soundManager.playSound();
        }

        beasy=(Button) findViewById(R.id.b_easy);
        bmedium=(Button) findViewById(R.id.b_medium);
        bhard=(Button) findViewById(R.id.b_hard);

        beasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                db.deleteRecords();
                Intent game=new Intent(EasyMediumHard.this,GamePage.class);
                game.putExtra("Text",5);
                game.putExtra("Heads","Easy");
                startActivity(game);
            }
        });
        bmedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                db.deleteRecords();
                Intent game=new Intent(EasyMediumHard.this,GamePage.class);
                game.putExtra("Text",20);
                game.putExtra("Heads","Med");
                startActivity(game);
            }
        });
        bhard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                db.deleteRecords();
                Intent game=new Intent(EasyMediumHard.this,GamePage.class);
                game.putExtra("Text",35);
                game.putExtra("Heads","Hard");
                startActivity(game);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }

}