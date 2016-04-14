package jchess.core.pieces.traits.behaviors.implementation;

import java.util.HashSet;
import java.util.Set;

import jchess.core.Square;
import jchess.core.pieces.Piece;
import jchess.core.pieces.traits.behaviors.Behavior;

public class ArrowBehavior extends LongRangePieceBehavior {

	public ArrowBehavior(Piece piece) {
		super(piece);
	}

	@Override
	public Set<Square> getSquaresInRange() {
		//TODO better !
        Set<Square> list = new HashSet<>();
        
        list.addAll(getMovesForDirection(DIRECTION_NILL, DIRECTION_UP));
        
        return list;
	}

}
