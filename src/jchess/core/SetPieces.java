package jchess.core;

public abstract class SetPieces {
	
	protected int _chessboardSize;
	protected Chessboard _chessboard;
	
	public SetPieces(Chessboard chessboard, int size)
	{
		_chessboard = chessboard;
		_chessboardSize = size;
	}
	
	public abstract void setPieces4NewGame(Player plWhite, Player plBlack);

}
