package jchess.core.visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class TypeVisitor implements ChessboardVisitor {

	private Map<String, Integer> whitePieces;
	private Map<String, Integer> blackPieces;
	
	public TypeVisitor() {
		whitePieces = new HashMap<>();
		blackPieces = new HashMap<>();
	}
	
	@Override
	public void visit(Chessboard chessboard) {
		StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> iter = whitePieces.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        Chessboard.LOG.info("whitePieces: " + sb.toString());
        
        sb = new StringBuilder();
        iter = blackPieces.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        Chessboard.LOG.info("blackPieces: " + sb.toString());
		
	}

	@Override
	public void visit(Square square) {
		Piece p = square.getPiece();
		if(p != null)
		{
			String name = p.getName();
			if(p.getPlayer().getColor() == Colors.WHITE)
			{
				if(whitePieces.containsKey(name))
				{
					int updateValue = whitePieces.get(name) + 1;
					whitePieces.replace(name, updateValue);
				}
				else
				{
					whitePieces.put(name, 1);
				}
			}
			else
			{
				if(blackPieces.containsKey(name))
				{
					int updateValue = blackPieces.get(name) + 1;
					blackPieces.replace(name, updateValue);
				}
				else
				{
					blackPieces.put(name, 1);
				}
			}
		}	
	}

}
