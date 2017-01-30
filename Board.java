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
 * Time spent: 5 hours
 */
public class Board {

	// instance variables
	private int[] tiles;
	private int zeroPos;
	
	// constructor
	public Board(){
		tiles = new int[9]; // could create n-puzzle in theory
		//goal = new int[9]; // but for this assignment we are only concerned with 8-puzzle
		// create random puzzle with values 0-9
		// 0 tile represents blank/empty tile
		int curInit;
		Set<Integer> closed = new HashSet<Integer>();
		Random rand = new Random();
		boolean flag;
		for(int i = 0; i < tiles.length; i++){
			flag = true;
			while(flag){
				curInit = rand.nextInt(tiles.length);
				if(!closed.contains(curInit)){ // only add number if it's not already on board
					tiles[i] = curInit;
					if (curInit == 0){ // save empty tile location
						zeroPos = i;
					}
					closed.add(curInit); // track numbers on board
					flag = false; // trip flag once number is added
				}
			}

		}
	}

	public Board(int[] state, int zero){
		tiles = state;
		zeroPos = zero;
	}
	
	/**
	 * 
	 */
	public void str(){
		String cur = "";
		for(int i = 0; i < tiles.length; i++){ 
			if(i % 3 == 0){
				cur += '\n';
			}
			cur += " " + tiles[i] + " |";
				
		} 
		System.out.println(cur);
	}
	
	/**
	 * j = horizontal direction
	 * i = vertical direction
	 * @param direction
	 */
	/*public void moveTile(char direction){
		switch(direction){
		case 'w':
			if(zeroPos[1] - 1 >= 0){
				tiles[zeroPos[0]][zeroPos[1]] = tiles [zeroPos[0]] [zeroPos[1] - 1];
				tiles [zeroPos[0]] [zeroPos[1] - 1] = 0;
				zeroPos[1] -= 1;
				//Board westBoard = new Board
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
	}*/
  
  	public ArrayList<Board> getMoves(){
      
      ArrayList<Board> posMoves = new ArrayList<Board>();
      
      if ((zeroPos % 3) < 2){
    	  int[] copy = tiles.clone(); //this copy might not be a copy!!!
    	  int temp = copy[zeroPos + 1];
    	  copy[zeroPos + 1] = 0;
    	  copy[zeroPos] = temp;
    	  posMoves.add(new Board(copy, zeroPos + 1));
      }
      if ((zeroPos % 3) >= 0){
    	  int[] copy = tiles.clone(); //this copy might not be a copy!!!
    	  int temp = copy[zeroPos - 1];
    	  copy[zeroPos - 1] = 0;
    	  copy[zeroPos] = temp;
    	  posMoves.add(new Board(copy, zeroPos - 1));
      }
      if ((zeroPos) >= 3){
    	  int[] copy = tiles.clone(); //this copy might not be a copy!!!
    	  int temp = copy[zeroPos - 3];
    	  copy[zeroPos - 3] = 0;
    	  copy[zeroPos] = temp;
    	  posMoves.add(new Board(copy, zeroPos - 3));
      }
      if ((zeroPos) < 6){
    	  int[] copy = tiles.clone(); //this copy might not be a copy!!!
    	  int temp = copy[zeroPos + 3];
    	  copy[zeroPos + 3] = 0;
    	  copy[zeroPos] = temp;
    	  posMoves.add(new Board(copy, zeroPos + 3));
      }
      
      return posMoves;
  	}
  	
  	public int heuristic1(){
  		
  		int numMisplaced = 0;
  		
  		for (int i = 0; i < tiles.length; i++){
  			if (tiles[i] != i){
  				numMisplaced++;
  			}
  		}
  		
  		return numMisplaced;
  	}
  	
  	/*private int findDisplacemt(int num){
  		
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
  	}*/
  	public int heuristic2(){
  		
  		int totalMovt = 0;
  		
  		for (int i = 0; i < tiles.length; i++){
  			totalMovt += Math.abs(i - tiles[i])%3 + Math.abs(i - tiles[i])%3;
  		}
  	
  		return totalMovt;
  	}
}