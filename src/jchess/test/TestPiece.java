package jchess.test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import jchess.core.Chessboard;
import jchess.core.Colors;
import jchess.core.ComputerPlayer;
import jchess.core.Game;
import jchess.core.GloutonComputerPlayer;
import jchess.core.RandomComputerPlayer;
import jchess.core.ScoringVisitor;
import jchess.core.Square;
import jchess.core.TypeVisitor;
import jchess.core.moves.AlgebricChainMove;
import jchess.core.pieces.Piece;
import jchess.core.pieces.implementation.Bishop;
import jchess.core.pieces.implementation.King;
import jchess.core.pieces.implementation.Knight;
import jchess.core.pieces.implementation.Pawn;
import jchess.core.pieces.implementation.Queen;
import jchess.core.pieces.implementation.Rook;
import jchess.utils.Settings;

/**
 * Created by macher1 on 12/04/2015.
 */
public class TestPiece {

    private  Settings settings;

    private  Chessboard board;

    @Before
    public void setUp() {
        //SingleFrameApplication.launch(JChessApp.class, new String[] {});

        settings = new Settings();
        board = new Game().getChessboard(); // new Chessboard(settings, new Moves(new Game()));


        // Game g = new Game();
        // #1 bad API design
        // g.newGame(); // fails because coupled to GUI concerns and tabs stuff
        // anyway
        board.setPieces("", settings.getPlayerWhite(), settings.getPlayerBlack());


        // #2 bad API design
        //  Moves moves = new Moves(g);
        // Chessboard board = new Chessboard(settings, moves);
        // g.getChessboard() != board :(
        // board.getMoves() != moves :(


    }

    @Test
    public void testInitBoard() throws Exception {
        assertEquals(16, board.getAllPieces(Colors.WHITE).size());
        assertEquals(16, board.getAllPieces(Colors.BLACK).size());
        // #3 bad API design
        // assertNotNull(board.getMoves());
    }

    @Test
    public void testBasicMovement() throws Exception {


        Square sq = board.getSquare("f7"); // 1st rown (black relative)
        Piece p = sq.getPiece();
        assertTrue(p instanceof Pawn);
        assertEquals(Colors.BLACK, p.getPlayer().getColor());

        Piece p2 = board.getSquare("f2").getPiece(); // 6th row (black relative)
        assertNotNull(p2);
        assertTrue(p2 instanceof Pawn);
        assertEquals(Colors.WHITE, p2.getPlayer().getColor());

        assertEquals(2, p2.getAllMoves().size()); // e2e3 or e2e4

        Piece p3 = board.getSquare("e1").getPiece(); // 7th row (black relative)
        assertNotNull(p3);
        assertTrue(p3 instanceof King);
        assertEquals(Colors.WHITE, p3.getPlayer().getColor());

        assertEquals(0, p3.getAllMoves().size()); // no legal move


        assertNull(board.getSquare("d5").getPiece()); // nothing there
        // e2 (4, 6) e4 (4, 4)
        new AlgebricChainMove(board).from("e2").to("e4").move();

        // #4 bad API design
        //assertEquals(1, board.getMoves().size());

        assertNull(board.getSquare("e2").getPiece()); // now the pawn is not present in e2
        Piece p4 = board.getSquare("e4").getPiece(); // and there is a pawn in e4
        assertTrue(p4 instanceof Pawn);
        assertEquals(Colors.WHITE, p4.getPlayer().getColor());





    }

    @Test
    public void testBishop1() throws Exception {
        new AlgebricChainMove(board).from("e2").to("e4").move();
        new AlgebricChainMove(board).from("e7").to("e5").move();


        assertNull(board.getSquare("e7").getPiece()); // now the pawn is not present in e7
        Piece p1 = board.getSquare("e5").getPiece(); // and there is a pawn in e5
        assertTrue(p1 instanceof Pawn);
        assertEquals(Colors.BLACK, p1.getPlayer().getColor());

        Piece b1 = board.getSquare("f1").getPiece();
        assertTrue(b1 instanceof Bishop);
        assertEquals(Colors.WHITE, b1.getPlayer().getColor());

        assertEquals(5, b1.getAllMoves().size());
    }

    @Test
    public void testBishop2() throws Exception {
        new AlgebricChainMove(board).from("d2").to("d4").move();
        new AlgebricChainMove(board).from("e7").to("e5").move();

        // bishop in c1
        Piece b1 = board.getSquare("c1").getPiece();
        assertTrue(b1 instanceof Bishop);
        assertEquals(Colors.WHITE, b1.getPlayer().getColor());

        assertEquals(5, b1.getAllMoves().size());
    }

    @Test
    public void testKnight() throws Exception {
        Piece knight = board.getSquare("b1").getPiece();
        assertTrue(knight instanceof Knight);
        assertEquals(Colors.WHITE, knight.getPlayer().getColor());
        assertEquals(2, knight.getAllMoves().size());
        
        new AlgebricChainMove(board).from("b1").to("c3").move();
        new AlgebricChainMove(board).from("e7").to("e5").move();
        knight = board.getSquare("c3").getPiece();
        assertTrue(knight instanceof Knight);
        assertEquals(Colors.WHITE, knight.getPlayer().getColor());
        assertEquals(5, knight.getAllMoves().size());

    }

    @Test
    public void testPawn1() throws Exception {
        Piece pawn = board.getSquare(1, 1).getPiece();
        assertTrue(pawn instanceof Pawn);
        assertEquals(Colors.BLACK, pawn.getPlayer().getColor());
        assertEquals(2, pawn.getAllMoves().size());
    }
    

    @Test
    public void testRook() throws Exception {
        new AlgebricChainMove(board).from("a2").to("a4").move();
        new AlgebricChainMove(board).from("a7").to("a5").move();
        
        Piece rook = board.getSquare("a1").getPiece();
        assertTrue(rook instanceof Rook);
        assertEquals(Colors.WHITE, rook.getPlayer().getColor());
        assertEquals(2, rook.getAllMoves().size());
        
        new AlgebricChainMove(board).from("a1").to("a3").move();
        new AlgebricChainMove(board).from("a8").to("a6").move();
        rook = board.getSquare("a3").getPiece();
        assertTrue(rook instanceof Rook);
        assertEquals(Colors.WHITE, rook.getPlayer().getColor());
        assertEquals(9, rook.getAllMoves().size());

    }
    

    @Test
    public void testChess1() throws Exception {
        new AlgebricChainMove(board).from("d2").to("d4").move();
        new AlgebricChainMove(board).from("d7").to("d5").move();
        new AlgebricChainMove(board).from("d1").to("d3").move();
        new AlgebricChainMove(board).from("d8").to("d6").move();
        new AlgebricChainMove(board).from("d3").to("b5").move();
        Piece king = board.getSquare("e8").getPiece();
        assertTrue(king instanceof King);
        assertEquals(Colors.BLACK, king.getPlayer().getColor());
        assertEquals(1, king.getAllMoves().size());
        Piece bishop = board.getSquare("c8").getPiece();
        assertTrue(bishop instanceof Bishop);
        assertEquals(Colors.BLACK, bishop.getPlayer().getColor());
        assertEquals(1, bishop.getAllMoves().size());
        Piece queen = board.getSquare("d6").getPiece();
        assertTrue(queen instanceof Queen);
        assertEquals(Colors.BLACK, queen.getPlayer().getColor());
        assertEquals(2, queen.getAllMoves().size());
        Piece pawn = board.getSquare("b7").getPiece();
        assertTrue(pawn instanceof Pawn);
        assertEquals(Colors.BLACK, pawn.getPlayer().getColor());
        assertEquals(0, pawn.getAllMoves().size());
    }
    
    @Test
    public void testVisitor()
    {
    	board.accept(new ScoringVisitor());
    	board.accept(new TypeVisitor());
    }
    

    @Test
    public void testAI()
    {
    	ComputerPlayer pW = new GloutonComputerPlayer(Colors.WHITE);
    	ComputerPlayer pB = new RandomComputerPlayer(Colors.BLACK);
    	
    	try
    	{
	    	for(int i = 0; i < 1000; ++i)
	    	{
	    		pW.move(board);
	    		pB.move(board);
	    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println("gagné, nul ou promotion");
    	}
    }
}
