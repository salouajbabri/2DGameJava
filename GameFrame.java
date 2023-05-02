import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame {

	public GameFrame(Game sw, int GWIDTH, int GHEIGHT) {
		
		JFrame frame = new JFrame("My Game");
        frame.setPreferredSize(new Dimension( GWIDTH, GHEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		frame.getContentPane().add(sw);
        frame.pack();
        frame.setVisible(true);
	}
}