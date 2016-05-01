package jchess.core.moves;

import jchess.core.Chessboard;

public abstract class MoveBuilder {

	private class NoPieceException extends Exception {
		private static final long serialVersionUID = 1L;

		public NoPieceException(String message) {
			super(message);
		}
	}

	protected int _xFrom;
	protected int _yFrom;
	protected int _xTo;
	protected int _yTo;
	protected Chessboard _board;

	public void move() throws Exception {
		if(_board.getSquare(_xFrom, _yFrom).piece == null)
			throw new NoPieceException("No piece at square: " + _xFrom + "/" + _yFrom);
		if(!_board.getSquare(_xFrom, _yFrom).piece.canMove(_xTo, _yTo))
			throw new NoPieceException("Piece can't move to : " + _xTo + "/" + _yTo);
		_board.testMove(_xFrom, _yFrom, _xTo, _yTo);
	}

}
