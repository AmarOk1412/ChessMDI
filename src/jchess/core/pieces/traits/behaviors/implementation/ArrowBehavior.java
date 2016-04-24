package jchess.core.pieces.traits.behaviors.implementation;

import java.util.HashSet;
import java.util.Set;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class ArrowBehavior extends LongRangePieceBehavior {

	public ArrowBehavior(Piece piece) {
		super(piece);
	}

	@Override
	public Set<Square> getSquaresInRange() {
        Set<Square> list = new HashSet<>();
        int first = (piece.getPlayer().getColor() == Colors.WHITE) ? piece.getSquare().getPozY() + 1 : piece.getSquare().getPozY() - 1;
        int second =  piece.getSquare().getPozX() - 1;
        int third = piece.getSquare().getPozX() + 1;
        
        Chessboard chessboard = piece.getChessboard();

        Square sqY = chessboard.getSquare(piece.getSquare().getPozX(), first);
        Square sqX1 = chessboard.getSquare(second, piece.getSquare().getPozY());
        Square sqX2 = chessboard.getSquare(third, piece.getSquare().getPozY());
        
        if (!piece.isOut(piece.getSquare().getPozX(), first))
        {
        	if(sqY.getPiece() == null || (sqY.getPiece() != null && sqY.getPiece().getPlayer().getColor() != piece.getPlayer().getColor()))
            list.add(chessboard.getSquares()[piece.getSquare().getPozX()][first]);
        }
        if (!piece.isOut(second, piece.getSquare().getPozY()))
        {
        	if(sqX1.getPiece() == null || (sqX1.getPiece() != null && sqX1.getPiece().getPlayer().getColor() != piece.getPlayer().getColor()))
            list.add(chessboard.getSquares()[second][piece.getSquare().getPozY()]);
        }
        if (!piece.isOut(third, piece.getSquare().getPozY()))
        {
        	if(sqX2.getPiece() == null || (sqX2.getPiece() != null && sqX2.getPiece().getPlayer().getColor() != piece.getPlayer().getColor()))
            list.add(chessboard.getSquares()[third][piece.getSquare().getPozY()]);
        }
        
        if(piece.getPlayer().getColor() == Colors.WHITE)
        list.addAll(getMovesForDirection(DIRECTION_NILL, DIRECTION_UP));
        if(piece.getPlayer().getColor() == Colors.BLACK)
        list.addAll(getMovesForDirection(DIRECTION_NILL, DIRECTION_BOTTOM));
        
        return list;
	}

}
