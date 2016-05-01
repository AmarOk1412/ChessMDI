package jchess.core.visitor;

import jchess.core.Chessboard;
import jchess.core.Square;

public interface ChessboardVisitor {
	
	void visit(Chessboard chessboard);
	void visit(Square square);
}
