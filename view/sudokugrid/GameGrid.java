package com.example.sudokuapp.view.sudokugrid;

import com.example.sudokuapp.DatabaseHandler;
import com.example.sudokuapp.EasyMediumHard;
import com.example.sudokuapp.GamePage;
import com.example.sudokuapp.GridValue;
import com.example.sudokuapp.MainMenu;
import com.example.sudokuapp.SudokuChecker;
import com.example.sudokuapp.WinPage;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

public class GameGrid {
	private SudokuCell[][] Sudoku = new SudokuCell[9][9];
	public static int[] flatArray = new int[81];
	public static int [][] sudGrid = new int[9][9];


	private Context context;

	public GameGrid( Context context ){
		this.context = context;
		for( int x = 0 ; x < 9 ; x++ ){
			for( int y = 0 ; y < 9 ; y++){
				Sudoku[x][y] = new SudokuCell(context);
			}
		}
	}

	public void setGrid( int[][] grid ){
		for( int x = 0 ; x < 9 ; x++ ){
			for( int y = 0 ; y < 9 ; y++){
				Sudoku[x][y].setInitValue(grid[x][y]);
				if( grid[x][y] != 0 ){
					Sudoku[x][y].setNotModifiable();
				}
			}
		}
	}

	public SudokuCell getItem(int x , int y ){
		return Sudoku[x][y];
	}
	public SudokuCell getItem( int position ){
		int x = position % 9;
		int y = position / 9;
		return Sudoku[x][y];
	}
	
	public void setItem( int x , int y , int number ){
		Sudoku[x][y].setValue(number);
	}
	
	public void checkGame(){
		for( int x = 0 ; x < 9 ; x++ ){
			for( int y = 0 ; y < 9 ; y++ ){
				sudGrid[x][y] = getItem(x,y).getValue();
//				saving[x][y]=sudGrid[x][y];
//				System.out.print(sudGrid[x][y]);
			}

		}
		if( SudokuChecker.getInstance().checkSudoku(sudGrid)){
			DatabaseHandler db=new DatabaseHandler(context.getApplicationContext());
			GamePage gg=new GamePage();
			db.addWonGrid(new GridValue(gg.Head,gg.time,"won"));
			Intent x=new Intent(this.context.getApplicationContext(), WinPage.class);
			this.context.startActivity(x);
			System.out.println("Hurray........................................");
			Toast.makeText(context, "You solved the sudoku.", Toast.LENGTH_LONG).show();
		}
	}

	/**while saving and exiting... Convert to 1D array to store in database**/
	public static StringBuilder getData(){
		int index = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				flatArray[index] =sudGrid[i][j];
				index++;
			}
		}
		StringBuilder arrayString = new StringBuilder();
		for (int i = 0; i < flatArray.length; i++)
		{
			arrayString.append(flatArray[i]);
			if (i < flatArray.length - 1)
			{
				arrayString.append(",");
			}
		}
		return arrayString;
	}
}
