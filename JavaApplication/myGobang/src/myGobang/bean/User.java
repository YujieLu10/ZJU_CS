package myGobang.bean;

import java.io.Serializable;

public class User implements Serializable {


	public String userName;//用户名字
	private int qiziColor;//棋子颜色 1执黑，2执白
	
	
	public User() {
		super();
	}
	public User(String userName, int qiziColor) {
		super();
		this.userName = userName;
		this.qiziColor = qiziColor;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getQiziColor() {
		return qiziColor;
	}
	public void setQiziColor(int qiziColor) {
		this.qiziColor = qiziColor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + qiziColor;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (qiziColor != other.qiziColor)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
