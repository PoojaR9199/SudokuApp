package com.example.sudokuapp.view.buttonsgrid;

import com.example.sudokuapp.GameEngine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class NumberButton extends Button implements OnClickListener{

	private int number;
	
	public NumberButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		GameEngine.getInstance().setNumber(number);
	}
	
	public void setNumber(int number){
		this.number = number;
	}
}
