package artemis.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Player implements Serializable{
	

	// variables for each player
	private String playerName;
	private int position;
	private double resources;
	
	/*
	 * ArrayList to contain the system(s) owned by player
	 * Used in game to ensure that players do not buy 
	 * elements owned by another player
	 */
	private ArrayList<Integer> playerSystem;
	
	/**
	 * Default constructor
	 */
	public Player() {
		
	}
	
	/**
	 * Constructor with argument(s)
	 * 
	 * @param playerName
	 */
	public Player(String playerName) {
		setPlayerName(playerName);
		setPosition(0);
		this.resources = 1000;
		playerSystem = new ArrayList<Integer>();
	}

	/**
	 * @return the player
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int diceRoll) {
	
		this.position = position +diceRoll;
		
		// once you go back to the beginning of the board, 
		// 200 is added as you are on square "GO".
		if (this.position >= 12) {
			this.position = position - 12; 
			this.resources += 200; 	
		// prints to screen whenever the player has PASSED go
		// does not print if they have LANDED on go
			if (this.position != 0) { // 0 is the GO square
			System.out.println("");
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("You have passed Blast Off! and therefore have\ncollected 200 resources.");
				System.out.println("\nYour resources now stand at "+this.getResources()+".\n");
				System.out.println("-------------------------------------------------------"); // 55
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
			}
		} 
		
	}

	/**
	 * @return the resources
	 */
	public double getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(double transaction) {
		this.resources += transaction;
		
	}

	/**
	 * @return the playerSystem
	 */
	public ArrayList<Integer> getPlayerSystem() {
		return playerSystem;
	}

	/**
	 * @param playerSystem the playerSystem to set
	 */
	public void setPlayerSystem(int playerSystem) {
		this.playerSystem.add(playerSystem);
	}
	

}
