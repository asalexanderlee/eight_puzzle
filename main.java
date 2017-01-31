package eightpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class main {
	
	public static int[] aStar(Board board){
		
		Queue<Board> frontier = new PriorityQueue<Board>();
		int nodeCount = 0;
		boolean goalReached = false;
		
		frontier.add(board);
		
		while (!goalReached){
			Board curNode = frontier.poll();
			if(curNode.heuristic() == 0){
				System.out.println("Goal found");
				int[] answer = {nodeCount, curNode.getNumberMoved()};
				return answer;
			}
			System.out.println("h: " + curNode.heuristic() + " moves: " + curNode.getNumberMoved());
			curNode.str();
			List<Board> possibleMoves = curNode.getMoves();
			frontier.addAll(possibleMoves); //CHECK HERE IF SOMETHING GOES WRONG
		}
		
		
		return new int[2];
	}
	
	public static void main(String args[]){
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
		

		Board board = new Board(1);
		while(!board.isSolvable()){
			board = new Board(1);
		}
		int[] solutions = aStar(board);
		board.str();
		System.out.println("Nodes expanded = " + solutions[0] + '\n' +
				"Depth = " + solutions[1]);
		
	}
}