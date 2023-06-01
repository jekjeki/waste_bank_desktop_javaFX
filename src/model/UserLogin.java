package model;

public class UserLogin {
	
	private Integer UserId;
	private String UserRole, UserEmail, UserName, UserPassword;
	public UserLogin(Integer userId, String userRole, String userEmail, String userName, String userPassword) {
		super();
		UserId = userId;
		UserRole = userRole;
		UserEmail = userEmail;
		UserName = userName;
		UserPassword = userPassword;
	}
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	public String getUserRole() {
		return UserRole;
	}
	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	public String getUserEmail() {
		return UserEmail;
	}
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	

}
