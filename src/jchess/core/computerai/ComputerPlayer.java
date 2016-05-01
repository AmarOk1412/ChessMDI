package jchess.core.computerai;

import jchess.core.Chessboard;
import jchess.core.Colors;

public abstract class ComputerPlayer {

	protected Colors _color;
	
	public ComputerPlayer(Colors color) {
		_color = color;
	}
	
	public abstract void move(Chessboard board);
}
