package jchess.core.moves;

import org.apache.log4j.Logger;

import jchess.core.Chessboard;

public class AlgebricChainMove extends MoveBuilder {

    private static final Logger LOG = Logger.getLogger(Chessboard.class);  
	private String _horizontal = "abcdefgh";
    private String _vertical = "87654321";

    public AlgebricChainMove(Chessboard board) {
    	_board = board;
    }

    public AlgebricChainMove from(String value)
    {
    	if(value.length() != 2)
    	{
    		LOG.error("from value incorrecte : " + value );
    		return this;
    	}
    	int index = _horizontal.indexOf(value.getBytes()[0]);
    	this._xFrom = index;
    	index = _vertical.indexOf(value.getBytes()[1]);
    	this._yFrom = index;
    	return this;
    }

    public AlgebricChainMove to(String value)
    {
    	if(value.length() != 2)
    	{
    		LOG.error("from value incorrecte : " + value );
    		return this;
    	}
    	int index = _horizontal.indexOf(value.getBytes()[0]);
    	this._xTo = index;
    	index = _vertical.indexOf(value.getBytes()[1]);
    	this._yTo = index;
    	return this;
    }
    

}
