package jchess.core.visitor;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;

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
			if(p != null)
				scorePiece = p.getScore();
			
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
