package jchess.core.computerai;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.Square;
import jchess.core.pieces.Piece;

public class MinMaxComputerPlayer extends ComputerPlayer {

	public MinMaxComputerPlayer(Colors color) {
		super(color);
	}

	@Override
	public void move(Chessboard board) {
		// Get all pieces
		Map<Entry<Piece, Square>, Integer> pieces = getPossibleMoves(board);
		// Sort Map by score (value)
		pieces = sortByValue(pieces);
		// Get random move in this case
		List<Entry<Piece, Square>> bestMoves = new ArrayList<>();
		Iterator<Entry<Entry<Piece, Square>, Integer>> iter = pieces.entrySet().iterator();
		// Init
		Entry<Entry<Piece, Square>, Integer> best = iter.next();
		bestMoves.add(best.getKey());
		int bestScore = best.getValue();
		while (iter.hasNext()) {
			Entry<Entry<Piece, Square>, Integer> current = iter.next();
			if (current.getValue() != bestScore)
				break;
			bestMoves.add(current.getKey());
		}
		// pick one
		Collections.shuffle(bestMoves);
		Entry<Piece, Square> maxMove = getMaxMove(bestMoves);
		Piece pieceToMove = maxMove.getKey();
		Square fromSquare = pieceToMove.getSquare();
		Square endSquare = maxMove.getValue();
		// Move
		Chessboard.LOG.info(_color + ": Move " + pieceToMove.getName() + " : from " + fromSquare.getPozX() + "-"
				+ fromSquare.getPozY() + " : to " + endSquare.getPozX() + "-" + endSquare.getPozY());
		board.move(fromSquare, endSquare);
	}

	// Map<Entry<Piece, possibleSquare>, scoreOther>
	private Map<Entry<Piece, Square>, Integer> getPossibleMoves(Chessboard board) {

		Map<Entry<Piece, Square>, Integer> result = new HashMap<Entry<Piece, Square>, Integer>();
		Square[][] squares = board.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				Piece p = squares[i][j].getPiece();
				if (null != p && (p.getPlayer().getColor() == _color)) {
					Set<Square> possibleSquare = p.getAllMoves();
					Iterator<Square> iter = possibleSquare.iterator();
					while (iter.hasNext()) {
						Square sq = iter.next();
						Entry<Piece, Square> couple = new AbstractMap.SimpleEntry<Piece, Square>(p, sq);

						//simulate Move
						Square begin = p.getSquare();
						begin.setPiece(null);
						Piece endPiece = sq.getPiece();
						sq.setPiece(p);
						//Get max score for other
						int scorePiece = getMaxScore(board);
						//Replace
						begin.setPiece(p);
						sq.setPiece(endPiece);
						result.put(couple, scorePiece);
					}
				}
			}
		}
		return result;
	}

	private int getMaxScore(Chessboard board) {
		int result = 0;
		Square[][] squares = board.getSquares();
        for(int i=0; i<squares.length; i++)
        {
            for(int j=0; j<squares[i].length; j++)
            {
                Piece p = squares[i][j].getPiece();
                if(null != p && (p.getPlayer().getColor() != _color))
                {
                	Set<Square> possibleSquare = p.getAllMoves();
                	Iterator<Square> iter = possibleSquare.iterator();
                	while (iter.hasNext()) {
                		Square sq = iter.next();

            			int scorePiece = 0;
            			Piece future = sq.getPiece();
            			if(future != null)
            				scorePiece = future.getScore();
            			
            			if(scorePiece > result)
            				result = scorePiece;
                	}
                }
            }
        }       
		return result;
	}
	
	private Entry<Piece, Square> getMaxMove(List<Entry<Piece, Square>> moves) {
		Entry<Piece, Square> result = moves.get(0);
		Iterator<Entry<Piece, Square>> iter = moves.iterator();
		int maxScore = 0;
		while(iter.hasNext())
		{
			Entry<Piece, Square> pieceAndSquare = iter.next();
			int scorePiece = 0;
			Piece future = pieceAndSquare.getValue().getPiece();
			if(future != null)
				scorePiece = future.getScore();
			
			if(maxScore < scorePiece) {
				maxScore = scorePiece;
				result = pieceAndSquare;
			}
		}
		
		return result;
		
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				return (e1.getValue()).compareTo(e2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
