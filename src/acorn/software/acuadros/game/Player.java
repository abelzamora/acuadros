package acorn.software.acuadros.game;

import android.graphics.Color;

public class Player {
	
	/* User name */
	private String username;
	
	/* Player color */
	private Color color;
	
	/* Points gotten by the player */
	private int points;
	
	/**
	 * Player constructor
	 * @param username
	 * @param color
	 */
	public Player(String username, Color color) {
		// Init user name and color
		this.username = username;
		this.color = color;
		
		// Set points to the init value
		this.points = 0;
	}
	
	/**
	 * Get player username
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Get player color
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Get player points
	 * @return
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Add points to the player
	 * @param nPoints
	 */
	public void addPoints(int nPoints) {
		points += nPoints;
	}
}
