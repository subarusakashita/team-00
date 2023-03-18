package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Actions {
	List<ArrayList<Integer>> actions;
	List<ArrayList<Integer>> arrowThrows;
	
	public Actions() {
		this.actions = new ArrayList<ArrayList<Integer>>();
		this.arrowThrows = new ArrayList<ArrayList<Integer>>();
	}
	public void getActions(Board board, Queen queen) {
		actions = new ArrayList<ArrayList<Integer>>();
		int Qrow = queen.getRow();
		int Qcol = queen.getCol();
		ArrayList<Integer> action;
		// up
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0) {break;}
			else if(board.board[Qrow - i][Qcol] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(0);
			actions.add(action);
		}
		// down
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9) {break;}
			else if(board.board[Qrow + i][Qcol] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(0);
			actions.add(action);
		}
		// left
		for(int i=1;i<10;++i) {
			if(Qcol - i < 0) {break;}
			else if(board.board[Qrow][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(0);
			action.add(-i);
			actions.add(action);
		}
		// right
		for(int i=1;i<10;++i) {
			if(Qcol + i > 9) {break;}
			else if(board.board[Qrow][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(0);
			action.add(i);
			actions.add(action);
		}
		// up left
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0 || Qcol - i < 0) {break;}
			else if(board.board[Qrow - i][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(-i);
			actions.add(action);
		}
		// up right
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0 || Qcol + i > 9) {break;}
			else if(board.board[Qrow - i][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(i);
			actions.add(action);
		}
		// down left
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9 || Qcol - i < 0) {break;}
			else if(board.board[Qrow + i][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(-i);
			actions.add(action);
		}
		// down right
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9 || Qcol + i > 9) {break;}
			else if(board.board[Qrow + i][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(i);
			actions.add(action);
		}
	}
	public boolean escape(Board board, Queen queen) {
		for(int i=0;i<board.allies.length;i++) {
			int numofAction = actions.size();
			if(numofAction <2) {
				return true;
			}
			
		}
		return false;
	}
	public void availableArrows(Board board, Queen queen) {
		arrowThrows = new ArrayList<ArrayList<Integer>>();
		int Qrow = queen.getRow();
		int Qcol = queen.getCol();
		ArrayList<Integer> action;
		// up
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0) {break;}
			else if(board.board[Qrow - i][Qcol] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(0);
			arrowThrows.add(action);
		}
		// down
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9) {break;}
			else if(board.board[Qrow + i][Qcol] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(0);
			arrowThrows.add(action);
		}
		// left
		for(int i=1;i<10;++i) {
			if(Qcol - i < 0) {break;}
			else if(board.board[Qrow][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(0);
			action.add(-i);
			arrowThrows.add(action);
		}
		// right
		for(int i=1;i<10;++i) {
			if(Qcol + i > 9) {break;}
			else if(board.board[Qrow][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(0);
			action.add(i);
			arrowThrows.add(action);
		}
		// up left
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0 || Qcol - i < 0) {break;}
			else if(board.board[Qrow - i][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(-i);
			arrowThrows.add(action);
		}
		// up right
		for(int i=1;i<10;++i) {
			if(Qrow - i < 0 || Qcol + i > 9) {break;}
			else if(board.board[Qrow - i][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(-i);
			action.add(i);
			arrowThrows.add(action);
		}
		// down left
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9 || Qcol - i < 0) {break;}
			else if(board.board[Qrow + i][Qcol - i] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(-i);
			arrowThrows.add(action);
		}
		// down right
		for(int i=1;i<10;++i) {
			if(Qrow + i > 9 || Qcol + i > 9) {break;}
			else if(board.board[Qrow + i][Qcol + i] != null) {break;}
			action = new ArrayList<>();
			action.add(i);
			action.add(i);
			arrowThrows.add(action);
		}
	}
}