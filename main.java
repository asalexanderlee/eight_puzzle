
public class main {
	public static void main(String args[]){
		Board board = new Board(3);
		board.str();
		System.out.println();
		
		board.moveTile('n');
		board.moveTile('s');
		System.out.println();
		board.str();
		
		board.moveTile('e');
		System.out.println();
		board.str();
		
		board.moveTile('w');
		System.out.println();
		board.str();
		board.moveTile('w');
		
		board.moveTile('x');
		
		board.moveTile('e');
		board.moveTile('e');
		board.moveTile('e');
		board.str();
		board.moveTile('s');
		board.moveTile('s');
		board.str();
	}
}
