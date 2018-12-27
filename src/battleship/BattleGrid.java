package battleship;

import javax.swing.*;
import java.awt.*;

public abstract class BattleGrid extends JPanel {
	private JPanel temp;
	JPanel self;
	public void goooieee() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {				
				JFrame frame = new JFrame("Hello World Swing!");
				frame.setSize(500, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	/*
	public BattleGrid() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		self = new JPanel();
		self.setLayout(new GridLayout(0,10));
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j< 10; j++) {
				temp = getCell();
				self.add(temp);
			}
		}
	}*/
}
