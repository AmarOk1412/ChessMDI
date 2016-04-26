package jchess.core;


public abstract class ComputerPlayer {

	protected Colors _color;
	
	public ComputerPlayer(Colors color) {
		_color = color;
	}
	
	public abstract void move(Chessboard board);
}
