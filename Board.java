/**
 * class governing state and behavior of 8-puzzle board
 * 
 * AI HW1
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 * 
 * Time spent: 1 hour
 */
public class Board {

	// instance variables
	private int[][] tiles;
	private int[] zeroPos;
	
	// constructor
	public Board(int size){
		tiles = new int[size][size];
		zeroPos = new int[2];
		// create 'model' puzzle for testing
		int cur = 0;
		for(int i = 0; i < size; i++){ // vertical
			for(int j = 0; j < size; j++){ // horizontal
				tiles[i][j] = cur;
				if (cur == 0){
					zeroPos[0] = i;
					zeroPos[1] = j;
				}
				cur++;
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
}

