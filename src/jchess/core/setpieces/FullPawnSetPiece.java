package jchess.core.setpieces;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Player;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Pawn;

public class FullPawnSetPiece extends SetPieces  {

	public FullPawnSetPiece(Chessboard chessboard, int size) {
		super(chessboard, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPieces4NewGame(Player plWhite, Player plBlack) {
        this.setKingLine(0, plWhite);
        this.setPawns4NewGame(1, plWhite);
        this.setKingLine(_chessboardSize-1, plBlack);
        this.setPawns4NewGame(_chessboardSize-2, plBlack);
	}
	
	/**  
     *  Method to set Figures in row (and set Queen and King to right position)
     *  @param i row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setKingLine(int i, Player player)
    {
        if (i != 0 && i != _chessboardSize-1)
        {
            Chessboard.LOG.error("error setting figures like rook etc.");
            return;
        }
        else if (i == 0)
        {
            player.goDown = true;
        }
        
        for(int s = 0; s < _chessboardSize; ++s)
        {
        	if(s != (int)(_chessboardSize/2))
        	{
        		_chessboard.getSquare(s, i).setPiece(new Pawn(_chessboard, player));
        	}
        }
        
        if (player.getColor() == Colors.WHITE)
        {
            _chessboard.setKingWhite(new King(_chessboard, player));
            _chessboard.getSquare(_chessboardSize/2, i).setPiece(_chessboard.getKingWhite());
        }
        else
        {
            _chessboard.setKingBlack(new King(_chessboard, player));
            _chessboard.getSquare(_chessboardSize/2, i).setPiece(_chessboard.getKingBlack());
        }
    }

    /**  method set Pawns in row
     *  @param i row where to set pawns
     *  @param player player which is owner of pawns
     * */
    private void setPawns4NewGame(int i, Player player)
    {
        for (int x = 0; x < _chessboardSize; x++)
        {
            _chessboard.getSquare(x, i).setPiece(new Pawn(_chessboard, player));
        }
    }

}
