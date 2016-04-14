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
 * NewGameWindow.java
 *
 * Created on 2009-10-20, 15:11:49
 */
package jchess;

import javax.swing.*;
import jchess.utils.Settings;
import jchess.display.windows.DrawLocalSettings;


/**
 *
 * @author Mateusz Sławomir Lach ( matlak, msl )
 */
public class EditGameWindow extends JDialog {

    private JTabbedPane jTabbedPane1;

    /** Creates new form NewGameWindow */
    public EditGameWindow() {
        initComponents();

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.jTabbedPane1.addTab(Settings.lang("local_game"), new DrawLocalSettings(this));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()
    {

        jTabbedPane1 = new JTabbedPane();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setName("Form");

        jTabbedPane1.setName("jTabbedPane1");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addContainerGap())
            );

        pack();
    }
}
