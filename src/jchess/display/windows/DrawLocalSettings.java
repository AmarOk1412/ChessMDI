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

package jchess.display.windows;

import jchess.JChessApp;
import jchess.core.Game;
import jchess.core.Player;
import jchess.core.computerai.GloutonComputerPlayer;
import jchess.core.computerai.MinMaxComputerPlayer;
import jchess.core.computerai.RandomComputerPlayer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.*;
import javax.swing.text.BadLocationException;
import jchess.utils.Settings;
import org.apache.log4j.Logger;

/**
 * @author: Mateusz Sławomir Lach ( matlak, msl )
 * Class responsible for drawing the fold with local game settings
 */
public class DrawLocalSettings extends JPanel implements ActionListener, TextListener
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(DrawLocalSettings.class);
    
	private JDialog parent;//needet to close newGame window
	private JComboBox<String> color;//to choose color of player
	private JRadioButton oponentComp;//choose oponent
	private JRadioButton oponentHuman;//choose oponent (human)
	private ButtonGroup oponentChoos;//group 4 radio buttons
	private JLabel compLevLab;
	private JSlider computerLevel;//slider to choose jChess Engine level
	private JTextField firstName;//editable field 4 nickname
	private JTextField secondName;//editable field 4 nickname
	private JLabel firstNameLab;
	private JLabel secondNameLab;
	private JCheckBox upsideDown;//if true draw chessboard upsideDown(white on top)
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private JButton okButton;
	private JCheckBox timeGame;
	private JComboBox<String> time4Game;
	private JLabel chessboardSizeLab;
    private JComboBox<String> sizeChessboard;
    private JLabel gameModeLab;
    private JComboBox<String> gameMode;
    
    private String colors[] =
    {
        Settings.lang("white"), Settings.lang("black")
    };
    
    private String times[] =
    {
        "1", "3", "5", "8", "10", "15", "20", "25", "30", "60", "120"
    };
    
    private String sizes[] =
    {
        "8", "10", "12", "15", "20"
    };

    private String modes[] =
    {
        "classical", "random", "fullpawn"
    };
    
    
    
    public DrawLocalSettings(JDialog parent)
    {
        super();
        Settings actualSettings = JChessApp.getJavaChessView().getActiveTabGame().getSettings();
        
        this.parent = parent;
        this.color = new JComboBox<String>(colors);
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        new JSeparator();
        this.okButton = new JButton(Settings.lang("ok"));
        this.compLevLab = new JLabel(Settings.lang("computer_level"));

        this.firstName = new JTextField(actualSettings.getPlayerWhite().getName(), 10);
        this.firstName.setSize(new Dimension(200, 50));
        this.secondName = new JTextField(actualSettings.getPlayerBlack().getName(), 10);
        this.secondName.setSize(new Dimension(200, 50));
        this.firstNameLab = new JLabel(Settings.lang("first_player_name") + ": ");
        this.secondNameLab = new JLabel(Settings.lang("second_player_name") + ": ");
        this.oponentChoos = new ButtonGroup();
        this.computerLevel = new JSlider();
        this.upsideDown = new JCheckBox(Settings.lang("upside_down"));
        if(actualSettings.isUpsideDown()) this.upsideDown.setSelected(true);
        this.timeGame = new JCheckBox(Settings.lang("time_game_min"));
        if(actualSettings.isTimeLimitSet()) this.timeGame.setSelected(true);
        this.time4Game = new JComboBox<String>(times);

        this.chessboardSizeLab = new JLabel(Settings.lang("chessboard_size") + ": ");
        this.sizeChessboard = new JComboBox<String>(sizes);
        this.gameModeLab = new JLabel(Settings.lang("game_mode") + ": ");
        this.gameMode = new JComboBox<String>(modes);

        this.oponentComp = new JRadioButton(Settings.lang("against_computer"), false);
        this.oponentHuman = new JRadioButton(Settings.lang("against_other_human"), true);

        this.setLayout(gbl);
        this.oponentComp.addActionListener(this);
        this.oponentHuman.addActionListener(this);
        this.okButton.addActionListener(this);

        this.secondName.addActionListener(this);

        this.oponentChoos.add(oponentComp);
        this.oponentChoos.add(oponentHuman);
        this.computerLevel.setMaximum(3);
        this.computerLevel.setMinimum(1);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbl.setConstraints(oponentComp, gbc);
        this.add(oponentComp);
        this.gbc.gridx = 1;
        this.gbl.setConstraints(oponentHuman, gbc);
        this.add(oponentHuman);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbl.setConstraints(firstNameLab, gbc);
        this.add(firstNameLab);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbl.setConstraints(firstName, gbc);
        this.add(firstName);
        this.gbc.gridx = 1;
        this.gbc.gridy = 2;
        this.gbl.setConstraints(color, gbc);
        this.add(color);
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbl.setConstraints(secondNameLab, gbc);
        this.add(secondNameLab);
        this.gbc.gridy = 4;
        this.gbl.setConstraints(secondName, gbc);
        this.add(secondName);
        this.gbc.gridy = 5;
        this.gbc.insets = new Insets(0, 0, 0, 0);
        this.gbl.setConstraints(compLevLab, gbc);
        this.add(compLevLab);
        this.gbc.gridy = 6;
        this.gbl.setConstraints(computerLevel, gbc);
        this.add(computerLevel);
        this.gbc.gridy = 7;
        this.gbl.setConstraints(upsideDown, gbc);
        this.add(upsideDown);
        this.gbc.gridy = 8;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(timeGame, gbc);
        this.add(timeGame);
        this.gbc.gridx = 1;
        this.gbc.gridy = 8;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(time4Game, gbc);
        this.add(time4Game);
        this.gbc.gridx = 0;
        this.gbc.gridy = 9;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(chessboardSizeLab, gbc);
        this.add(chessboardSizeLab);
        this.gbc.gridx = 1;
        this.gbc.gridy = 9;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(sizeChessboard, gbc);
        this.add(sizeChessboard);
        this.gbc.gridx = 0;
        this.gbc.gridy = 10;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(gameModeLab, gbc);
        this.add(gameModeLab);
        this.gbc.gridx = 1;
        this.gbc.gridy = 10;
        this.gbc.gridwidth = 1;
        this.gbl.setConstraints(gameMode, gbc);
        this.add(gameMode);
        this.gbc.gridx = 0;
        this.gbc.gridy = 11;
        this.gbc.gridwidth = 0;
        this.gbl.setConstraints(okButton, gbc);
        this.add(okButton);

    }
        
    /** 
     * Method witch is checking correction of edit tables
     * @param e Object where is saving this what contents edit tables
     */
    @Override
    public void textValueChanged(TextEvent e)
    {
        Object target = e.getSource();
        if (target == this.firstName || target == this.secondName)
        {
            JTextField temp = new JTextField();
            if (target == this.firstName)
            {
                temp = this.firstName;
            }
            else if (target == this.secondName)
            {
                temp = this.secondName;
            }

            int len = temp.getText().length();
            if (len > 8)
            {
                try
                {
                    temp.setText(temp.getText(0, 7));
                }
                catch (BadLocationException exc)
                {
                    LOG.error("BadLocationException: Something wrong in editables, msg: " + exc.getMessage() + " object: " + exc);
                }
            }
        }
    }

    /** Method responsible for changing the options which can make a player
     * when he want to start new local game
     * @param e where is saving data of performed action
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource(); 
        if (target == this.oponentComp) //toggle enabled of controls depends of oponent (if computer)
        {
            this.computerLevel.setEnabled(true);//enable level of computer abilities
            this.secondName.setEnabled(false);//disable field with name of player2
        }
        else if (target == this.oponentHuman) //else if oponent will be HUMAN
        {
            this.computerLevel.setEnabled(false);//disable level of computer abilities
            this.secondName.setEnabled(true);//enable field with name of player2
        }
        else if (target == this.okButton) //if clicked OK button (on finish)
        {
            if (this.firstName.getText().length() > 9) //make names short to 10 digits
            {
                this.firstName.setText(this.trimString(firstName, 9));
            }
            if (this.secondName.getText().length() > 9)  //make names short to 10 digits
            {
                this.secondName.setText(this.trimString(secondName, 9));
            }
            if (!this.oponentComp.isSelected()
                    && (this.firstName.getText().length() == 0 || this.secondName.getText().length() == 0))
            {
                JOptionPane.showMessageDialog(this, Settings.lang("fill_names"));
                return;
            }
            if ((this.oponentComp.isSelected() && this.firstName.getText().length() == 0))
            {
                JOptionPane.showMessageDialog(this, Settings.lang("fill_name"));
                return;
            }
            Game newGUI = JChessApp.getJavaChessView().getActiveTabGame();
            
            Settings sett = newGUI.getSettings();//sett local settings variable
            Player pl1 = sett.getPlayerWhite();//set local player variable
            Player pl2 = sett.getPlayerBlack();//set local player variable
            sett.setSize(new Integer(sizes[this.sizeChessboard.getSelectedIndex()]));
            sett.setGameMode(Settings.gameModes.newGame);
            sett.setTypeMode(modes[this.gameMode.getSelectedIndex()]);
            if(this.firstName.getText().length() > 9 ) this.firstName.setText(this.firstName.getText().substring(0, 8));
            if (this.color.getSelectedIndex() == 0) //if first player is white
            {
                pl1.setName(this.firstName.getText());//set name of player
                pl2.setName(this.secondName.getText());//set name of player
            }
            else //else change names
            {
                pl2.setName(this.firstName.getText());//set name of player
                pl1.setName(this.secondName.getText());//set name of player
            }
            pl1.setType(Player.playerTypes.localUser);//set type of player
            pl2.setType(Player.playerTypes.localUser);//set type of player
            sett.setGameType(Settings.gameTypes.local);
            if (this.oponentComp.isSelected()) //if computer opponent is checked
            {
                pl2.setType(Player.playerTypes.computer);
                switch (this.computerLevel.getValue()) {
				case 1:
					pl2.setComputerPlayer(new RandomComputerPlayer(pl2.getColor()));
					break;
				case 2:
					pl2.setComputerPlayer(new GloutonComputerPlayer(pl2.getColor()));
					break;
				case 3:
					pl2.setComputerPlayer(new MinMaxComputerPlayer(pl2.getColor()));
					break;
				default:
					pl2.setComputerPlayer(new RandomComputerPlayer(pl2.getColor()));
					break;
				}
            }
            sett.setUpsideDown(this.upsideDown.isSelected());
            if (this.timeGame.isSelected()) //if timeGame is checked
            {
                String value = this.times[this.time4Game.getSelectedIndex()];//set time for game
                Integer val = new Integer(value);
                sett.setTimeForGame((int) val * 60);//set time for game and mult it to seconds
                newGUI.getGameClock().setTimes(sett.getTimeForGame(), sett.getTimeForGame());
            }
            LOG.debug("this.time4Game.getActionCommand(): " + this.time4Game.getActionCommand());
            LOG.debug("****************\nStarting new game: " + pl1.getName() + " vs. " + pl2.getName()
                    + "\ntime 4 game: " + sett.getTimeForGame() + "\ntime limit set: " + sett.isTimeLimitSet()
                    + "\nwhite on top?: " + sett.isUpsideDown() + "\n****************");//4test

            newGUI.getChessboard().initSquares();
            newGUI.newGame();
            newGUI.resizeGame();
            this.parent.setVisible(false);//hide parent
            JChessApp.getJavaChessView().getActiveTabGame().repaint();
            JChessApp.getJavaChessView().setActiveTabGame(JChessApp.getJavaChessView().getNumberOfOpenedTabs()-1);
        }
    }

    /**
     * Method responsible for triming white symbols from strings
     * @param txt Where is capt value to equal
     * @param length How long is the string
     * @return result trimmed String
     */
    public String trimString(JTextField txt, int length)
    {
        String result = new String();
        try
        {
            result = txt.getText(0, length);
        }
        catch (BadLocationException exc)
        {
            LOG.error("BadLocationException: Something wrong in trimString: \n" + exc);
        }
        return result;
    }
}