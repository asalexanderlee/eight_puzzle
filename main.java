import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * main class to run A* algorithm on 8-puzzle implementation
 * 
 * @author Collin Epstein
 * @author Ashley Alexander-Lee
 *
 */
public class main {

	/**
	 * runs depth-limited A* algorithm on a given board
	 * 
	 * uses any of 3 heuristics:
	 * -misplaced tiles
	 * -manhattan distance
	 * -tiles out of row and column
	 * 
	 * returns solution depth and number of nodes generated
	 * 
	 * use d = 100 or something large to eliminate depth limitation
	 * 8-puzzle solution lengths limited to ~30  (research this?)
	 * 
	 * @param board - an 8-puzzle board object
	 * @param d - int solution depth limit
	 * @return int[] - 0: # of nodes generated, 1: solution depth
	 */
	private static int[] aStar(Board board, int d){

		Queue<Board> frontier = new PriorityQueue<Board>();
		Set<Board> visited = new HashSet<Board>();
		int nodeCount = 0;

		frontier.add(board); // initialize problem

		while (!frontier.isEmpty()){ // return failure if frontier is empty
			Board curNode = frontier.poll(); // pop node
			if(curNode.heuristic() == 0){ // goal state at heuristic = 0
				int[] answer = {nodeCount, curNode.getNumberMoved()};
				return answer;
			}
			List<Board> possibleMoves = curNode.getMoves(); 
			for(int i = 0; i < possibleMoves.size(); i++){
				Board child = possibleMoves.get(i); // add all successors to queue
				// no repeat visits and depth-limited
				if(!visited.contains(child) && child.getNumberMoved() <= d){
					frontier.add(child);
					visited.add(child);
				}
				nodeCount++;
			}
		}

		return new int[2]; //[0,0] indicates no solution possible (or trivial problem)
	}

	/**
	 * run depth-limited A* multiple times and record resulting data to file
	 * 
	 * generates random boards, solves, records and writes data on:
	 * -solution depth
	 * -number of boards generated of each solution depth
	 * -average number of nodes generated in board of each solution depth
	 * 
	 * writes data to .txt file
	 * adapted from code from users Michael and EJP at:
	 * http://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-a-file-in-java
	 * 
	 * @param n - number of boards to generate and run A* on
	 * @param h - heuristic to use in A*
	 * @param d - int solution depth limit
	 */
	private static void generateRandSolutions(int n, int h, int d){
		
		//List: 0: num Nodes, 1: num Boards generated
		Map<Integer,List<Integer>> solutions = new HashMap<Integer,List<Integer>>(); 
		int i = 0;

		while (i < n){ // run algorithm many times
			
			Board board = new Board(h);
			while(!board.isSolvable()){ // only pass solvable board to A*
				board = new Board(h);
			}
			
			int[] aStarAns = aStar(board, d); // 0: num nodes, 1: depth
			// if solution of found depth exists in map, increase number of solutions found
			// and calculate average number of nodes found
			if (solutions.containsKey(aStarAns[1])){ 
				List<Integer> value = solutions.get(aStarAns[1]);
				if(value.get(1) < 100){ // save and average 1st 100 boards of given length
					value.set(0, value.get(0) + aStarAns[0]);
					value.set(1, value.get(1)+1);
					solutions.put(aStarAns[1], value);
				}
			}
			else{ // if no solution of found depth exists in map, create entry
				List<Integer> value = new ArrayList<Integer>();
				value.add(0, aStarAns[0]);
				value.add(1, 1);
				solutions.put(aStarAns[1], value);
			}
			i++;
		}

		Set<Integer> keys = solutions.keySet();
		try{ // write all data to text file
			PrintWriter writer = new PrintWriter("aStar" + h + "." + d + "." + n + ".txt", "UTF-8");

			writer.println("Heuristic " + h + ", Depth limit = " + d + ", Trials = " + n);
			writer.println("Note: dpeth = 0 and # of nodes = 0 indicates discarded solutions.");
			for (int k: keys){
				writer.println("d: " + k + ", Average Nodes Generated: " + 
						solutions.get(k).get(0)/solutions.get(k).get(1) + ", Number of boards: " + solutions.get(k).get(1));
			}
			writer.close();
		}catch (IOException e) {
			// do something
		}

	}
	
	// main method
	public static void main(String args[]){

		generateRandSolutions(5000, 1, 24);
		generateRandSolutions(5000, 2, 24);
		generateRandSolutions(5000, 3, 24);

	}
}