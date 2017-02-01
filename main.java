package eightpuzzle;

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


public class main {

	public static int[] aStar(Board board){

		Queue<Board> frontier = new PriorityQueue<Board>();
		int nodeCount = 0;
		Set<Board> visited = new HashSet<Board>();

		frontier.add(board);

		while (!frontier.isEmpty()){
			Board curNode = frontier.poll();
			if(curNode.heuristic() == 0){
				int[] answer = {nodeCount, curNode.getNumberMoved()};
				return answer;
			}
			List<Board> possibleMoves = curNode.getMoves();
			for(int i = 0; i < possibleMoves.size(); i++){
				Board child = possibleMoves.get(i);
				if(!visited.contains(child)){
					frontier.add(child);
					visited.add(child);
				}
				nodeCount++;
			}
		}


		return new int[2];
	}

	private static void generateRandSolutions(int h){
		Map<Integer,List<Integer>> solutions = new HashMap<Integer,List<Integer>>(); //List: 0:num Nodes, 1:num Boards generated

		int i = 0;

		while (i < 1200){
			Board board = new Board(h);
			while(!board.isSolvable()){
				board = new Board(h);
			}
			int[] aStarAns = aStar(board); // 0: num nodes, 1: depth
			if (solutions.containsKey(aStarAns[1])){
				List<Integer> value = solutions.get(aStarAns[1]);
				value.set(0, value.get(0) + aStarAns[0]);
				value.set(1, value.get(1)+1);
				solutions.put(aStarAns[1], value);
			}
			else{
				List<Integer> value = new ArrayList<Integer>();
				value.add(0, aStarAns[0]);
				value.add(1, 1);
				solutions.put(aStarAns[1], value);
			}
			i++;
		}

		Set<Integer> keys = solutions.keySet();
		try{
			PrintWriter writer = new PrintWriter("aStar"+h+".txt", "UTF-8");
			
			writer.println("Heuristic " + h);
			for (int k: keys){
				writer.println("d: " + k + ", Average Nodes Generated: " + 
						solutions.get(k).get(0)/solutions.get(k).get(1) + ", Number of boards: " + solutions.get(k).get(1));
			}
			writer.close();
		}catch (IOException e) {
			// do something
		}

	}
	public static void main(String args[]){

		generateRandSolutions(1);
		generateRandSolutions(2);
		generateRandSolutions(3);
		
		/*Board board = new Board(3);
		
		while(!board.isSolvable()){
			board = new Board(3);}
		
		board.str();
		
		int[] solutions = aStar(board);
		System.out.println("Nodes expanded = " + solutions[0] + '\n' +
				"Depth = " + solutions[1]);*/
		
		
		//		Board board = new Board(1);
		//		board.str();
		//		Board boardO = new Board(1);
		//		boardO.str();
		//		System.out.println("Heuristics:" + '\n' + 
		//				"This Board = " + board.heuristic() + '\n' +
		//				"Other Board = " + boardO.heuristic());
		//		System.out.println(board.compareTo(boardO));

		//		int i = 0;
		//		int yes = 0;
		//		int no = 0;
		//		Board board;
		//		while(i < 100){
		//			board = new Board(1);
		//			if(board.isSolvable()){
		//				yes++;
		//			} else {
		//				no++;
		//			}
		//			i++;
		//		}
		//		
		//		System.out.println("Solvable = " + yes + '\n'
		//				+ "Unsolvable = " + no);

		/*int[] ans = {1,4,2,3,0,5,6,7,8};
		Board board = new Board(ans,4,1,0);*/
		/*while(!board.isSolvable()){
			board = new Board(2);
		}*/
		/*board.str();
		int[] solutions = aStar(board);
		//board.str();
		System.out.println("Nodes expanded = " + solutions[0] + '\n' +
				"Depth = " + solutions[1]);*/

	}
}