package artemis.game;

import java.io.Serializable;

public class Systems implements Serializable{
	
	private int systemNumber;
	private String systemName;
	private String systemOwner;
	
	/**
	 * Constructor with arguments
	 * 
	 * @param systemNumber
	 * @param systemName
	 */
	public Systems (int systemNumber, String systemName) {
		this.systemNumber = systemNumber;
		this.systemName = systemName;
		this.systemOwner = "";
	}
	
	
	/**
	 * @return the systemNumber
	 */
	public int getSystemNumber() {
		return systemNumber;
	}
	/**
	 * @param systemNumber the systemNumber to set
	 */
	public void setSystemNumber(int systemNumber) {
		this.systemNumber = systemNumber;
	}
	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}
	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}


	/**
	 * @return the systemOwner
	 */
	public String getSystemOwner() {
		return systemOwner;
	}


	/**
	 * @param systemOwner the systemOwner to set
	 */
	public void setSystemOwner(String systemOwner) {
		this.systemOwner = systemOwner;
	}
	
	@Override
	public String toString() {
		return ("\n"+systemName);
	}
	
	

}
