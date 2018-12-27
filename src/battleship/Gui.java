package battleship;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gui {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				JFrame frame = new JFrame("Hello World Swing!");
				frame.setSize(500, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		
	
		
	}

}
