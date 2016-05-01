package jchess.core.setpieces;

import java.util.Random;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Player;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.Arrow;
import jchess.core.pieces.implementation.Bishop;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Knight;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Queen;
import jchess.core.pieces.implementation.Rook;

public class RandomSetPieces extends SetPieces {
	
	private  Random rand = new Random();

	public RandomSetPieces(Chessboard chessboard, int size) {
		super(chessboard, size);
	}

	@Override
	public void setPieces4NewGame(Player plWhite, Player plBlack) {
        this.setFigures4NewGame(plWhite);
        this.setPawns4NewGame(plWhite);
        this.setFigures4NewGame(plBlack);
        this.setPawns4NewGame(plBlack);				
	}
	
	private void setPawns4NewGame(Player player) {
		for(int p = 0; p < _chessboardSize; ++p)
        {
            int posX = -1;
            int posY = -1;
            while(_chessboard.getSquare(posX, posY) == null ||
            	(_chessboard.getSquare(posX, posY) != null && _chessboard.getSquare(posX, posY).getPiece() != null))
            {
            	posX = rand.nextInt(_chessboardSize + 1);
            	posY = rand.nextInt(_chessboardSize + 1);
            }
            Piece piece = new Pawn(_chessboard, player);
            _chessboard.getSquare(posX, posY).setPiece(piece);
        }
	}

	/**  
     *  Method to set Figures in row (and set Queen and King to right position)
     *  @param i row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setFigures4NewGame(Player player)
    {
        for(int p = 0; p < _chessboardSize; ++p)
        {
            int posX = -1;
            int posY = -1;
            while(_chessboard.getSquare(posX, posY) == null ||
            	(_chessboard.getSquare(posX, posY) != null && _chessboard.getSquare(posX, posY).getPiece() != null))
            {
            	posX = rand.nextInt(_chessboardSize + 1);
            	posY = rand.nextInt(_chessboardSize + 1);
            }
            Piece piece = null;
            switch (p) {
			case 0:
			case 1:
			case 10:
			case 15:
				piece = new Rook(_chessboard, player);
				break;
			case 2:
			case 3:
			case 11:
			case 16:
				piece = new Bishop(_chessboard, player);
				break;
			case 4:
			case 5:
			case 12:
			case 17:
				piece = new Knight(_chessboard, player);
				break;
			case 6:
			case 13:
			case 18:
				piece = new Queen(_chessboard, player);
				break;
			case 7:
				piece = new King(_chessboard, player);
		        if (player.getColor() == Colors.WHITE) {
		        	_chessboard.setKingWhite((King) piece);
		        }
		        if (player.getColor() == Colors.BLACK) {
		        	_chessboard.setKingBlack((King) piece);
		        }
				break;
			case 8:
			case 9:
			case 14:
			case 19:
				piece = new Arrow(_chessboard, player);
				break;

			default:
				break;
			}
            _chessboard.getSquare(posX, posY).setPiece(piece);
        }
    }

}
