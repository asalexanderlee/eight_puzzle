import java.util.*;

/**
 * class governing state and behavior of 8-puzzle board
 * 
 * AI HW1
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 * 
 * Time spent: 2 hours
 */
public class Board {

	// instance variables
	private int[][] tiles;
	private int[] zeroPos;
	private int[][] goal;
	
	// constructor
	public Board(int size){
		tiles = new int[size][size];
		goal = new int[size][size];
		zeroPos = new int[2];
		// create random puzzle with values 0-9
		// 0 tile represents blank/empty tile
		int curTest;
		int curGoal = 1;
		Set<Integer> closed = new HashSet<Integer>();
		Random rand = new Random();
		boolean flag;
		for(int i = 0; i < size; i++){ // vertical (columns)
			for(int j = 0; j < size; j++){ // horizontal (rows)
				flag = true;
				while(flag){
					curTest = rand.nextInt(size * size);
					if(!closed.contains(curTest)){ // only add number if it's not already on board
						tiles[i][j] = curTest;
						if (curTest == 0){ // save empty tile location
							zeroPos[0] = i;
							zeroPos[1] = j;
						}
						closed.add(curTest); // track numbers on board
						flag = false; // trip flag once number is added
					}
				}
				
				goal[i][j] = curGoal;
				curGoal++;
				if(curGoal > (size * size - 1)){
					curGoal = 0;
				}
			}
		}
	}
	
	/**
	 * treats current board state as a matrix and calculates its determinant
	 * 
	 * implementation from:
	 * http://stackoverflow.com/questions/29775646/finding-the-cofactor-and-determinant-of-a-3x3-matrix
	 * user Mathias Kogler
	 * 
	 * @return determinant of current board state as in
	 */
	public int getDet(){
		if (tiles.length == 2)
	        return tiles[0][0] * tiles[1][1] - tiles[0][1] * tiles[1][0];

	    int determinant1 = 0, determinant2 = 0;
	    for (int i = 0; i < tiles[0].length; i++) {
	        int temp = 1, temp2 = 1;
	        for (int j = 0; j < tiles.length; j++) {
	            temp *= tiles[(i + j) % tiles.length][j];
	            temp2 *= tiles[(i + j) % tiles.length][tiles[0].length - 1 - j];
	        }

	        determinant1 += temp;
	        determinant2 += temp2;
	    }

	    return determinant1 - determinant2;
	}
	
	/**
	 * returns representation of the current board state as a string
	 * for testing
	 * 
	 * @return board represented as string
	 */
	public void str(){
		System.out.println(generalString(tiles));
	}
	
	/**
	 * returns representation of goal state as a string
	 * for testing
	 * 
	 * @return goal state represented as string
	 */
	public void goalState(){
		System.out.println(generalString(goal));
	}
	
	/**
	 * general helper method to return a given n x n matrix representing
	 * and n-puzzle board as a string
	 * @param board - 2d array representing n-puzzle board
	 * @return cur - string representation of board
	 */
	private String generalString(int[][] board){
		String cur = "";
		for(int i = 0; i < board[0].length; i++){ // vertical
			for(int j = 0; j < board.length; j++){ // horizontal
				cur += "| " + board[i][j] + " |";
			}
			cur += '\n';  
		}
		return cur;
	}
	
	/**
	 * j = horizontal direction
	 * i = vertical direction
	 * @param direction
	 * @return void
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
}

