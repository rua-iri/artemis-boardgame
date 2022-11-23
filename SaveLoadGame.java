package artemis.game;

import java.io.*;
import java.util.ArrayList;

public class SaveLoadGame {

	/*
	 * The first half of this code deals with saving the data stored in arraylists
	 * in the game class.
	 * 
	 * The second half deals with loading those instances from their respective .ser
	 * files so that players can continue a game if it is accidently quit etc.
	 */

	// variables to hold file locations
	// probably makes it easier to access without issues
	public final static String playersFile = "playerstats.ser";
	public final static String squaresFile = "squaresstats.ser";
	public final static String systemsFile = "systemsstats.ser";

	/**
	 * method for serialising player objects
	 * 
	 * @param playerList
	 */
	public static void savePlayers(ArrayList<Player> playerList) {

		// try/catch block in case something goes wrong
		try {
			FileOutputStream fileOut = new FileOutputStream(playersFile);
			ObjectOutputStream obOut = new ObjectOutputStream(fileOut);

			// cycles through players in the arraylist
			for (Player p : playerList) {
				// writes each object to the .ser file
				obOut.writeObject(p);
			}


			// close those things b/c we
			// don't need them anymore
			obOut.close();
			fileOut.close();

			// hopefully there aren't any errors, but if there are
			// this might tell us what's wrong
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method for serialising GameSquares objects
	 * 
	 * @param squaresList
	 */
	public static void saveSquares(ArrayList<GameSquares> squaresList) {

		try {
			FileOutputStream fileOut = new FileOutputStream(squaresFile);
			ObjectOutputStream obOut = new ObjectOutputStream(fileOut);

			// cycles through squares in the arraylist
			for (GameSquares gs : squaresList) {
				// writes each object to the .ser file
				obOut.writeObject(gs);
			}


			// close those things b/c we
			// don't need them anymore
			obOut.close();
			fileOut.close();

			// hopefully there aren't any errors, but if there are
			// this might tell us what's wrong
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method for serialising Systems objects
	 * 
	 * @param systemsList
	 */
	public static void saveSystems(ArrayList<Systems> systemsList) {

		try {
			FileOutputStream fileOut = new FileOutputStream(systemsFile);
			ObjectOutputStream obOut = new ObjectOutputStream(fileOut);

			// cycles through systems in the arraylist
			for (Systems s : systemsList) {
				// writes each object to the .ser file
				obOut.writeObject(s);
			}


			// close those things b/c we
			// don't need them anymore
			obOut.close();
			fileOut.close();

			// hopefully there aren't any errors, but if there are
			// this might tell us what's wrong
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * The following will be the methods to load a game from the files that have
	 * been written to above
	 * 
	 */

	/**
	 * method for loading players
	 * 
	 * @return ArrayList<Player>
	 */
	public static ArrayList<Player> loadPlayer() {
		// creating arraylist to store the player objects
		ArrayList<Player> playList = new ArrayList<Player>();

		try {
			FileInputStream fileIn = new FileInputStream(playersFile);
			ObjectInputStream obIn = new ObjectInputStream(fileIn);

			/* 
			 * While the file still has unread data
			 *  this will keep running and 
			 *  adding to the arraylist
			 */
			while (fileIn.available() != 0) {
				playList.add((Player) obIn.readObject());
			}

			// close that stuff
			// we won't be needing it any more
			obIn.close();
			fileIn.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return the arraylist that contains the data
		// from the file
		return playList;
	}

	/**
	 * method for loading Game Squares
	 * 
	 * @return ArrayList<GameSquares>
	 */
	public static ArrayList<GameSquares> loadSquare() {
		// creating arraylist to store the Game Squares objects
		ArrayList<GameSquares> squareList = new ArrayList<GameSquares>();

		try {
			FileInputStream fileIn = new FileInputStream(squaresFile);
			ObjectInputStream obIn = new ObjectInputStream(fileIn);

			/* 
			 * While the file still has unread data
			 *  this will keep running and 
			 *  adding to the arraylist
			 */
			while (fileIn.available() != 0) {
				squareList.add((GameSquares) obIn.readObject());
			}

			// close that stuff
			// we won't be needing it any more
			obIn.close();
			fileIn.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return the arraylist that contains the data
		// from the file
		return squareList;
	}
	
	
	

	/**
	 * method for loading Systems
	 * 
	 * @return ArrayList<Systems>
	 */
	public static ArrayList<Systems> loadSystem() {
		// creating arraylist to store the player objects
		ArrayList<Systems> systemList = new ArrayList<Systems>();

		try {
			FileInputStream fileIn = new FileInputStream(systemsFile);
			ObjectInputStream obIn = new ObjectInputStream(fileIn);

			/* 
			 * While the file still has unread data
			 *  this will keep running and 
			 *  adding to the arraylist
			 */
			while (fileIn.available() != 0) {
				systemList.add((Systems) obIn.readObject());
			}

			// close that stuff
			// we won't be needing it any more
			obIn.close();
			fileIn.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return the arraylist that contains the data
		// from the file
		return systemList;
	}

}
