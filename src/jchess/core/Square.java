/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jchess.core;

import jchess.core.pieces.Piece;
import jchess.core.visitor.ChessboardVisitor;

/**
 * @author: Mateusz Sławomir Lach ( matlak, msl )
 * @author: Damian Marciniak
 * Class to represent a chessboard square
 */
public class Square
{
    /**
     * X position of square 
     */
    protected int pozX; 

    /**
     * Y position of square
     */
    protected int pozY; 
    
    /**
     * object Piece on square (and extending Piecie)
     */
    private Piece piece = null;
    
    public Square()
    {
    	this.pozX = -1;
    	this.pozY = -1;
    	this.piece = null;
    }

    public Square(int pozX, int pozY, Piece piece)
    {
        this.pozX = pozX;
        this.pozY = pozY;
        this.piece = piece;
    }/*--endOf-Square--*/


    public Square(Square square)
    {
        this.pozX = square.pozX;
        this.pozY = square.pozY;
        this.piece = square.piece;
    }

    public Square clone(Square square)
    {
        return new Square(square);
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
        if(piece != null)
        this.piece.setSquare(this);
    }

    /**
     * @return the pozX
     */
    public int getPozX()
    {
        return pozX;
    }

    /**
     * @param pozX the pozX to set
     */
    public void setPozX(int pozX)
    {
        this.pozX = pozX;
    }

    /**
     * @return the pozY
     */
    public int getPozY()
    {
        return pozY;
    }

    /**
     * @param pozY the pozY to set
     */
    public void setPozY(int pozY)
    {
        this.pozY = pozY;
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public boolean isEmptyOrSamePiece(Piece piece)
    {
        return null == this.piece || this.piece == piece;
    }

	public void accept(ChessboardVisitor visitor) {
		visitor.visit(this);
	}
}
