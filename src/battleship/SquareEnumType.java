package battleship;

import java.awt.Color;

public enum SquareEnumType {
	
	Hit(Color.RED, true), Miss(Color.GREEN, true),
	Ship(Color.GRAY, true), Water(Color.BLUE , true);
	
	Color squareColor;
	boolean buildable;
	
	SquareEnumType(Color squareColor, boolean buildable){
		//this.textureName = textureName;
		this.squareColor = squareColor;
		this.buildable = buildable;
	}
	
	
	//goal: make hit, miss, ship, water into a color
}
