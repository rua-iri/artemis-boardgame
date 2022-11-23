package artemis.game;

import java.io.Serializable;

public class GameSquares implements Serializable{

	//instance variables for GameSquares
	private String squareName;
	private int priceOfSquare;
	private double developmentCost;
	private int developmentLevel;
	private int rent;
	private String elementOwner;
	private int squareSystem;

	
	/**
	 * Constructor with arguments
	 * 
	 * @param squareName
	 * @param priceOfSquare
	 * @param developmentCost
	 * @param rent
	 * @param squareSystem
	 */
	public GameSquares(String squareName, int priceOfSquare, double developmentCost, int rent, int squareSystem) {
		this.squareName = squareName;
		this.priceOfSquare = priceOfSquare;
		this.developmentCost = developmentCost;
		this.rent = rent;
		this.developmentLevel = 0;
		this.elementOwner = "";
		this.squareSystem = squareSystem;
	}

	/**
	 * @return the squareName
	 */
	public String getSquareName() {
		return squareName;
	}

	/**
	 * @return the priceOfSquare
	 */
	public int getPriceOfSquare() {
		return priceOfSquare;
	}

	/**
	 * @return the developmentCost
	 */
	public double getDevelopmentCost() {
		return developmentCost;
	}

	/**
	 * @param developmentCost the developmentCost to set
	 */
	public void setDevelopmentCost() {
		
		if (this.developmentLevel == 1) {
			this.developmentCost = developmentCost * 5;
		} else if (this.developmentLevel == 2) {
			this.developmentCost = developmentCost * 2;
		} else if (this.developmentLevel == 3) {
			this.developmentCost = developmentCost * 1.5;
		} else if (this.developmentLevel == 4) {
			this.developmentCost = developmentCost * 1.5;
		}
	}

	/**
	 * @return the developmentLevel
	 */
	public int getDevelopmentLevel() {
			return this.developmentLevel;
	}

	/**
	 * @param developmentLevel the developmentLevel to set "Add Development to
	 *                         square"
	 */
	public void setDevelopmentLevel(int level) {
		this.developmentLevel = level;

		// Set the new rent
		setRent();

		// set new development cost
		setDevelopmentCost();
	}

	/**
	 * @return the rent
	 */
	public int getRent() {
		return rent;
	}

	/**
	 * rent to set only called once development is added to owned
	 *             square
	 */
	public void setRent() {
		this.rent += rent * 2 * this.developmentLevel;
	}

	/**
	 * @return the name of the player who owns the square
	 */
	public String getElementOwner() {
		return elementOwner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setElementOwner(String elementOwner) {
		this.elementOwner = elementOwner;
	}
	
	/**
	 * @return the squareSystem
	 */
	public int getSquareSystem() {
		return squareSystem;
	}

	/**
	 * @param squareSystem the squareSystem to set
	 */
	public void setSquareSystem(int squareSystem) {
		this.squareSystem = squareSystem;
	}

	@Override
	public String toString() {
		return ("\n" + squareName + ", (Development Level : "+developmentLevel)+")";
	}


}
