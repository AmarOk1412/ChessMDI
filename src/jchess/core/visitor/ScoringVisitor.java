package jchess.core.visitor;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.*;

public class ScoringVisitor implements ChessboardVisitor {

	private int scoreWhite;
	private int scoreBlack;
	@Override
	public void visit(Chessboard chessboard) {
		Chessboard.LOG.info("total score: " + scoreWhite + "(White) - "
											+ scoreBlack + "(Black)");
		
	}

	@Override
	public void visit(Square square) {
		Piece p = square.getPiece();
		if(p != null)
		{
			int scorePiece = 0;
			
			if(p instanceof Pawn)
				scorePiece += 1;
			else if(p instanceof Bishop || 
					p instanceof Knight ||
					p instanceof Arrow)
				scorePiece += 3;
			else if(p instanceof Rook)
				scorePiece += 5;
			else if(p instanceof Queen)
				scorePiece += 10;
			else if(p instanceof King)
				scorePiece += 1000;
			
			if(p.getPlayer().getColor() == Colors.WHITE)
			{
				scoreWhite += scorePiece;
			}
			else
			{
				scoreBlack += scorePiece;
			}
		}	
	}

}
