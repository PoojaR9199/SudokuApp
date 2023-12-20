package com.example.sudokuapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class EasyHistory extends Fragment {



    public EasyHistory() {
        // Required empty public constructor
    }

    TextView count,won,winRate,bestTime,avgTime;
    String level;
    public EasyHistory(String level) {
        this.level=level;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_easy_history, container, false);
        count=view.findViewById(R.id.numofplay);
        won=view.findViewById(R.id.numofwon);
        winRate=view.findViewById(R.id.percentage);
        bestTime=view.findViewById(R.id.numofbest);
        avgTime=view.findViewById(R.id.numofavg);

        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        int rowCount = dbHandler.getRowCount(level);
        int wonCount = dbHandler.getWonCount(level);
        if (rowCount != 0) {
            count.setText(String.valueOf(rowCount));
            won.setText(String.valueOf(wonCount));
            int Rate = (wonCount * 100) / rowCount;
            winRate.setText(String.valueOf(Rate) + "%");
        } else {
            count.setText("0");
            won.setText("0");
            winRate.setText("0%");
        }
        System.out.println("-----------" + rowCount);
        Log.d("Row Count", "Number of rows in the table: " + rowCount);


        /** BEST - TIME **/
        String totTime = "10:00";//default time
        String time_fetched= dbHandler.getBestTime(level);
        if(time_fetched!=null) {
            System.out.println(time_fetched);
            String[] parts1 = totTime.split(":");
            String[] parts2 = time_fetched.split(":");

            // Parse the minutes and seconds as integers
            int minutes1 = Integer.parseInt(parts1[0]);
            int seconds1 = Integer.parseInt(parts1[1]);
            int minutes2 = Integer.parseInt(parts2[0]);
            int seconds2 = Integer.parseInt(parts2[1]);

            // Subtract the times
            int resultMinutes = minutes1 - minutes2;
            int resultSeconds = seconds1 - seconds2;

            // Handle negative seconds by adjusting the minutes
            if (resultSeconds < 0) {
                resultMinutes--;
                resultSeconds += 60;
            }
            String result = String.format("%02d:%02d", resultMinutes, resultSeconds);
            bestTime.setText(result);
        }
        else {
            bestTime.setText("0");
        }


        /** AVERAGE TIME **/
        String averageTime = dbHandler.getAverageTime(level);
        System.out.println(averageTime);
        avgTime.setText(averageTime);
        return view;
    }
}