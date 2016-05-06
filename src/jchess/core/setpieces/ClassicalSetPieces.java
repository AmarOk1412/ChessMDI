package jchess.core.setpieces;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Player;
import jchess.core.pieces.implementation.Bishop;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Knight;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Queen;
import jchess.core.pieces.implementation.Rook;

public class ClassicalSetPieces extends SetPieces {

	public ClassicalSetPieces(Chessboard chessboard, int size) {
		super(chessboard, size);
	}

	@Override
	public void setPieces4NewGame(Player plWhite, Player plBlack) {
		if(_chessboardSize != 8)
		{
			_chessboard.getChessMoves().getGame().endGame("The classical mode can't be played with size=" + _chessboardSize + " please change the mode");
			Chessboard.LOG.error("ClassicalSetPieces can't setPieces4NewGame size != 8");
			return;
		}
        Player player = plBlack;
        Player player1 = plWhite;
        this.setFigures4NewGame(0, player);
        this.setPawns4NewGame(1, player);
        this.setFigures4NewGame(7, player1);
        this.setPawns4NewGame(6, player1);		
	}
	
	/**  
     *  Method to set Figures in row (and set Queen and King to right position)
     *  @param i row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setFigures4NewGame(int i, Player player)
    {
        if (i != 0 && i != 7)
        {
            Chessboard.LOG.error("error setting figures like rook etc.");
            return;
        }
        else if (i == 0)
        {
            player.goDown = true;
        }

        _chessboard.getSquare(0, i).setPiece(new Rook(_chessboard, player));
        _chessboard.getSquare(7, i).setPiece(new Rook(_chessboard, player));
        _chessboard.getSquare(1, i).setPiece(new Knight(_chessboard, player));
        _chessboard.getSquare(6, i).setPiece(new Knight(_chessboard, player));
        _chessboard.getSquare(2, i).setPiece(new Bishop(_chessboard, player));
        _chessboard.getSquare(5, i).setPiece(new Bishop(_chessboard, player));
        

        _chessboard.getSquare(3, i).setPiece(new Queen(_chessboard, player));
        if (player.getColor() == Colors.WHITE)
        {
            _chessboard.setKingWhite(new King(_chessboard, player));
            _chessboard.getSquare(4, i).setPiece(_chessboard.getKingWhite());
        }
        else
        {
            _chessboard.setKingBlack(new King(_chessboard, player));
            _chessboard.getSquare(4, i).setPiece(_chessboard.getKingBlack());
        }
    }

    /**  method set Pawns in row
     *  @param i row where to set pawns
     *  @param player player which is owner of pawns
     * */
    private void setPawns4NewGame(int i, Player player)
    {
        if (i != 1 && i != 6)
        {
            Chessboard.LOG.error("error setting pawns etc.");
            return;
        }
        for (int x = 0; x < _chessboardSize; x++)
        {
            _chessboard.getSquare(x, i).setPiece(new Pawn(_chessboard, player));
        }
    }

}
