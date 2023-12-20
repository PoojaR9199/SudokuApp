package com.example.sudokuapp;

import com.example.sudokuapp.view.sudokugrid.GameGrid;

import android.content.Context;

public class GameEngine {
	private static GameEngine instance;
	public GameGrid grid = null;
	private int selectedPosX = -1, selectedPosY = -1;
	private GameEngine(){}

	public static GameEngine getInstance(){
		if( instance == null ){
			instance = new GameEngine();
		}
		return instance;
	}
	
	public void createGrid(Context context,int n){
		int[][] Sudoku = SudokuGenerator.getInstance().generateGrid();
		GamePage gamePage=new GamePage();
		gamePage.printSudoku(Sudoku); //print data in LOGCAT (Original generated sudoku)
		Sudoku = SudokuGenerator.getInstance().removeElements(Sudoku,n);
		grid = new GameGrid(context);
		grid.setGrid(Sudoku);
	}

	public void createGrid(Context context){
		int[][] Sudoku = SudokuGenerator.getInstance().genearteContinueGrid();
		GamePage gamePage=new GamePage();
		gamePage.printSudoku(Sudoku); //print data in LOGCAT (Original generated sudoku)
		grid = new GameGrid(context);
		grid.setGrid(Sudoku);
	}
	
	public GameGrid getGrid()
	{return grid;
	}
	
	public void setSelectedPosition( int x , int y ){
		selectedPosX = x;
		selectedPosY = y;
	}
	
	public void setNumber( int number ){
		if( selectedPosX != -1 && selectedPosY != -1 ){
			grid.setItem(selectedPosX,selectedPosY,number);
		}
		grid.checkGame();
	}

	public void saveExit()
	{
		grid.checkGame();
	}
}
