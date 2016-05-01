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
 */
package jchess.display.windows;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jchess.utils.GUI;

/** Class responsible for promotion of a pawn.
 * When pawn reach the end of the chessboard it can be change to rook,
 * bishop, queen or knight. For what pawn is promoted decideds player.
 * @param parent Information about the current piece
 * @param color The player color
 */
public class PawnPromotionWindow extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton knightButton;
	private JButton bishopButton;
	private JButton rookButton;
	private JButton queenButton;
	private JButton arrowButton;
	private GridBagLayout gbl;
	private String result;
	private GridBagConstraints gbc;

    public PawnPromotionWindow(Frame parent, String color)
    {        
        super(parent);
        this.setTitle("Choose piece");
        this.setMinimumSize(new Dimension(520, 130));
        this.setSize(new Dimension(520, 130));
        this.setMaximumSize(new Dimension(520, 130));
        this.setResizable(false);
        this.setLayout(new GridLayout(1, 4));


        this.setGbl(new GridBagLayout());

        this.setGbc(new GridBagConstraints());

        this.knightButton = new JButton();
        this.bishopButton = new JButton();
        this.rookButton = new JButton();
        this.queenButton = new JButton();
        this.arrowButton = new JButton();
        this.setResult("");

        this.knightButton.addActionListener(this);
        this.bishopButton.addActionListener(this);
        this.rookButton.addActionListener(this);
        this.queenButton.addActionListener(this);
        this.arrowButton.addActionListener(this);


        this.add(queenButton);
        this.add(rookButton);
        this.add(bishopButton);
        this.add(knightButton);
        this.add(arrowButton);

        pack();
    }

    /** Method setting the color fo promoted pawn
     * @param color The players color
     */
    public void setColor(String color)
    {
        this.knightButton.setIcon(new ImageIcon(GUI.loadImage("Knight-" + color + ".png")));
        this.bishopButton.setIcon(new ImageIcon(GUI.loadImage("Bishop-" + color + ".png")));
        this.rookButton.setIcon(new ImageIcon(GUI.loadImage("Rook-" + color + ".png")));
        this.queenButton.setIcon(new ImageIcon(GUI.loadImage("Queen-" + color + ".png")));
        this.arrowButton.setIcon(new ImageIcon(GUI.loadImage("Arrow-" + color + ".png")));//TODO change to Arrow image
    }

    /** Method wich is changing a pawn into queen, rook, bishop or knight
     * @param arg0 Capt information about performed action
     */
    public void actionPerformed(ActionEvent arg0)
    {
        if (arg0.getSource() == queenButton)
        {
            setResult("Queen");
        }
        else if (arg0.getSource() == rookButton)
        {
            setResult("Rook");
        }
        else if (arg0.getSource() == bishopButton)
        {
            setResult("Bishop");
        }
        else if (arg0.getSource() == arrowButton)
        {
            setResult("Arrow");
        }
        else //knight
        {
            setResult("Knight");
        }
        this.setVisible(false);
    }

	public GridBagLayout getGbl() {
		return gbl;
	}

	public void setGbl(GridBagLayout gbl) {
		this.gbl = gbl;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public GridBagConstraints getGbc() {
		return gbc;
	}

	public void setGbc(GridBagConstraints gbc) {
		this.gbc = gbc;
	}
}
