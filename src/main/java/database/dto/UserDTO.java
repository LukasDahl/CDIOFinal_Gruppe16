package database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard implementation of IUserDTO
 */
public class UserDTO implements Serializable, IUserDTO {
	//Fields
	private int	userId;
	private String userName;
	private String ini;
	private String cpr;
	private boolean isAdmin;
	private boolean isPharma;
	private boolean isPLeader;
	private boolean isLabo;
	//Constructor
	public UserDTO() {
	}
	//Getters and Setters
	@Override
	public int getUserId() {
		return userId;
	}
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String getUserName() {
		return userName;
	}
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String getIni(){return ini;}
	@Override
	public void setIni(String ini) {this.ini = ini;}
	@Override
	public String getCpr(){return cpr;}
	@Override
	public void setCpr(String cpr) {this.cpr = cpr;}
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}
	@Override
	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
	@Override
	public boolean isPharma() {
		return isPharma;
	}
	@Override
	public void setPharma(boolean pharma) {
		isPharma = pharma;
	}
	@Override
	public boolean isPLeader() {
		return isPLeader;
	}
	@Override
	public void setPLeader(boolean PLeader) {
		isPLeader = PLeader;
	}
	@Override
	public boolean isLabo() {
		return isLabo;
	}
	@Override
	public void setLabo(boolean labo) {
		isLabo = labo;
	}
	@Override
	public String toString(){
		return "" + userId + userName + isAdmin + isLabo + isPLeader + isPharma;
	}
}