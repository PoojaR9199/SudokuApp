package com.example.sudokuapp;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {
	private static SudokuGenerator instance;

	private ArrayList<ArrayList<Integer>> Available = new ArrayList<ArrayList<Integer>>();
	String text;
	private Random rand = new Random();
	SudokuGenerator(String text)
	{
		this.text=text;
	}
	SudokuGenerator(){}
	int[][] Sudoku = new int[9][9];
	public int [][]temp = new int[9][9];

	public static SudokuGenerator getInstance(){
		if( instance == null ){
			instance = new SudokuGenerator();
		}
		return instance;
	}

	/** ORIGINAL GRID with solved values **/
	public int[][] generateGrid(){
		int currentPos = 0;
		while( currentPos < 81 ){
			if( currentPos == 0 ){
				clearGrid(Sudoku);
			}
			if( Available.get(currentPos).size() != 0 ){
				int i = rand.nextInt(Available.get(currentPos).size());
				int number = Available.get(currentPos).get(i);
				if( !checkConflict(Sudoku, currentPos , number)){
					int xPos = currentPos % 9;
					int yPos = currentPos / 9;
					Sudoku[xPos][yPos] = number;
					Available.get(currentPos).remove(i);
					currentPos++;
				}else{
					Available.get(currentPos).remove(i);
				}
			}
			else{
				for( int i = 1 ; i <= 9 ; i++ ){
					Available.get(currentPos).add(i);
				}
				currentPos--;
			}
		}
		return Sudoku;
	}

	public int[][] genearteContinueGrid() {
		MainMenu mm=new MainMenu();
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				Sudoku[i][j]=mm.Grid2DArray[i][j];
			}
		}
		return Sudoku;
	}

	public int[][] removeElements(int[][] Sudoku,int n){
		int i = 0;
		int m=n;
		while (i < m) {
				int x = rand.nextInt(9);
				int y = rand.nextInt(9);
				if (Sudoku[x][y] != 0) {
					Sudoku[x][y] = 0;
					i++;

				}
			}
		/** After removing the elements **/
		for (int a=0;a<9;a++){
			for(int b=0;b<9;b++){
				//temp[a][b]=Sudoku[a][b];
				System.out.print(Sudoku[a][b]);
			}
			System.out.println();
		}
		return Sudoku;
	}
	
	private void clearGrid(int [][] Sudoku){
		Available.clear();
		for( int y =  0; y < 9 ; y++ ){
			for( int x = 0 ; x < 9 ; x++ ){
				Sudoku[x][y] = -1;
			}
		}
		for( int x = 0 ; x < 81 ; x++ ){
			Available.add(new ArrayList<Integer>());
			for( int i = 1 ; i <= 9 ; i++){
				Available.get(x).add(i);
			}
		}
	}
	
	private boolean checkConflict( int[][] Sudoku , int currentPos , final int number){
		int xPos = currentPos % 9;
		int yPos = currentPos / 9;
		
		if( checkHorizontalConflict(Sudoku, xPos, yPos, number) || checkVerticalConflict(Sudoku, xPos, yPos, number) || checkRegionConflict(Sudoku, xPos, yPos, number) ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return true if there is a conflict
	 * @param Sudoku
	 * @param xPos
	 * @param yPos
	 * @param number
	 * @return
	 */
	private boolean checkHorizontalConflict( final int[][] Sudoku , final int xPos , final int yPos , final int number ){
		for( int x = xPos - 1; x >= 0 ; x-- ) {
			if (number == Sudoku[x][yPos]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkVerticalConflict( final int[][] Sudoku , final int xPos , final int yPos , final int number ){
		for( int y = yPos - 1; y >= 0 ; y-- ){
			if( number == Sudoku[xPos][y] ){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkRegionConflict( final int[][] Sudoku , final int xPos , final int yPos , final int number ){
		int xRegion = xPos / 3;
		int yRegion = yPos / 3;
		
		for( int x = xRegion * 3 ; x < xRegion * 3 + 3 ; x++ ){
			for( int y = yRegion * 3 ; y < yRegion * 3 + 3 ; y++ ){
				if( ( x != xPos || y != yPos ) && number == Sudoku[x][y] ){
					return true;
				}
			}
		}
		
		return false;
	}

}
