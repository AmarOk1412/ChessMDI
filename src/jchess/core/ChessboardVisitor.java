package jchess.core;

public interface ChessboardVisitor {
	
	void visit(Chessboard chessboard);
	void visit(Square square);
}
