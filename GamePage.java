package com.example.sudokuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudokuapp.view.sudokugrid.GameGrid;

public class GamePage extends AppCompatActivity {

    private SoundManager soundManager;
    private CountDownTimer countDownTimer;
    SharedPreferences sharedPreferences;
    int num;
    public static String Head,xyz_l,xyz_t;
    public static String time;
    public static int [][] original_suduko=new int[9][9];
    public static int [] original_FlatArray=new int[81];
    TextView timerTextView;

    ImageView settingHomes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        soundManager = SoundManager.getInstance(this);
        if (soundManager.isSoundOn()) {
            soundManager.playSound();
        }

        TextView T = (TextView) findViewById(R.id.Head);
        settingHomes = findViewById(R.id.settingHome);
        Intent getButton = getIntent();

        /**For heading as easy medium and hard**/
        Head = getButton.getStringExtra("Heads");
        xyz_l=getButton.getStringExtra("XYZ_level");//When continue pressed EMH
        System.out.println(xyz_l);
        xyz_t=getButton.getStringExtra("XYZ_time");//When continue pressed TIME
        System.out.println(xyz_t);

        /**TIME**/
        timerTextView=findViewById(R.id.timertext);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean timerState = sharedPreferences.getBoolean("timerState", false);
        if (timerState) {
            if(Head!=null) {
                T.setText(Head);
                num = getButton.getIntExtra("Text", 0);
                startCountdownTimer(1 * 60 * 1000); // 10 minutes in milliseconds
                GameEngine.getInstance().createGrid(this, num);
            }

            //For continue button to work
            if(xyz_l!=null & xyz_t!=null){
                T.setText(xyz_l);
                String[] parts = xyz_t.split(":");
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);
                long totalMilliseconds = (minutes * 60 + seconds) * 1000;
                startCountdownTimer(totalMilliseconds);
                GameEngine.getInstance().createGrid(this);
            }
        }


        //Go to settings page
        settingHomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(GamePage.this, settingsPage.class);
                startActivity(setting);
            }

        });
    }

    private void startCountdownTimer(long milliseconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(milliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update the timerTextView with the remaining time in MM:SS format
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                time = String.format("%02d:%02d", minutes, seconds);
                timerTextView.setText(time);
            }

            public void onFinish() {
                timerTextView.setText("00:00");
                Toast.makeText(getApplicationContext(),"You lost the game",Toast.LENGTH_LONG).show();
                DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                db.addWonGrid(new GridValue(Head,time,"lost"));
                Intent i=new Intent(GamePage.this,MainMenu.class);
                startActivity(i);
                finish();
            }
        };
        countDownTimer.start();
    }

    // Declare the onBackPressed method when the back button is pressed this method will call
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GamePage.this);
        builder.setMessage("You won't be able to change the existing game, Are you sure ?");
        builder.setTitle("Alert !");
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        /** Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.**/
        builder.setPositiveButton("Save and exit", (DialogInterface.OnClickListener) (dialog, which) -> {
            try {
                DatabaseHandler dbHelper = new DatabaseHandler(this);
                GameEngine.getInstance().saveExit();
                StringBuilder savedData = GameGrid.getData();
                StringBuilder originalData=getOriginalData();
                dbHelper.addGrid(new GridValue(savedData,originalData,Head,time,"save"));
                Toast.makeText(this,"Game Saved",Toast.LENGTH_SHORT).show();
            }
            catch (SQLException e) {
                Toast.makeText(this,"Game not saved",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Intent i=new Intent(GamePage.this,MainMenu.class);
            startActivity(i);
            finish();
        });

        /** Exit without saving **/
        builder.setNeutralButton("Exit", (DialogInterface.OnClickListener) (dialog, which) -> {
            DatabaseHandler db=new DatabaseHandler(this);
            db.deleteRecords();
            db.addWonGrid(new GridValue(Head,time,"Exit"));
            Intent i=new Intent(GamePage.this,MainMenu.class);
            startActivity(i);
        });

        /** Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface. **/
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

   /** ORIGINAL SUDUKO OR ANSWER **/
    public void printSudoku (int Sudoku[][]){

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    original_suduko[x][y]=Sudoku[x][y];
                    System.out.print(original_suduko[x][y] + "|");
                }
                System.out.println();
            }
        }

    /** STORING ANSWER IN 1D ARRAY FORMAT **/
    public static StringBuilder getOriginalData(){
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                original_FlatArray[index] =original_suduko[i][j];
                index++;
            }
        }
        StringBuilder arrayString = new StringBuilder();
        for (int i = 0; i < original_FlatArray.length; i++)
        {
            arrayString.append(original_FlatArray[i]);
            if (i < original_FlatArray.length - 1)
            {
                arrayString.append(",");
            }
        }
        return arrayString;
    }
}






