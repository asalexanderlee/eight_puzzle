package eightpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class Board implements Comparable<Board>{

	// instance variables
	private int[] tiles;
	private int zeroPos;
	private int heur;
	private int moves;

	// constructor
	public Board(int h){
		tiles = new int[9]; // could create n-puzzle in theory
		heur = h;
		moves = 0;
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

	public Board(int[] state, int zero, int h, int m){
		tiles = state;
		zeroPos = zero;
		heur = h;
		moves = m;
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

	public List<Board> getMoves(){

		List<Board> posMoves = new ArrayList<Board>();

		boolean[] checks = {(zeroPos % 3) < 2, (zeroPos % 3) >= 0, (zeroPos) >= 3, (zeroPos) < 6};
		int[] movts = {zeroPos+1, zeroPos-1, zeroPos-3, zeroPos+3};
		int i = 0;

		while (i < 4){
			if (checks[i]){
				int[] copy = tiles.clone();
				int temp = copy[movts[i]];
				copy[movts[i]] = 0;
				copy[zeroPos] = temp;
				posMoves.add(new Board(copy, movts[i], this.heur, this.moves++));
			}
			i++;
		}
		return posMoves;
	}


	private int heuristic1(){

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
	private int heuristic2(){

		int totalMovt = 0;

		for (int i = 0; i < tiles.length; i++){
			totalMovt += Math.abs(i - tiles[i])%3 + Math.abs(i - tiles[i])%3;
		}

		return totalMovt;
	}

	public int compareTo(Board o) {
		
		return (this.heuristic() + this.getNumberMoved()) - (o.heuristic() + o.getNumberMoved());
	}
	
	public int getUsedHeur(){
		return heur;
	}

	public boolean isSolvable(){
		// count number of inversions on board
		int inversions = 0;
		for(int i = 0; i < tiles.length; i++){
			for(int j = i + 1; j < tiles.length; j++){
				if(tiles[j] > tiles[i]){
					inversions++;
				}
			}
		}
		//System.out.println(inversions);
		// check parity of inversions
		if(inversions % 2 == 0){ // even = solvable
			//System.out.println("Solvable puzzle.");
			return true;
		} else { // odd = unsolvable
			//System.out.println("Unsolvable puzzle.");
			return false;
		}
	}
	
	public int heuristic(){
		if(heur == 1){
			return this.heuristic1();
		}
		else if (heur == 2){
			return this.heuristic2();
		} else {
			return 0; //EXCEPTION NEEDED?
		}
	}
	
	public int getNumberMoved(){
		return moves;
	}
}