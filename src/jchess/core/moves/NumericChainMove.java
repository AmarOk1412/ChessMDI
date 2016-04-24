package jchess.core.moves;

import jchess.core.Chessboard;

public class NumericChainMove extends MoveBuilder {

    public NumericChainMove(Chessboard board) {
    	_board = board;
    }
    
    public NumericChainMove xFrom(int value)
    {
    	this._xFrom = value;
    	return this;
    }
    
    public NumericChainMove yFrom(int value)
    {
    	this._yFrom = value;
    	return this;
    }
    
    public NumericChainMove xTo(int value)
    {
    	this._xTo = value;
    	return this;
    }

    public NumericChainMove yTo(int value)
    {
    	this._yTo = value;
    	return this;
    }
}
