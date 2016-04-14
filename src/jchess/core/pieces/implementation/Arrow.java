package jchess.core.pieces.implementation;

import jchess.core.Chessboard;
import jchess.core.Player;
import jchess.core.pieces.Piece;
import jchess.core.pieces.traits.behaviors.implementation.ArrowBehavior;
import jchess.core.pieces.traits.behaviors.implementation.BishopBehavior;

public class Arrow extends Piece
{

	public Arrow(Chessboard chessboard, Player player) {
		super(chessboard, player);
        this.symbol = "A";
        this.addBehavior(new ArrowBehavior(this));
	}

}
