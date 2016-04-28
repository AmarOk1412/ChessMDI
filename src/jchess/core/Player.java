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

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 * Sébastien Blin <sebastien.blin@enconn.fr>
 */
package jchess.core;

import java.io.Serializable;
import java.security.SecureRandom;
import java.math.BigInteger;


/**
 * Class representing the player in the game
 */
public class Player implements Serializable
{
	private String name;
	private Colors color;
    private playerTypes playerType; //TODO: Useful ?
    public boolean goDown; //TODO: Move
    private ComputerPlayer _computer;

    public enum playerTypes //TODO: Useful ?
    {
        localUser, networkUser, computer
    }
    

    /**
     * Constructor for Player class
     * @param color 
     */
    public Player(String color)
    {
    	SecureRandom random = new SecureRandom();
        this.name = new BigInteger(8, random).toString() + "_" + color;
        this.color = Colors.valueOf(color.toUpperCase());
        this.goDown = false;
        this.playerType = this.playerType.localUser;
        this._computer = null;
    }

    /**
     * Constructor for Player class
     * @param name
     * @param color 
     */
    public Player(String name, String color)
    {
        this.name = name;
        this.color = Colors.valueOf(color.toUpperCase());
        this.goDown = false;
        this.playerType = this.playerType.localUser;
        this._computer = null;
    }

    /** Method setting the players name
     *  @param name name of player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Method getting the players name
     *  @return name of player
     */
    public String getName()
    {
        return this.name;
    }

    /** Method setting the players type
     *  @param type type of player - enumerate
     */
    public void setType(playerTypes type)
    {
        this.playerType = type;
    }
    
    public void setComputerPlayer(ComputerPlayer computer) {
    	this._computer = computer;
    }

    /**
     * @return the color
     */
    public Colors getColor()
    {
        return color;
    }

    /**
     * @return the playerType
     */
    public playerTypes getPlayerType()
    {
        return playerType;
    }

    /**
     * @return the goDown
     */
    public boolean isGoDown()
    {
        return goDown;
    }
    
    public void computerMove(Chessboard board)
    {
    	if(_computer != null)
    		_computer.move(board);
    	//TODO log else
    }
}
