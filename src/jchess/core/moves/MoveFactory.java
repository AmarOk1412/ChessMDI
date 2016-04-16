package jchess.core.moves;

import org.apache.log4j.Logger;

import jchess.core.Chessboard;

public class MoveFactory {

	private Chessboard _board = null;  
    private static final Logger LOG = Logger.getLogger(Chessboard.class);  
    private int _xFrom = -1;
    private int _yFrom = -1;
    private int _xTo = -1;
    private int _yTo = -1;
    private String _horizontal = "abcdefgh";
    private String _vertical = "87654321";
    
    public MoveFactory(Chessboard board) {
    	_board = board;
    }
    
    public MoveFactory xFrom(Character value)
    {
    	int index = _horizontal.indexOf(value);
    	if(isValidPos(index, true))
    		this._xFrom = index;
    	return this;
    }
    
    public MoveFactory xFrom(int value)
    {
    	if(isValidPos(value, true))
    		this._xFrom = value;
    	return this;
    }


    public MoveFactory yFrom(Character value)
    {
    	int index = _horizontal.indexOf(value);
    	if(isValidPos(index, false))
    		this._yFrom = index;
    	return this;
    }
    
    public MoveFactory yFrom(int value)
    {
    	if(isValidPos(value, false))
    		this._yFrom = value;
    	return this;
    }
    

    public MoveFactory xTo(Character value)
    {
    	int index = _horizontal.indexOf(value);
    	if(isValidPos(index, true))
    		this._xTo = index;
    	return this;
    }
    
    public MoveFactory xTo(int value)
    {
    	if(isValidPos(value, true))
    		this._xTo = value;
    	return this;
    }


    public MoveFactory yTo(Character value)
    {
    	int index = _horizontal.indexOf(value);
    	if(isValidPos(index, false))
    		this._yTo = index;
    	return this;
    }
    
    public MoveFactory yTo(int value)
    {
    	if(isValidPos(value, false))
    		this._yTo = value;
    	return this;
    }

    public MoveFactory from(String value)
    {
    	if(value.length() != 2)
    	{
    		LOG.error("from value incorrecte : " + value );
    		return this;
    	}
    	int index = _horizontal.indexOf(value.getBytes()[0]);
    	if(isValidPos(index, true))
    		this._xFrom = index;
    	index = _vertical.indexOf(value.getBytes()[1]);
    	if(isValidPos(index, false))
    		this._yFrom = index;
    	return this;
    }

    public MoveFactory to(String value)
    {
    	if(value.length() != 2)
    	{
    		LOG.error("from value incorrecte : " + value );
    		return this;
    	}
    	int index = _horizontal.indexOf(value.getBytes()[0]);
    	if(isValidPos(index, true))
    		this._xTo = index;
    	index = _vertical.indexOf(value.getBytes()[1]);
    	if(isValidPos(index, false))
    		this._yTo = index;
    	return this;
    }
    
    public void move()
    {
    	if(isValidPos(_xFrom, true) && isValidPos(_xTo, true) &&
    	isValidPos(_yFrom, false) && isValidPos(_xTo, false))
    	{
    		_board.move(_xFrom, _yFrom, _xTo, _yTo);
    		_xFrom = -1;
    		_yFrom = -1;
    		_xTo = -1;
    		_yTo = -1;
    	}
    	else
    	{
    		LOG.error("incorrect move");
    	}
    }
    
    public boolean isValidPos(int value, boolean horizontal)
    {
    	//TODO
    	return value >= 0 && value < 8;
    }
    
}
