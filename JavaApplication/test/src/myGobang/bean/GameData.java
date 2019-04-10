package myGobang.bean;

import java.io.Serializable;

public class GameData implements Serializable{

	private User user;
	private Point p;
	
	
	public GameData() {
		super();
	}


	public GameData(User user, Point p) {
		super();
		this.user = user;
		this.p = p;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Point getP() {
		return p;
	}


	public void setP(Point p) {
		this.p = p;
	}
	
	
}