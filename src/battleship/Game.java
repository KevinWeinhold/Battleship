package battleship;

public class Game {
	
	private boolean isClient;
	
	//Game class that handles turns and phases of the game
	public Game(boolean tof)
	{
		isClient = tof;
		pickPositions();
	}
	
	private void pickPositions() {
		System.out.print(isClient);
	}
}