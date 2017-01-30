package eightpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * class governing state and behavior of 8-puzzle board
 * 
 * AI HW1
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 * 
 * Time spent: 2.5 hours
 */
public class Board {

	// instance variables
	private int[][] tiles;
	private int[] zeroPos;
	private int[][] goal;
	
	// constructor
	public Board(int size){
		tiles = new int[3][3]; // could create n-puzzle in theory
		goal = new int[3][3]; // but for this assignment we are only concerned with 8-puzzle
		zeroPos = new int[2];
		// create random puzzle with values 0-9
		// 0 tile represents blank/empty tile
		int curInit;
		int curGoal = 1;
		Set<Integer> closed = new HashSet<Integer>();
		Random rand = new Random();
		boolean flag;
		for(int i = 0; i < tiles[0].length; i++){ // vertical (columns)
			for(int j = 0; j < tiles.length; j++){ // horizontal (rows)
				flag = true;
				while(flag){
					curInit = rand.nextInt(tiles[0].length * tiles.length);
					if(!closed.contains(curInit)){ // only add number if it's not already on board
						tiles[i][j] = curInit;
						if (curInit == 0){ // save empty tile location
							zeroPos[0] = i;
							zeroPos[1] = j;
						}
						closed.add(curInit); // track numbers on board
						flag = false; // trip flag once number is added
					}
				}
				
				goal[i][j] = curGoal;
				curGoal++;
				if(curGoal > (8)){
					curGoal = 0;
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public void str(){
		String cur = "";
		for(int i = 0; i < tiles[0].length; i++){ // vertical
			for(int j = 0; j < tiles.length; j++){ // horizontal
				cur += " " + tiles[i][j] + " |";
			}
			cur += '\n';  
		}
		System.out.println(cur);
	}
	
	/**
	 * j = horizontal direction
	 * i = vertical direction
	 * @param direction
	 */
	public void moveTile(char direction){
		switch(direction){
		case 'w':
			if(zeroPos[1] - 1 >= 0){
				tiles[zeroPos[0]][zeroPos[1]] = tiles [zeroPos[0]] [zeroPos[1] - 1];
				tiles [zeroPos[0]] [zeroPos[1] - 1] = 0;
				zeroPos[1] -= 1;
			} else {
				System.out.println("Move is not valid.");
			}
			break;
		case 'e':
			if(zeroPos[1] + 1 <= tiles.length - 1){
				tiles[zeroPos[0]][zeroPos[1]] = tiles [zeroPos[0]] [zeroPos[1] + 1];
				//System.out.println(tiles[zeroPos[0]][zeroPos[1]]);
				tiles [zeroPos[0]] [zeroPos[1] + 1] = 0;
				zeroPos[1] += 1;
				//System.out.println("Zero Position: (" + zeroPos[0] + ", " + zeroPos[1] + ")");
			} else {
				System.out.println("Move is not valid.");
			}
			break;
		case 's':
			if(zeroPos[0] + 1 <= tiles.length - 1){
				tiles[zeroPos[0]][zeroPos[1]] = tiles [zeroPos[0] + 1] [zeroPos[1]];
				tiles [zeroPos[0] + 1] [zeroPos[1]] = 0;
				zeroPos[0] += 1;
			} else {
				System.out.println("Move is not valid.");
			}
			break;
		case 'n':
			if(zeroPos[0] - 1 >= 0){
				tiles[zeroPos[0]][zeroPos[1]] = tiles [zeroPos[0] - 1] [zeroPos[1]];
				tiles [zeroPos[0] - 1] [zeroPos[1]] = 0;
				zeroPos[0] -= 1;
			} else {
				System.out.println("Move is not valid.");
			}
			break;
		default:
			System.out.println("Must move n, s, w, or e.");
			break;
		}
	}
  
  	public ArrayList<int[]> getMoves(){
      
      ArrayList<int[]> posMoves = new ArrayList<int[]>();
      
      if ((zeroPos[0] - 1) >= 0){
    	  int[] possPos = {zeroPos[0]-1, zeroPos[1]};
    	  posMoves.add(possPos);
      }
      if ((zeroPos[0] + 1) < tiles.length){
    	  int[] possPos = {zeroPos[0]+1, zeroPos[1]};
    	  posMoves.add(possPos);
      }
      if ((zeroPos[1] - 1) >= 0){
    	  int[] possPos = {zeroPos[0], zeroPos[1]-1};
    	  posMoves.add(possPos);
      }
      if ((zeroPos[1] + 1) < tiles[0].length){
    	  int[] possPos = {zeroPos[0], zeroPos[1]+1};
    	  posMoves.add(possPos);
      }
      
      return posMoves;
  	}
  	
  	public int heuristic1(){
  		
  		int numMisplaced = 0;
  		
  		for (int i = 0; i < tiles.length; i++){
  			for (int j = 0; j < tiles[0].length; j++){
  				if ((tiles[i][j] != goal[i][j]) && tiles[i][j] != 0){
  					numMisplaced++;
  				}
  			}
  		}
  		
  		return numMisplaced;
  	}
  	
  	private int findDisplacemt(int num){
  		
  		int[] goalPlace = new int[2];
  		int[] tilesPlace = new int[2];
  		boolean goalPlaceFound = false;
  		boolean tilesPlaceFound = false;
  		
  		for (int i = 0; i < tiles.length; i++){
  			for (int j = 0; j < tiles[0].length; j++){
  				if (tiles[i][j] == num){
  					tilesPlace[0] = i;
  					tilesPlace[1] = j;
  				}
  				if (goal[i][j] == num){
  					goalPlace[0] = i;
  					goalPlace[1] = j;
  				}
  				if (goalPlaceFound && tilesPlaceFound){
  					break;
  				}
  			}
  		}
  		return Math.abs(goalPlace[0]-tilesPlace[0]) + Math.abs(goalPlace[1]-tilesPlace[1]);
  	}
  	public int heuristic2(){
  		
  		int totalMovt = 0;
  		
  		for (int i = 1; i < 9; i++){
  				totalMovt += findDisplacemt(i);
  		}
  	
  		return totalMovt;
  	}
}
