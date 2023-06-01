import java.util.Vector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.User;
import util.Connect;



public class Register{

	private Vector<User> users = new Vector<>();
	
	BorderPane borderContainer;
	GridPane gridContainer;
	FlowPane flowContainer;
	Label titleLbl, userIdLbl, emailLbl, usernameLbl, passwordLbl, confPasswordLbl;
	CheckBox terms;
	TextField tfUserId, tfUsername, tfEmail;
	PasswordField pfPassword, pfConfPassword;
	Button backBtn, registerBtn;
	
	//connection
	private Connect connect = Connect.getConnection();
	
	
	
	
	public Register() {
		initialize();
		setPos();
		addComponent();
		getUsersData();
		tfUserId.setText(Integer.toString(users.size()+1));
	}
	
	
	private void initialize()
	{
		borderContainer = new BorderPane();
		gridContainer = new GridPane();
		flowContainer = new FlowPane();
		
		titleLbl = new Label("Register");
		userIdLbl = new Label("User ID");
		emailLbl = new Label("Email");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		confPasswordLbl = new Label("Confirm Password");
		terms = new CheckBox("Agree on terms and conditions**");
		registerBtn = new Button("Register");
		backBtn = new Button("Back");
		
		tfUserId = new TextField();
		tfUsername = new TextField();
		tfEmail = new TextField();
		pfPassword = new PasswordField();
		pfConfPassword = new PasswordField();
		
		//disable tfUserId
		tfUserId.setDisable(true);
		
	}
	
	
	//SELECT USER DATA
	private void getUsersData()
	{
		users.removeAllElements();
		
		String query = "SELECT * FROM user";
		connect.rs = connect.selectQuery(query);
		
		try {
			
			while(connect.rs.next())
			{
				Integer id = connect.rs.getInt("UserId");
				String UserRole = connect.rs.getString("UserRole");
				String UserEmail = connect.rs.getString("UserEmail");
				String UserName = connect.rs.getString("UserName");
				String UserPassword = connect.rs.getString("UserPassword");
				
				users.add(new User(id, UserRole, UserEmail, UserName, UserPassword));
			}
			
		} catch(Exception e){
			
		}
		
	}
	
	//ADD USER DATA 
	private void addUserData(String UserRole, String UserEmail, String UserName, String UserPassword)
	{
		String query = "INSERT INTO user VALUES ('0', '"+UserRole+"', '"+UserEmail+"'"
				+ ", '"+UserName+"', '"+UserPassword+"')";
		
		connect.executeUpdate(query);
	}
	
	//valdate input
	public void validateUserInput()
	{
		
		boolean emailAt = false;
		boolean emailDot = false;
		int count = 0;
		boolean usernameLen = false;
		boolean passwordLen = false;
		boolean passwordEqualsConf = false;
		boolean isAgree = false; 
		boolean isChecked = false;
		boolean isCountAceed = false;
		
		
		if(!tfEmail.getText().contains("@"))
		{
			emailAt = true;
		}
//		else if(tfEmail.getText().indexOf(".") < tfEmail.getText().indexOf("@"))
//		{
//			emailDot = false;
//		}
		
		if(emailAt == true)
		{
			count++;
		}
		
		if(count > 1)
		{
			isCountAceed = true;
		}
		
		if(tfUsername.getText().length() < 5 
				|| tfUsername.getText().length() > 30)
		{
			usernameLen = true;
		}
		
		if(pfPassword.getText().length() < 5 ||
		pfPassword.getText().length() > 20)
		{
			passwordLen = true;
		}
		
		if(!pfConfPassword.getText().equals(pfPassword.getText()))
		{
			passwordEqualsConf = true;
		}
		
//		if(!terms.isSelected())
//		{
//			isChecked = true;
//		}
		
		if(emailAt == true || isCountAceed == true 
			|| usernameLen == true || passwordLen == true 
			|| passwordEqualsConf == true || isChecked == true)
		{
			showAlert(Alert.AlertType.ERROR, "Error", "Username must be 5 - 30 characters !", 
					"Password must be 5 - 20 characters !");
		}
		else {
			showSuccessMessage(Alert.AlertType.CONFIRMATION, "Notification", "Account Succesfully Registered !");
			addUserData("Customer", tfEmail.getText(), tfUsername.getText(), pfPassword.getText());
		}
		
	}
	
	
	private void addComponent()
	{
		
		flowContainer.getChildren().add(registerBtn);
		flowContainer.getChildren().add(backBtn);
		
		gridContainer.add(userIdLbl, 0, 0);
		gridContainer.add(emailLbl, 0, 1);
		gridContainer.add(usernameLbl, 0, 2);
		gridContainer.add(passwordLbl, 0, 3);
		gridContainer.add(confPasswordLbl, 0, 4);
		gridContainer.add(terms, 0, 5);
		
		gridContainer.add(tfUserId, 1, 0);
		gridContainer.add(tfEmail, 1, 1);
		gridContainer.add(tfUsername, 1, 2);
		gridContainer.add(pfPassword, 1, 3);
		gridContainer.add(pfConfPassword, 1, 4);
		
		
		gridContainer.add(flowContainer, 0, 6);
		
	}
	
	private void setPos()
	{
		borderContainer.setTop(titleLbl);
		borderContainer.setCenter(gridContainer);
		borderContainer.setPadding(new Insets(10));
		
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
	}
	
	public Pane getRegisterPane() {
		return borderContainer;
	}
	
	public void showAlert(Alert.AlertType alertType, String title, String message
			, String message2)
	{
		Alert a = new Alert(alertType);
		a.setTitle(title);
		a.setContentText(message);
		a.setContentText(message2);
		a.show();
		
	}
	
	private void showSuccessMessage(Alert.AlertType alertType, String title, String message)
	{
		Alert a = new Alert(alertType);
		a.setTitle(title);
		a.setContentText(message);
		a.show();
	}

	

	
	
	
	
	


}
