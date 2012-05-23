package acorn.software.acuadros.game;

import java.util.Vector;

public class Game {

	/* List of players */
	private Vector<Player> players;
	
	/* Player turn */
	private int turn;
	
	/* Total max points in the game */
	private int maxPoints;
	
	/* Counter of points in the game */
	private int totalPoints;
	
	/**
	 * Game constructor
	 * @param maxPoints
	 */
	public Game(int maxPoints) {
		// Init points
		this.maxPoints = maxPoints;
		this.totalPoints = 0;
		
		// Init players array
		players = new Vector<Player>();
		
		// No turn 
		turn = -1;
	}
	
	/**
	 * Get player
	 * @param nPlayer
	 * @return
	 */
	public Player getPlayer(int nPlayer) {
		return players.get(nPlayer);
	}
	
	/**
	 * Add new player to game
	 * @param player
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	/**
	 * Start the game, this method must be user after players set up
	 * @return
	 */
	public Player startGame() {
		if(turn < 0 && players.size() > 1) {
			// If this is no turn and there is enought players the game can be started
			turn = 0;
			return players.get(turn);
		} else {
			// Game can't be started
			return null;
		}
	}
	
	/**
	 * Get next turn player
	 * @return
	 */
	public Player nextTurn() {
		if(turn > -1) {
			// Get next player turn if the game has started
			turn++;
			if(turn == players.size()) {
				// New round
				turn = 0;
			}
			
			// Retunt player
			return players.get(turn);
		} else {
			// Game hasn't been started
			return null;
		}
	}
	
	/**
	 * Score player points in the game
	 * @param points
	 * @return
	 */
	public boolean playerScores(int points) {
		if(turn > -1) {
			// Scores the player is the game has started
			players.get(points).addPoints(points);
			
			// Add point to total counter
			totalPoints += points;
			
			// Check total point with max
			if(totalPoints == maxPoints) {
				// Game finish
				turn = -1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
