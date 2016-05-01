package jchess.core.computerai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class RandomComputerPlayer extends ComputerPlayer {

	public RandomComputerPlayer(Colors color) {
		super(color);
	}

	@Override
	public void move(Chessboard board) {
		//Get all pieces
		List<Piece> pieces = getPossiblePieces(board);
		//Get random Piece
		Piece pieceToMove = pieces.get(new Random().nextInt(pieces.size()));
		//Get random move
		List<Square> endSquares = new ArrayList<Square>(pieceToMove.getAllMoves());
		Collections.shuffle(endSquares);
		Square fromSquare = pieceToMove.getSquare() ;
		Square endSquare = endSquares.get(0);
		//Move
		System.out.println(_color + ": Move " + pieceToMove.getName() 
		+ " : from " + fromSquare.getPozX() + "-" + fromSquare.getPozY()
		+ " : to " + endSquare.getPozX() + "-" + endSquare.getPozY());
		board.move(fromSquare, endSquare);
	}
	
	private List<Piece>  getPossiblePieces(Chessboard board) {
		
		List<Piece> result = new ArrayList<Piece>();
		Square[][] squares = board.getSquares();
        for(int i=0; i<squares.length; i++)
        {
            for(int j=0; j<squares[i].length; j++)
            {
                Piece p = squares[i][j].getPiece();
                if(null != p && (p.getPlayer().getColor() == _color))
                {
                	if(p.getAllMoves().size() > 0)
                    result.add(p);
                }
            }
        }       
		return result;
		
	}

}
