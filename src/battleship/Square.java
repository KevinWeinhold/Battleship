package battleship;

import java.awt.*;
import javax.swing.*;
import java.applet.*;

public class Square {
	/*
	 * Functions for Is Hit, Is Miss, Is Empty
	 */
	private int x;
	private int y;
	private int width;
	private int height;
	private SquareEnumType type;
	
	private Graphics g;
	
	public Square(int x, int y, int width, int height, SquareEnumType type)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		
		//paintComponent(x, y, width, height, g, type);
		//draw the type
		
		//Graphics.drawRect(x, y, width, height, getType().squareColor);
		/*anim1 a = new anim1();
		Graphics g = anim1.getGraphics();
		paintComponent(g);
		*/
		
	}
	
	
	
	public void paintComponent(int x, int y, int width, int height, Graphics g, SquareEnumType type) {
		g.setColor(getType().squareColor);
		g.fillRect(x, y, width, height);
	}

	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public SquareEnumType getType() {
		return type;
	}

	public Color getColor(SquareEnumType type) {
		return type.squareColor;
	}

	public void setType(SquareEnumType type) {
		this.type = type;
	}



	public void Draw() {
		
	}
}