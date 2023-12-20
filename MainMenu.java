package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    private SoundManager soundManager;
    Button bnew,bcontinue,bsettings,bhistory;
    public static int[][]Original2DArray=new int[9][9];
    public static int[][]Grid2DArray=new int[9][9];
    public int[]Original1DArray=new int[81];
    public int[]Saved1DArray=new int[81];
    public String fetched_level,fetched_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        soundManager = SoundManager.getInstance(this);
        if (soundManager.isSoundOn()) {
            soundManager.playSound();
        }

        bnew=(Button) findViewById(R.id.b_new);
        bcontinue=(Button) findViewById(R.id.b_continue);
        bsettings=(Button) findViewById(R.id.b_settings);
        bhistory=(Button) findViewById(R.id.b_history);

        bnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, EasyMediumHard.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                startActivity(intent);
        }
    });



        bcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(MainMenu.this);
                if (dbHandler.checkEmpty()) {
                    Intent intent = new Intent(MainMenu.this, SorryNoGame.class);
                    startActivity(intent);
                } else {
                    GridValue lastSavedGrid = dbHandler.getLastSavedGrid();
                    if (lastSavedGrid != null) {
                        // Update the dummy_data and dummy_original members
                        GridValue currentGridValue = new GridValue(lastSavedGrid.getDummyData(), lastSavedGrid.getDummyOriginal(), lastSavedGrid.getLevel(), lastSavedGrid.getTime(), lastSavedGrid.getStatus());

                        /**Convert stringbuilder original_saved grid to integer 1D array; **/
                        StringBuilder oValues = currentGridValue.dummy_original; //Original answer grid
                        StringBuilder cValues = currentGridValue.dummy_data; //Original saved grid
                        fetched_level = currentGridValue.level;
                        fetched_time = currentGridValue.time;
                        System.out.println(fetched_level + "Fetched Data");
                        System.out.println(fetched_time + "Fetched Data");

                        String[] oStringArray = oValues.toString().split(",");
                        String[] cStringArray = cValues.toString().split(",");
                        //Convert string back to 1D array
                        for (int i = 0; i < oStringArray.length; i++) {
                            Original1DArray[i] = Integer.parseInt(oStringArray[i]);
                            Saved1DArray[i] = Integer.parseInt(cStringArray[i]);
                        }

                    } else {
                        Intent intent = new Intent(MainMenu.this, SorryNoGame.class);
                        startActivity(intent);
                    }


                    /**Convert 1D arrays to 2D Integer arrays **/
                    int index = 0; // Index for iterating through the 1D array
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            // Copy the value from the 1D array to the 2D array
                            if (index < 81) {
                                Original2DArray[i][j] = Original1DArray[index];
                                Grid2DArray[i][j] = Saved1DArray[index];
                                System.out.println(Grid2DArray[i][j]);
                                index++;
                            }
                        }

                    }

                    /** Generate the Grid **/
                    Intent game = new Intent(MainMenu.this, GamePage.class);
                    game.putExtra("XYZ_level", fetched_level);
                    game.putExtra("XYZ_time", fetched_time);
                    startActivity(game);
                }
            }
        });


//                DatabaseHandler dbHandler = new DatabaseHandler(MainMenu.this);
//                GridValue lastSavedGrid = dbHandler.getLastSavedGrid();
//                if (lastSavedGrid != null) {
//                    // Update the dummy_data and dummy_original members
//                    GridValue currentGridValue = new GridValue(lastSavedGrid.getDummyData(), lastSavedGrid.getDummyOriginal(), lastSavedGrid.getLevel(),lastSavedGrid.getTime(),lastSavedGrid.getStatus());
//
//                    /**Convert stringbuilder original_saved grid to integer 1D array; **/
//                    StringBuilder oValues=currentGridValue.dummy_original; //Original answer grid
//                    StringBuilder cValues=currentGridValue.dummy_data; //Original saved grid
//                    fetched_level=currentGridValue.level;
//                    fetched_time=currentGridValue.time;
//                    System.out.println( fetched_level+"Fetched Data");
//                    System.out.println( fetched_time+"Fetched Data");
//                    String[] oStringArray = oValues.toString().split(",");
//                    String[] cStringArray = cValues.toString().split(",");
////                    System.out.println("Easy");
//                    for (int i = 0; i < oStringArray.length; i++) {
//                        Original1DArray[i] = Integer.parseInt(oStringArray[i]);
//                        Saved1DArray[i] = Integer.parseInt(cStringArray[i]);
//                    }
//                } else {
//                    System.out.println("getLastSavedGrid() is NULL");
//                }
//
//
//                /**Convert 1D arrays to 2D Integer arrays **/
//                int index = 0; // Index for iterating through the 1D array
//                for (int i = 0; i < 9; i++) {
//                    for (int j = 0; j < 9; j++) {
//                        // Copy the value from the 1D array to the 2D array
//                        if (index < 81) {
//                            Original2DArray[i][j] = Original1DArray[index];
//                            Grid2DArray[i][j]=Saved1DArray[index];
//                            System.out.println(Grid2DArray[i][j]);
//                            index++;
//                        }
//                    }
//                }
//
//                /** Generate the Grid **/
//                Intent game=new Intent(MainMenu.this,GamePage.class);
//                game.putExtra("XYZ",fetched_level);
//                startActivity(game);
//            }



        bhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,HistoryPage.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                startActivity(intent);
            }
        });



        bsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,settingsPage.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                startActivity(intent);
            }
        });


}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }

}