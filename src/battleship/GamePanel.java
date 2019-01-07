package battleship;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;
	private JTextField userText;
	private JTextArea chatWindow;
	
	public GamePanel()
	{
		super();
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		this.setVisible(true);
		this.setBackground(Color.white);		
	}

	
	public void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		paintSquares(g);
		
	}
	
	/*public void GridLines (Graphics l) {
		l.setColor(Color.CYAN);
		l.drawLine(80, 10, 80, 600);
	}*/
	
	//Square tile5 = new Square(fixed, fixed, fixed, fixed, )
	
	public void paintSquares(Graphics g) {
		super.paintComponent(g);
		//use a for loop to make map
		int width = 60;
		int height = 60;
		int current_x = 0;
		int current_y = 0;
		
		for(int i = 0; i < 10; i++) {
			current_y = 0;
			for(int j = 0; j < 10; j++) {
				//Square tile = new Square(current_x, current_y, width, height, SquareEnumType.Miss);
				//g.setColor(tile.getColor(SquareEnumType.Miss));
				g.drawRect(current_x, current_y, width, height);
				current_y += 60;
			}
			current_x += 60;
		}
			
		//test squares
		/*
		Square tile = new Square(10, 10, 64, 64, SquareEnumType.Miss);
		g.setColor(tile.getColor(SquareEnumType.Miss));
		g.drawRect(30, 30, 60, 60);
		g.fillRect(30, 30, 60, 60);
		
		Square tile2 = new Square(10, 10, 64, 64, SquareEnumType.Hit);
		g.setColor(tile2.getColor(SquareEnumType.Hit));
		g.drawRect(80, 80, 60, 60);
		g.fillRect(80, 80, 60, 60);
		*/
	}
}

