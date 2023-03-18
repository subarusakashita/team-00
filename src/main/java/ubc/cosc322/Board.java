package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class Board {
	Tile[][] board;
	Queen[] allies;
	Queen[] opponents;
	Queen chosen;
	ArrayList<ArrayList<Integer>> move;
	
	public Board(boolean black) {
		if(!black) {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,false),null,null,new Queen(0,6,false),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(3,0,false),null,null,null,null,null,null,null,null,new Queen(3,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(6,0,true),null,null,null,null,null,null,null,null,new Queen(6,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,true),null,null,new Queen(9,6,true),null,null,null}
			};
			allies = new Queen[] {(Queen) board[0][3],(Queen) board[0][6],(Queen) board[3][0],(Queen) board[3][9]};
			opponents = new Queen[] {(Queen) board[6][0],(Queen) board[6][9],(Queen) board[9][3],(Queen) board[9][6]};
		}
		else {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,true),null,null,new Queen(0,6,true),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(3,0,true),null,null,null,null,null,null,null,null,new Queen(3,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(6,0,false),null,null,null,null,null,null,null,null,new Queen(6,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,false),null,null,new Queen(9,6,false),null,null,null}
			};
			allies = new Queen[] {(Queen) board[6][0],(Queen) board[6][9],(Queen) board[9][3],(Queen) board[9][6]};
			opponents = new Queen[] {(Queen) board[0][3],(Queen) board[0][6],(Queen) board[3][0],(Queen) board[3][9]};
		}
	}
	public Board(Board oldBoard, boolean enemy) {
        this.board = new Tile[10][10];
        this.opponents = new Queen[4];
        this.allies = new Queen[4];
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                if(oldBoard.board[row][col] instanceof Queen) {
                    Queen oldQueen = (Queen) oldBoard.board[row][col];
                    Queen newQueen = new Queen(row, col, oldQueen.getOpponent());
                    this.board[row][col] = newQueen;
                    if(oldQueen.getOpponent()) {
                        for(int i = 0; i < opponents.length; i++) {
                            if(opponents[i] == null) {
                                opponents[i] = newQueen;
                                break;
                            }
                        }
                    } else {
                        for(int i = 0; i < allies.length; i++) {
                            if(allies[i] == null) {
                                allies[i] = newQueen;
                                break;
                            }
                        }
                    }
                } else if(oldBoard.board[row][col] instanceof Arrow) {
                    this.board[row][col] = new Arrow(row, col);
                }
            }
        }
        if(enemy) {
        	Queen[] temp = allies;
        	allies = opponents;
        	opponents = temp;
        }
    }
	
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos, ArrayList<Integer> arrPos) {
		updateBoard(queenPrevPos, queenNewPos);
		updateBoard(arrPos);
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos) {
		int prevRow = queenPrevPos.get(0)-1, prevCol = queenPrevPos.get(1)-1, 
			newRow = queenNewPos.get(0)-1, newCol = queenNewPos.get(1)-1;
		setChosen((Queen) board[prevRow][prevCol]);
		getChosen().setPrevRow(prevRow);
		getChosen().setPrevCol(prevCol);
		getChosen().setRow(newRow);
		getChosen().setCol(newCol);
		board[newRow][newCol] = getChosen();
		board[prevRow][prevCol] = null;
	}
	public void updateBoard(ArrayList<Integer> arrPos) {
		int arrRow = arrPos.get(0)-1, arrCol = arrPos.get(1)-1;
		getChosen().setArrRow(arrRow);
		getChosen().setArrCol(arrCol);
		board[arrRow][arrCol] = new Arrow(arrRow,arrCol);
	}
	public ArrayList<ArrayList<Integer>> randomMove(boolean enemy) {
		ArrayList<Integer> queenPrevPos;
        ArrayList<Integer> queenNewPos;
        ArrayList<Integer> arrPos;
		if(enemy) {
			if(this.gameOverCheck(true) != 1) {
				for(Queen queen: this.opponents) {
	            	queen.actions.getActions(this,queen);
	            }
				chosen = this.opponents[(int) (Math.random()*4)];
				while(chosen.actions.actions.size()==0) {
					chosen = this.opponents[(int) (Math.random()*4)];
				}
		        ArrayList<Integer> action = chosen.actions.actions.get((int) (Math.random()*chosen.actions.actions.size()));
		        queenPrevPos = new ArrayList<Integer>();
		        queenPrevPos.add(chosen.getRow()+1); queenPrevPos.add(chosen.getCol()+1);
		        queenNewPos = new ArrayList<Integer>();
		        queenNewPos.add(chosen.getRow()+action.get(0)+1); queenNewPos.add(chosen.getCol()+action.get(1)+1);
		        updateBoard(queenPrevPos, queenNewPos);
		        chosen.actions.availableArrows(this, chosen);
		        ArrayList<Integer> arrowThrow = chosen.actions.arrowThrows.get((int) (Math.random()*chosen.actions.arrowThrows.size()));
		        arrPos = new ArrayList<Integer>();
		        arrPos.add(chosen.getRow()+arrowThrow.get(0)+1); arrPos.add(chosen.getCol()+arrowThrow.get(1)+1);
		        updateBoard(arrPos);
		        move = new ArrayList<>();
				move.add(queenPrevPos); move.add(queenNewPos); move.add(arrPos);
				return move;
			}
		}
		else {
			if(this.gameOverCheck(false) != 0) {
				for(Queen queen: this.allies) {
	            	queen.actions.getActions(this,queen);
	            }
				chosen = this.allies[(int) (Math.random()*4)];
				while(chosen.actions.actions.size()==0) {
					chosen = this.allies[(int) (Math.random()*4)];
				}
		        ArrayList<Integer> action = chosen.actions.actions.get((int) (Math.random()*chosen.actions.actions.size()));
		        queenPrevPos = new ArrayList<Integer>();
		        queenPrevPos.add(chosen.getRow()+1); queenPrevPos.add(chosen.getCol()+1);
		        queenNewPos = new ArrayList<Integer>();
		        queenNewPos.add(chosen.getRow()+action.get(0)+1); queenNewPos.add(chosen.getCol()+action.get(1)+1);
		        updateBoard(queenPrevPos, queenNewPos);
		        chosen.actions.availableArrows(this, chosen);
		        ArrayList<Integer> arrowThrow = chosen.bestArrowThrow(this);
		        arrPos = new ArrayList<Integer>();
		        arrPos.add(chosen.getRow()+arrowThrow.get(0)+1); arrPos.add(chosen.getCol()+arrowThrow.get(1)+1);
		        updateBoard(arrPos);
		        move = new ArrayList<>();
				move.add(queenPrevPos); move.add(queenNewPos); move.add(arrPos);
				return move;
			}
		}
		move = new ArrayList<>();
		move.add(null); move.add(null); move.add(null);
		return move;
	}
	public int gameOverCheck(boolean enemy) {
        if(enemy) {
        	for(Queen queen: this.opponents) {
            	queen.actions.getActions(this,queen);
            }
            if(this.opponents[0].actions.actions.size() == 0 && this.opponents[1].actions.actions.size() == 0 
            && this.opponents[2].actions.actions.size() == 0 && this.opponents[3].actions.actions.size() == 0) {
            	return 1;
            }
        }
        else {
        	for(Queen queen: this.allies) {
            	queen.actions.getActions(this,queen);
            }
            if(this.allies[0].actions.actions.size() == 0 && this.allies[1].actions.actions.size() == 0 
            && this.allies[2].actions.actions.size() == 0 && this.allies[3].actions.actions.size() == 0) {
            	return 0;
            }
        }
        return -1;
	}
	public int evaluateBoard() {
        int score = 0;
        for(int i = 0; i < allies.length; i ++) {
            allies[i].actions.getActions(this, allies[i]);
            score += allies[i].actions.actions.size();
        }
        for(int i = 0; i < opponents.length; i ++) {
            opponents[i].actions.getActions(this, opponents[i]);
            score -= opponents[i].actions.actions.size();
        }
        return score;
    }
	
	public boolean indanger() {
		for(int i=0; i<allies.length; i++) {
			//see cells around allies[i] to check if it is in danger. 
			int queenRow = allies[i].getRow();
			int queenCol = allies[i].getCol();
			
			
			//make a list of emptyPostions
			ArrayList<Tile> emptyPositions = new ArrayList<>();
			//checks bottom 3 tiles
			for(int j=-1; j<1; j++) {
				if(queenRow+1 < 10 && queenCol+j >= 0 && queenCol+j < 10 &&
					board[queenRow+1][queenCol+j] == null) {
					emptyPositions.add(new Tile(queenRow+1,queenCol+j));
				}
			}
			//checks top 3 tiles
			for(int j=-1; j<1; j++) {
				if(queenRow-1 >= 0 && queenCol+j >= 0 && queenCol+j < 10 &&
					board[queenRow-1][queenCol+j] == null) {
					emptyPositions.add(new Tile(queenRow-1,queenCol+j));
				}
			}
			//checks right tile
			if(queenCol+1 < 10 && board[queenRow][queenCol+1] == null) {
				emptyPositions.add(new Tile(queenRow,queenCol+1));
			}
			//checks left tile
			if(queenCol-1 >= 0 && board[queenRow][queenCol-1] == null) {
				emptyPositions.add(new Tile(queenRow,queenCol-1));
			}
			
			
			//see if its below two
			if (emptyPositions.size() == 1) {
				return true;
			}else if (emptyPositions.size() == 2) {
				if(emptyPositions.get(0).getRow() == emptyPositions.get(1).getRow() &&
						Math.abs(emptyPositions.get(0).getCol()-emptyPositions.get(1).getCol()) == 1) {
					return true;
				} else if(emptyPositions.get(0).getCol() == emptyPositions.get(1).getCol() &&
						Math.abs(emptyPositions.get(0).getRow()-emptyPositions.get(1).getRow()) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void printMove() {
		System.out.println("QCurr: "+move.get(0).toString());
		System.out.println("QNew: "+move.get(1).toString());
		System.out.println("Arrow: "+move.get(2).toString());
	}
	public void printBoard() {
		for(int r=9;r>=0;--r) {
			for(int c=0;c<10;++c) {
				if(board[r][c] instanceof Queen)System.out.print(" Q");
				else if(board[r][c] instanceof Arrow)System.out.print(" A");
				else System.out.print(" N");
			}
			System.out.println();
		}
	}
	public Queen getChosen() {return this.chosen;}
	public void setChosen(Queen chosen) {this.chosen = chosen;}
	
}