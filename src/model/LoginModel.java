package model;

public class LoginModel {

	String username, password, userole;
	
	public LoginModel(String username, String password, String userole) {
		super();
		this.username = username;
		this.password = password;
		this.userole = userole;
		
	}
	public String getUserole() {
		return userole;
	}
	public void setUserole(String userole) {
		this.userole = userole;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
