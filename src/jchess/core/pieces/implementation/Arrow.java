package jchess.core.pieces.implementation;

import jchess.core.Chessboard;
import jchess.core.Player;
import jchess.core.pieces.Piece;
import jchess.core.pieces.traits.behaviors.implementation.ArrowBehavior;

public class Arrow extends Piece
{
    protected final int value = 3;

	public Arrow(Chessboard chessboard, Player player) {
		super(chessboard, player);
        this.symbol = "A";
        this.addBehavior(new ArrowBehavior(this));
	}

	@Override
	public int getScore() {
		return value;
	}
}
