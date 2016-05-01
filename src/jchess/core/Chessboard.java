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

 */
package jchess.core;

import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Knight;
import jchess.core.pieces.implementation.Queen;
import jchess.core.pieces.implementation.Rook;
import jchess.core.setpieces.ClassicalSetPieces;
import jchess.core.setpieces.FullPawnSetPiece;
import jchess.core.setpieces.RandomSetPieces;
import jchess.core.setpieces.SetPieces;
import jchess.core.visitor.ChessboardVisitor;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Arrow;
import jchess.core.pieces.implementation.Bishop;
import jchess.core.pieces.Piece;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Set;
import jchess.JChessApp;
import jchess.core.moves.Castling;
import jchess.core.moves.Move;
import jchess.core.moves.Moves;
import jchess.display.views.chessboard.implementation.graphic2D.Chessboard2D;
import jchess.display.views.chessboard.ChessboardView;
import jchess.utils.Settings;
import org.apache.log4j.*;

/**
 * @author: Mateusz Sławomir Lach ( matlak, msl )
 * @author: Damian Marciniak Class to represent chessboard. Chessboard is made
 *          from squares. It is setting the squers of chessboard and sets the
 *          pieces(pawns) witch the owner is current player on it.
 */
public class Chessboard {
	public static final Logger LOG = Logger.getLogger(Chessboard.class);

	protected static final int TOP = 0;

	protected static final int BOTTOM = 7;

	/*
	 * squares of chessboard
	 */
	protected Square squares[][] = null;

	private Set<Square> moves;

	private Settings settings;

	protected King kingWhite;

	protected King kingBlack;

	// For En passant:
	// |-> Pawn whose in last turn moved two square
	protected Pawn twoSquareMovedPawn = null;

	private Moves Moves;

	protected Square activeSquare;

	protected int activeSquareX;

	protected int activeSquareY;

	/**
	 * chessboard view data object
	 */
	private ChessboardView chessboardView;

	/**
	 * Chessboard class constructor
	 * 
	 * @param settings
	 *            reference to Settings class object for this chessboard
	 * @param moves_history
	 *            reference to Moves class object for this chessboard
	 */
	public Chessboard(Settings settings, Moves Moves) {
		this.settings = settings;
		this.chessboardView = new Chessboard2D(this);

		this.activeSquareX = 0;
		this.activeSquareY = 0;

		if (settings.getSize() < 8) {
			Chessboard.LOG.log(Level.ERROR, "settings.getSize() < 8. Can't initialize. Exit");
		}

		initSquares();
		this.Moves = Moves;

	}/*--endOf-Chessboard--*/

	/**
	 * @return the top
	 */
	public static int getTop() {
		return TOP;
	}

	/**
	 * @return the bottom
	 */
	public static int getBottom() {
		return BOTTOM;
	}

	public int getSize() {
		return settings.getSize();
	}

	/**
	 * Method setPieces on begin of new game or loaded game
	 * 
	 * @param places
	 *            string with pieces to set on chessboard
	 * @param plWhite
	 *            reference to white player
	 * @param plBlack
	 *            reference to black player
	 */
	public void setPieces(String places, Player plWhite, Player plBlack) {
		if (places.equals("")) // if newGame
		{
			SetPieces setPieces = null;
			switch (settings.getTypeMode()) {
			case "classical":
				setPieces = new ClassicalSetPieces(this, settings.getSize());
				break;
			case "random":
				setPieces = new RandomSetPieces(this, settings.getSize());
				break;
			case "fullpawn":
				setPieces = new FullPawnSetPiece(this, settings.getSize());
				break;
			default:
				setPieces = new ClassicalSetPieces(this, settings.getSize());
				break;
			}
			setPieces.setPieces4NewGame(plWhite, plBlack);
		} else // if loadedGame
		{
			return;
		}
	}/*--endOf-setPieces--*/

	/**
	 * Method selecting piece in chessboard
	 * 
	 * @param sq
	 *            square to select (when clicked))
	 */
	public void select(Square sq) {
		this.setActiveSquare(sq);
		this.setActiveSquareX(sq.getPozX() + 1);
		this.setActiveSquareY(sq.getPozY() + 1);

		LOG.debug("active_x: " + this.getActiveSquareX() + " active_y: " + this.getActiveSquareY());// 4tests
		this.getChessboardView().repaint();
	}/*--endOf-select--*/

	public void unselect() {
		this.setActiveSquareX(0);
		this.setActiveSquareY(0);
		this.setActiveSquare(null);

		this.getChessboardView().unselect();
	}/*--endOf-unselect--*/

	public void resetActiveSquare() {
		this.setActiveSquare(null);
	}

	public void move(Square begin, Square end) {
		move(begin, end, true, true);
	}

	/**
	 * Method to move piece over chessboard
	 * 
	 * @param xFrom
	 *            from which x move piece
	 * @param yFrom
	 *            from which y move piece
	 * @param xTo
	 *            to which x move piece
	 * @param yTo
	 *            to which y move piece
	 */
	public void move(int xFrom, int yFrom, int xTo, int yTo) {
		Square fromSQ = null;
		Square toSQ = null;
		try {
			fromSQ = this.getSquares()[xFrom][yFrom];
			toSQ = this.getSquares()[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			LOG.error("error moving piece: " + exc.getMessage());
			return;
		}
		this.move(fromSQ, toSQ, true, true);
	}
	
	/**
	 * A move function for test unit
	 * @param xFrom
	 * @param yFrom
	 * @param xTo
	 * @param yTo
	 */
	public void testMove(int xFrom, int yFrom, int xTo, int yTo) {
		Square fromSQ = null;
		Square toSQ = null;
		try {
			fromSQ = this.getSquares()[xFrom][yFrom];
			toSQ = this.getSquares()[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			LOG.error("error moving piece: " + exc.getMessage());
			return;
		}
		toSQ.setPiece(null);
		toSQ.setPiece(fromSQ.getPiece());
		fromSQ.getPiece().setSquare(toSQ);
		fromSQ.setPiece(null);
	}

	/**
	 * Method move piece from square to square
	 * 
	 * @param begin
	 *            square from which move piece
	 * @param end
	 *            square where we want to move piece *
	 * @param refresh
	 *            chessboard, default: true
	 */
	public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory) {
		Castling wasCastling = Castling.NONE;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.getPiece() != null) {
			end.getPiece().setSquare(null);
		}

		Square tempBegin = new Square(begin);// 4 moves history
		Square tempEnd = new Square(end); // 4 moves history

		begin.getPiece().setSquare(end);// set square of piece to ending
		end.setPiece(begin.getPiece());// for ending square set piece from beginin
								// square
		begin.setPiece(null);// make null piece for begining square

		if (end.getPiece().getName().equals("King")) {
			if (!((King) end.getPiece()).getWasMotioned()) {
				((King) end.getPiece()).setWasMotioned(true);
			}

			// Castling
			if (begin.getPozX() + 2 == end.getPozX()) {
				move(getSquare(7, begin.getPozY()), getSquare(end.getPozX() - 1, begin.getPozY()), false, false);
				wasCastling = Castling.SHORT_CASTLING;
			} else if (begin.getPozX() - 2 == end.getPozX()) {
				move(getSquare(0, begin.getPozY()), getSquare(end.getPozX() + 1, begin.getPozY()), false, false);
				wasCastling = Castling.LONG_CASTLING;
			}
			// endOf Castling
		} else if (end.getPiece().getName().equals("Rook")) {
			if (!((Rook) end.getPiece()).getWasMotioned()) {
				((Rook) end.getPiece()).setWasMotioned(true);
			}
		} else if (end.getPiece().getName().equals("Pawn")) {
			if (getTwoSquareMovedPawn() != null
					&& getSquares()[end.getPozX()][begin.getPozY()] == getTwoSquareMovedPawn().getSquare()) // en
																											// passant
			{
				tempEnd.setPiece(getSquares()[end.getPozX()][begin.getPozY()].getPiece()); // ugly
																					// hack
																					// -
																					// put
																					// taken
																					// pawn
																					// in
																					// en
																					// passant
																					// plasty
																					// do
																					// end
																					// square

				squares[end.pozX][begin.pozY].setPiece(null);
				wasEnPassant = true;
			}

			if (begin.getPozY() - end.getPozY() == 2 || end.getPozY() - begin.getPozY() == 2) // moved
																								// two
																								// square
			{
				twoSquareMovedPawn = (Pawn) end.getPiece();
			} else {
				twoSquareMovedPawn = null; // erase last saved move (for En
											// passant)
			}

			if (end.getPiece().getSquare().getPozY() == 0
					|| end.getPiece().getSquare().getPozY() == settings.getSize() - 1) // promote
																						// Pawn
			{
				if (clearForwardHistory) {
					String color = String
							.valueOf(end.getPiece().getPlayer().getColor().getSymbolAsString().toUpperCase());
					String newPiece = "Queen";
					if(!end.getPiece().getPlayer().getPlayerType().equals(Player.playerTypes.computer))
					newPiece = JChessApp.getJavaChessView().showPawnPromotionBox(color); // return
																								// name
																								// of
																								// new
																								// piece

					Piece piece;
					switch (newPiece) {
					case "Queen":
						piece = new Queen(this, end.getPiece().getPlayer());
						break;
					case "Arrow":
						piece = new Arrow(this, end.getPiece().getPlayer());
						break;
					case "Rook":
						piece = new Rook(this, end.getPiece().getPlayer());
						break;
					case "Bishop":
						piece = new Bishop(this, end.getPiece().getPlayer());
						break;
					default:
						piece = new Knight(this, end.getPiece().getPlayer());
						break;
					}
					piece.setChessboard(end.getPiece().getChessboard());
					piece.setPlayer(end.getPiece().getPlayer());
					piece.setSquare(end.getPiece().getSquare());
					end.setPiece(piece);
					promotedPiece = end.getPiece();
				}
			}
		} else if (!end.getPiece().getName().equals("Pawn")) {
			twoSquareMovedPawn = null; // erase last saved move (for En passant)
		}

		if (refresh) {
			this.unselect();// unselect square
			repaint();
		}

		Integer duration = this.Moves.getGame().getGameClock().getTime4Game();
		for (Move m : this.Moves.getHistoryOfMoves())
			duration -= new Integer(m.getDuration());
		duration -= this.Moves.getGame().getGameClock().getLeftTime();
		if (clearForwardHistory) {
			this.Moves.clearMoveForwardStack();
			this.Moves.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece, duration.toString(),
					Moves.getGame().getActivePlayer().getName());
		} else {
			this.Moves.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece, duration.toString(),
					Moves.getGame().getActivePlayer().getName());
		}
	}/* endOf-move()- */

	public boolean redo() {
		return redo(true);
	}

	public boolean redo(boolean refresh) {
		if (this.getSettings().getGameType() == Settings.gameTypes.local) // redo
																			// only
																			// for
																			// local
																			// game
		{
			Move first = this.Moves.redo();

			Square from = null;
			Square to = null;

			if (first != null) {
				from = first.getFrom();
				to = first.getTo();

				this.move(this.getSquares()[from.getPozX()][from.getPozY()],
						this.getSquares()[to.getPozX()][to.getPozY()], true, false);
				if (first.getPromotedPiece() != null) {
					Pawn pawn = (Pawn) this.getSquares()[to.getPozX()][to.getPozY()].getPiece();
					pawn.setSquare(null);

					this.squares[to.pozX][to.pozY].setPiece(first.getPromotedPiece());
					Piece promoted = this.getSquares()[to.getPozX()][to.getPozY()].getPiece();
					promoted.setSquare(this.getSquares()[to.getPozX()][to.getPozY()]);
				}
				return true;
			}

		}
		return false;
	}

	public boolean undo() {
		return undo(true);
	}

	public synchronized boolean undo(boolean refresh) // undo last move
	{
		Move last = this.Moves.undo();

		if (last != null && last.getFrom() != null) {
			Square begin = last.getFrom();
			Square end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				this.squares[begin.pozX][begin.pozY].setPiece(moved);
				moved.setSquare(this.getSquares()[begin.getPozX()][begin.getPozY()]);

				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != Castling.NONE) {
					Piece rook = null;
					if (last.getCastlingMove() == Castling.SHORT_CASTLING) {
						rook = this.getSquares()[end.getPozX() - 1][end.getPozY()].getPiece();
						this.squares[7][begin.pozY].setPiece(rook);
						rook.setSquare(this.getSquares()[7][begin.getPozY()]);
						this.squares[end.pozX - 1][end.pozY].setPiece(null);
					} else {
						rook = this.getSquares()[end.getPozX() + 1][end.getPozY()].getPiece();
						this.squares[0][begin.pozY].setPiece(rook);
						rook.setSquare(this.getSquares()[0][begin.getPozY()]);
						this.squares[end.pozX + 1][end.pozY].setPiece(null);
					}
					((King) moved).setWasMotioned(false);
					((Rook) rook).setWasMotioned(false);
				} else if (moved.getName().equals("Rook")) {
					((Rook) moved).setWasMotioned(false);
				} else if (moved.getName().equals("Pawn") && last.wasEnPassant()) {
					Pawn pawn = (Pawn) last.getTakenPiece();
					this.squares[end.pozX][begin.pozY].setPiece(pawn);
					pawn.setSquare(this.getSquares()[end.getPozX()][begin.getPozY()]);

				} else if (moved.getName().equals("Pawn") && last.getPromotedPiece() != null) {
					Piece promoted = this.getSquares()[end.getPozX()][end.getPozY()].getPiece();
					promoted.setSquare(null);
					this.squares[end.pozX][end.pozY].setPiece(null);
				}

				// check one more move back for en passant
				Move oneMoveEarlier = this.Moves.getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Piece canBeTakenEnPassant = this
							.getSquare(oneMoveEarlier.getTo().getPozX(), oneMoveEarlier.getTo().getPozY()).getPiece();
					if (canBeTakenEnPassant.getName().equals("Pawn")) {
						this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					this.squares[end.pozX][end.pozY].setPiece(taken);
					taken.setSquare(this.getSquares()[end.getPozX()][end.getPozY()]);
				} else {
					this.squares[end.pozX][end.pozY].setPiece(null);
				}

				if (refresh) {
					this.unselect();// unselect square
					repaint();
				}

			} catch (ArrayIndexOutOfBoundsException | NullPointerException exc) {
				LOG.error("error: " + exc.getClass() + " exc object: " + exc);
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public void componentMoved(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void componentShown(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void componentHidden(ComponentEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the squares
	 */
	public Square[][] getSquares() {
		return squares;
	}

	public Square getSquare(String pos) {
		String _horizontal = "abcdefgh";
		String _vertical = "87654321";
		try {
			if (pos.length() != 2) {
				LOG.error("from value incorrecte : " + pos);
				return null;
			}
			int x = _horizontal.indexOf(pos.getBytes()[0]);
			int y = _vertical.indexOf(pos.getBytes()[1]);
			return this.getSquare(x, y);
		} catch (ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}

	public Square getSquare(int x, int y) {
		try {
			return squares[x][y];
		} catch (ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}

	/**
	 * @return the activeSquare
	 */
	public Square getActiveSquare() {
		return activeSquare;
	}

	public ArrayList<Piece> getAllPieces(Colors color) {
		ArrayList<Piece> result = new ArrayList<>();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				Square sq = squares[i][j];
				if (null != sq.getPiece() && (sq.getPiece().getPlayer().getColor() == color || color == null)) {
					result.add(sq.getPiece());
				}
			}
		}
		return result;
	}

	public static boolean wasEnPassant(Square sq) {
		return sq.getPiece() != null && sq.getPiece().getChessboard().getTwoSquareMovedPawn() != null
				&& sq == sq.getPiece().getChessboard().getTwoSquareMovedPawn().getSquare();
	}

	/**
	 * @return the kingWhite
	 */
	public King getKingWhite() {
		return kingWhite;
	}

	public void setKingWhite(King king) {
		kingWhite = king;
	}

	/**
	 * @return the kingBlack
	 */
	public King getKingBlack() {
		return kingBlack;
	}

	public void setKingBlack(King king) {
		kingBlack = king;
	}

	/**
	 * @return the twoSquareMovedPawn
	 */
	public Pawn getTwoSquareMovedPawn() {
		return twoSquareMovedPawn;
	}

	/**
	 * @return the chessboardView
	 */
	public ChessboardView getChessboardView() {
		return chessboardView;
	}

	/**
	 * @param chessboardView
	 *            the chessboardView to set
	 */
	public void setChessboardView(ChessboardView chessboardView) {
		this.chessboardView = chessboardView;
	}

	public void repaint() {
		getChessboardView().repaint();
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	/**
	 * @return the moves
	 */
	public Set<Square> getMoves() {
		return moves;
	}

	/**
	 * @param moves
	 *            the moves to set
	 */
	public void setMoves(Set<Square> moves) {
		this.moves = moves;
	}

	/**
	 * @param activeSquare
	 *            the activeSquare to set
	 */
	public void setActiveSquare(Square activeSquare) {
		this.activeSquare = activeSquare;
	}

	/**
	 * @return the activeSquareX
	 */
	public int getActiveSquareX() {
		return activeSquareX;
	}

	/**
	 * @param activeSquareX
	 *            the activeSquareX to set
	 */
	public void setActiveSquareX(int activeSquareX) {
		this.activeSquareX = activeSquareX;
	}

	/**
	 * @return the activeSquareY
	 */
	public int getActiveSquareY() {
		return activeSquareY;
	}

	/**
	 * @param activeSquareY
	 *            the activeSquareY to set
	 */
	public void setActiveSquareY(int activeSquareY) {
		this.activeSquareY = activeSquareY;
	}

	public void accept(ChessboardVisitor visitor) {
		for (int i = 0; i < settings.getSize(); i++) // create object for each
														// square
		{
			for (int y = 0; y < settings.getSize(); y++) {
				squares[i][y].accept(visitor);
			}
		}
		visitor.visit(this);
	}

	public void initSquares() {

		this.squares = new Square[settings.getSize()][settings.getSize()];

		for (int i = 0; i < settings.getSize(); i++) // create object for each
														// square
		{
			for (int y = 0; y < settings.getSize(); y++) {
				this.squares[i][y] = new Square(i, y, null);
			}
		} // --endOf--create object for each square
	}
}
