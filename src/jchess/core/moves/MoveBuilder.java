package jchess.core.moves;

import jchess.core.Chessboard;

public abstract class MoveBuilder {
	
	protected int _xFrom;
	protected int _yFrom;
	protected int _xTo;
	protected int _yTo;
	protected Chessboard _board;

    public void move()
    {
    	_board.testMove(_xFrom, _yFrom, _xTo, _yTo);
    }

}
