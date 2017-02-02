import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * class governing state and behavior of 8-puzzle board
 * blank tile represented as number 0
 * 
 * AI HW1
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 */
public class Board implements Comparable<Board>{

	// instance variables
	private int[] tiles; // board state
	private int zeroPos; // index of blank
	private int heur; // heuristic used
	private int moves; // moves to get to current state

	/**
	 * random constructor
	 * 
	 * constructs new randomized board that will used specified heuristic function
	 * 
	 * @param h - int specifying heuristic function to use
	 */
	public Board(int h){
		tiles = new int[9]; // represent board state as 1D array
		heur = h;
		moves = 0;

		// randomly generate initial state of board
		int curInit;
		Set<Integer> closed = new HashSet<Integer>();
		Random rand = new Random();
		boolean flag;
		for(int i = 0; i < tiles.length; i++){
			flag = true;
			while(flag){
				curInit = rand.nextInt(tiles.length); // generate random number in range
				if(!closed.contains(curInit)){ // only add number if it's not already on board
					tiles[i] = curInit;
					if (curInit == 0){ // save blank tile location
						zeroPos = i;
					}
					closed.add(curInit); // track numbers on board
					flag = false; // trip flag once number is added
				}
			}

		}
	}

	/**
	 * specific constructor
	 * 
	 * constructs new board object with specific state and heuristic function
	 * counts number of moves taken to get to current state
	 * 
	 * @param state - array of int describing board state
	 * @param zero - index of blank tiles (0)
	 * @param h - int specifying heuristic
	 * @param m - int number of moves taken to reach current state
	 */
	public Board(int[] state, int zero, int h, int m){
		tiles = state;
		zeroPos = zero;
		heur = h;
		moves = m;
	}
	
	/**
	 * getter for the number of the heuristic used for this board
	 * 
	 * @return int - heuristic ID number
	 */
	public int getUsedHeur(){
		return heur;
	}

	/**
	 * getter for number of moves taken to reach current state
	 * 
	 * @return int - number of moves
	 */
	public int getNumberMoved(){
		return moves;
	}

	/**
	 * determines whether the current board is in a solvable configuration
	 * 
	 * counts inversions (numbers in array that are 'out of order' and by how much)
	 * parity determines solvability:
	 * even = solvable
	 * odd = unsolvable
	 * 
	 * based on solutions described by Mark Ryan:
	 * http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
	 * 
	 * and code by Tushar Vaghela
	 * http://math.stackexchange.com/questions/293527/how-to-check-if-a-8-puzzle-is-solvable
	 * 
	 * @return boolean - whether board can be solved or not
	 */
	public boolean isSolvable(){
		// count number of inversions on board
		int inversions = 0;
		for(int i = 0; i < tiles.length; i++){
			for(int j = i + 1; j < tiles.length; j++){
				// don't count blank tile
				if(tiles[j] > tiles[i] && tiles[j] != 0 && tiles[i]!=0){
					inversions++;
				}
			}
		}
		// check parity of inversions
		if(inversions % 2 == 0){ // even = solvable
			return true;
		} else { // odd = unsolvable
			return false;
		}
	}
	
	/**
	 * generates and returns all possible moves for the given board state
	 * 
	 * @return list of boards - child nodes of current state
	 */
	public List<Board> getMoves(){

		List<Board> posMoves = new ArrayList<Board>();

		boolean[] checks = {(zeroPos % 3) < 2, (zeroPos % 3) > 0, (zeroPos) > 3, (zeroPos) < 6};
		int[] movts = {zeroPos+1, zeroPos-1, zeroPos-3, zeroPos+3}; // left, right, up down
		int i = 0;

		while (i < 4){ // all four direction possibilities
			if (checks[i]){ // if movement is allowed
				int[] copy = tiles.clone();
				int temp = copy[movts[i]];
				copy[movts[i]] = 0;
				copy[zeroPos] = temp;
				posMoves.add(new Board(copy, movts[i], this.heur, this.moves + 1)); // add child
			}
			i++;
		}
		return posMoves;
	}
	
	/**
	 * returns heuristic value for the given heuristic
	 * 
	 * evaluates heuristic function in helper method
	 * depends on heuristic specified in board
	 * 
	 * @return int - value of heuristic function for current state
	 */
	public int heuristic(){
		if(heur == 1){
			return this.heuristic1();
		}
		else if (heur == 2){
			return this.heuristic2();
		} 
		else if (heur == 3){
			return this.heuristic3();
		}else {
			return 0; // effectively don't evaluate problem if invalid heuristic is specified
		}
	}

	/**
	 * misplaced tiles heuristic function
	 * 
	 * counts number of tiles that are not in the 'correct' location
	 * 
	 * @return int - value of heuristic function 1 for current state
	 */
	private int heuristic1(){

		int numMisplaced = 0;

		// tile is misplaced if array value doesn't match array index
		for (int i = 0; i < tiles.length; i++){
			if (tiles[i] != i && tiles[i] != 0){ // don't count blank tile
				numMisplaced++;
			}
		}

		return numMisplaced;
	}

	/**
	 * manhattan distance heuristic function
	 * 
	 * counts vertical and horizontal displacement of all tiles from their 'correct' locations
	 * 
	 * row = x / 3
	 * column = x % 3
	 * 
	 * @return int - value of heuristic function 2 for current state
	 */
	private int heuristic2(){

		int totalMovt = 0;

		for (int i = 0; i < tiles.length; i++){
			if (tiles[i] != 0){ // don't count blank tile
				totalMovt += Math.abs(tiles[i]/3 - i/3) + Math.abs(tiles[i]%3 - i%3);
			}
		}

		return totalMovt;
	}
	
	/**
	 * out of column/row heuristic function
	 * 
	 * counts number of tiles that are displace from 'correct' row and 'correct' column
	 * 
	 * row = x / 3
	 * column = x % 3
	 * 
	 * from https://heuristicswiki.wikispaces.com/Tiles+out+of+row+and+column
	 * 
	 * @return int - value of heuristic function 3 for current state
	 */
	private int heuristic3(){
		int total = 0;
		for (int i = 0; i< tiles.length; i++){
			if (tiles[i] != 0){ // don't count blank tile
				if (tiles[i]/3 != i/3){
					total++;
				}
				if (tiles[i]%3 != i%3){
					total++;
				}
			}
		}
		return total;
	}

	/**
	 * overwritten method to implement Comparable interface
	 * 
	 * determines whether a current board is 'more than', 'less than' or 'equal to' a given board 
	 * compares boards based on function:
	 * f(n) = g(n) + h(n)
	 * where g(n) is the number of moves taken to reach the current state
	 * and h(n) is the value of the heuristic function at the current state
	 * 
	 * https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#compareTo-T-
	 * 
	 * @param o - other board object to compare to
	 * @return int - negative if this is smaller than other, 0 if this equals other, 
	 * 				positive if this is larger than other
	 */
	public int compareTo(Board o) {
		return (this.heuristic() + this.getNumberMoved()) - (o.heuristic() + o.getNumberMoved());
	}
	
	/**
	 * overwritten method to enable use with sets
	 * 
	 * determines whether two boards are 'equal'
	 * compares state of board (locations of tiles)
	 * 
	 * @param other - other board object to compare to
	 * @return boolean - whether two boards are equal
	 */
	public boolean equals(Object other){
		Board o = (Board) other; // cast to board to overwrite method
		for(int i = 0; i < this.tiles.length; i++){
			if(o.tiles[i] != this.tiles[i]){ // compare tile states
				return false;
			}
		}
		return true;
	}
	
	/**
	 * overwritten method to enable hashing
	 * 
	 * determines hash code based on state of board
	 * (may not be necessary?)
	 * 
	 * @return int - board hash code
	 */
	public int hashCode(){
		int[] prime = {1,2,5,7,13,17,19,23,29};
		int code = 0;
		for(int i = 0; i < tiles.length; i++){
			code += tiles[i] * prime[i];
		}
		return code;
	}
	
	/**
	 * prints string representation of 3x3 board
	 * 
	 * eg:
	 * 0 | 1 | 2 |
	 * 3 | 4 | 5 |
	 * 6 | 7 | 8 |
	 * 
	 * used primarily for testing
	 */
	public void str(){
		String cur = "";
		for(int i = 0; i < tiles.length; i++){ 
			if(i % 3 == 0){
				cur += '\n'; // new line every 3 tiles
			}
			cur += " " + tiles[i] + " |";

		} 
		System.out.println(cur);
	}
}