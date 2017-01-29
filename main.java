import java.util.Scanner;

/**
 * main class to test 8-puzzle
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 *
 */
public class main {
	public static void main(String args[]){
		
		// code to actively play game in console
		// use WASD command to move blank tiles
		Scanner console = new Scanner(System.in);
		Board board = new Board();
		
		while(!board.isSolvable()){
			board = new Board();
		}
		
		System.out.println("8-puzzle game begin!");
		board.str();
		
		while(board.isSolvable() && board.getState() != board.getGoal()){
			System.out.println("Your guess?");
			char command = console.next().toLowerCase().charAt(0);
			//System.out.println();
			board.moveTile(command);
			board.str();
		}
		
		console.close();
		// game code ends
		
		// code to generate series of randomly generated board
		// and check how many boards are solvable
//		Board board;
//		int i = 0;
//		int yes = 0;
//		int no = 0;
//		boolean solvable;
//		while(i < 100){
//			board = new Board();
//			//board.str();
//			solvable = board.isSolvable();
//			if(solvable){
//				yes++;
//			} else { 
//				no++;
//			}
//			i++;
//		}
//		System.out.println("Solvable Boards: " + yes + '\n' + 
//				"Unsolvable Boards: " + no);
		// end solvability test code
		
//		board.moveTile('n');
//		board.moveTile('s');
//		System.out.println();
//		board.str();
//		
//		board.moveTile('e');
//		System.out.println();
//		board.str();
//		
//		board.moveTile('w');
//		System.out.println();
//		board.str();
//		board.moveTile('w');
//		
//		board.moveTile('x');
//		
//		board.moveTile('e');
//		board.moveTile('e');
//		board.moveTile('e');
//		board.str();
//		board.moveTile('s');
//		board.moveTile('s');
//		board.str();
	}
}
