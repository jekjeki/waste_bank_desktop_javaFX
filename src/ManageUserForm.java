import java.util.Optional;
import java.util.Vector;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.MinimizeIcon;
import jfxtras.labs.scene.control.window.Window;
import model.User;
import util.Connect;

public class ManageUserForm {

	Window manageUserWindow;
	BorderPane bpManageUserWindow;
	GridPane gridContManageUserWindow;
	FlowPane flowLabelManageUserWindow, flowTfManageUserWindow, flowtfMode, flowbuttonsCrud;
	HBox hboxManageUserForm, hboxbuttonsChangeMode, hboxbuttonsCrud;
	Label labelId, labelRole , labelUsername, labelEmail, labelPassword;
	
	Button insertMode, changeMode, btnInsert, btnUpdate, btnDelete;
	
	TextField tfId, tfUsername, tfEmail;
	ComboBox<String> cbRole;
	ObservableList<User> roleUserItems;
	
	PasswordField pfPassword;
	
	TableViewSelectionModel<User> selectItemTableUser;
	ObservableList<User> userItems;
	
	private TableView<User> tableViewAllUser;
	TableColumn<User, Integer> userID;
	TableColumn<User, String> userRole;
	TableColumn<User, String> userUsername;
	TableColumn<User, String> userEmail;
	TableColumn<User, String> userPassword;
	
	//vector to keep user data
	Vector<User> ListAllUserData = new Vector<>();
	
	
	private Connect con = Connect.getConnection();
	
	public ManageUserForm() {
		init();
		windowSetTitle();
		setLayout();
		
		
		//get all user data 
		getAllUserData();
		
		//set cell value user data 
		setCellUserValue();
		
		//insert data to table user
		insertDatatoTableUser();
		
		textfieldsStyle();
		
		selectRowUserData();
		
		//button change mode click
		ChangeModeBtnClick();
		
		//button insert mode click
		InsertModeBtnClick();
		
		
		ChangeRoleDatawhenSelect();
		
	}
	
	private void init() {
		manageUserWindow = new Window();
		
		//border pane
		bpManageUserWindow = new BorderPane();
		
		//grid pane 
		gridContManageUserWindow = new GridPane();
		
		//flow labels data 
		flowLabelManageUserWindow = new FlowPane();
		
		//flow textfields data 
		flowTfManageUserWindow = new FlowPane();
		hboxManageUserForm = new HBox();
		
		//flow tf mode
		flowtfMode = new FlowPane();
		hboxbuttonsChangeMode = new HBox();
		
		//flow crudbtn 
		flowbuttonsCrud = new FlowPane();
		hboxbuttonsCrud = new HBox();
		
		//buttons mode
		insertMode = new Button("Insert Mode");
		changeMode = new Button("Change Mode");
		
		
		//crud buttons flow
		btnInsert = new Button("Insert");
		btnUpdate = new Button("Update");
		btnDelete = new Button("Delete");
		
		//init new textfields
		tfId = new TextField();
		cbRole = new ComboBox<>();
		tfUsername = new TextField();
		tfEmail = new TextField();
		pfPassword = new PasswordField();
		
		//table view all user init
		tableViewAllUser = new TableView<>();
		userID = new TableColumn<>("ID");
		userRole = new TableColumn<>("Role");
		userUsername = new TableColumn<>("Username");
		userEmail = new TableColumn<>("Email");
		userPassword = new TableColumn<>("Password");
		
		labelId = new Label("ID");
		labelRole = new Label("Role");
		labelUsername = new Label("Username");
		labelEmail = new Label("Email");
		labelPassword = new Label("Password");
		
		
	}
	
	//error alert 
	private void showErrorAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();;
	}
	
	//confirmation alert
	private Optional<ButtonType> setAlert(AlertType alertType, String message){
		
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.setHeaderText(null);
		return alert.showAndWait();
		
	}
	
	//object property
	
	private void addListenerRowTableUser() {
		tableViewAllUser.getSelectionModel().selectedItemProperty().addListener((obs, old, newdata)->{
			if(newdata != null) {
				tfId.setText(userItems.get(0).getUserId().toString());
				tfUsername.setText(userItems.get(0).getUserName());
				tfEmail.setText(userItems.get(0).getUserEmail());
				pfPassword.setText(userItems.get(0).getUserPassword());
				cbRole.getSelectionModel().select(userItems.get(0).getUserRole());
			}
			
			
				
		});
		
//		cbRole.setOnAction((e) -> {
//			cbRole.getSelectionModel().getSelectedItem();
//		});
		
	}
	
	
	private void selectRowUserData() {
		selectItemTableUser = tableViewAllUser.getSelectionModel();
		selectItemTableUser.setSelectionMode(SelectionMode.SINGLE);
		userItems = selectItemTableUser.getSelectedItems();
		
	
		
//		Vector<User> user = new Vector<>(tableViewAllUser.getSelectionModel().getSelectedItems());
//		
//		for (User u : user) {
//			System.out.println(u.getUserId());
//		}
		
		
//		User user = tableViewAllUser.getSelectionModel().getSelectedItem();
		
	}
	
	private void ChangeRoleDatawhenSelect() {
		cbRole.getItems().add("Admin");
		cbRole.getItems().add("Customer");

	}
	
	//clear all field
	private void clearAllFields() {
		tfId.clear();
		tfUsername.clear();
		tfEmail.clear();
		pfPassword.clear();
		cbRole.getSelectionModel().select("");
	}
	
	
	//button click change mode effect 
	private void ChangeModeBtnClick() {
		changeMode.setOnAction((e)->{
			clearAllFields();
			addListenerRowTableUser();
			
			btnInsert.setDisable(true);
			btnUpdate.setDisable(false);
			btnDelete.setDisable(false);
			tableViewAllUser.setDisable(false);
			
			//delete button chnage mode
			btnDelete.setOnAction((del) -> {
				if(tfId.getText().equals(""))
				{
					showErrorAlert(Alert.AlertType.ERROR, null, "user must be selected");
				}
				else {
					
					Optional<ButtonType> btOptional = setAlert(AlertType.CONFIRMATION, "Delete user " + tfUsername.getText());
					
					if (btOptional.get() == ButtonType.OK) {
						
						setAlert(AlertType.INFORMATION, "User " + tfUsername.getText() + " successfully deleted");
						
						String query_delete_user = "DELETE FROM user WHERE UserId = "+tfId.getText();
						
						con.executeUpdate(query_delete_user);
					}
					
					
				}
				
			});
			
			//update
			btnUpdate.setOnAction((up)->{
				
				if(tfId.getText().equals(""))
				{
					setAlert(AlertType.ERROR, "user must be selected first");
				}
				else {
					
					String updateEmail = tfEmail.getText();
					String updateUsername = tfUsername.getText();
					String updatePassword = pfPassword.getText();
					
					int countFalse = 0;
					int countAt = 0;
					int countDot = 0;
					
					boolean usernameFalse = false;
					boolean passwordFalse = false;
					boolean roleFalse = false;
					
					if(updateEmail.charAt(0) == '@' || updateEmail.charAt(updateEmail.length()-1) == '.') {
						countFalse++;
					}
					
					for(int i = 0; i < updateEmail.length(); i++)
					{
						
						if(updateEmail.charAt(i) == '@')
						{
							countAt++;
						}
						
						if(updateEmail.indexOf('@') > updateEmail.indexOf('.'))
						{
							countFalse++;
						}
						
						if(updateEmail.charAt(i) == '@' && updateEmail.charAt(i+1) == '.')
						{
							countFalse++;
						}
						
						
						if(updateEmail.indexOf('.') > updateEmail.indexOf('@'))
						{
							if(updateEmail.charAt(i) == '.')
							{
								countDot++;
							}
							
						}
						
					}
					
					//update username validate 
					if(updateUsername.length() < 5 || updateUsername.length() > 30)
					{
						usernameFalse = true;
					}
					
					//update password valdiate 
					if(updatePassword.length() < 5 || updatePassword.length() > 20)
					{
						passwordFalse = true;
					}
					
					//update role validate 
					if(cbRole.getSelectionModel().getSelectedItem() == null)
					{
						roleFalse = true;
					}
					
					if(usernameFalse == true || passwordFalse == true || roleFalse == true
							|| countAt > 1 || countFalse > 0 || countDot > 2)
					{
						showErrorAlert(AlertType.ERROR, null, "User must be selected first/nRole must be choosed");
					}
					else {
						
						String update_user_query = "UPDATE user SET UserEmail = " +"'"+tfEmail.getText() +"'"+", UserRole = "+"'"+cbRole.getSelectionModel().getSelectedItem().toString()+"'"
								+" , UserName = "+"'"+tfUsername.getText()+"'"+" , UserPassword = "+"'"+pfPassword.getText()+"'"
								+" WHERE UserId = " +tfId.getText();
						
						con.executeUpdate(update_user_query);
						
						
						showErrorAlert(AlertType.INFORMATION, "Information", "User " + tfUsername.getText() + 
								" successfully updated");
					}
				}
				
				
				
				
			}); 
		
			
		});
	}
	
	//insert mode table form
	private void InsertModeBtnClick() {
		insertMode.setOnAction((e) -> {
			clearAllFields();
			
			tfId.setText(Integer.toString(ListAllUserData.size()+1));
			
			btnInsert.setDisable(false);
			btnUpdate.setDisable(true);
			btnDelete.setDisable(true);
			
			tableViewAllUser.setDisable(true);
			
			btnInsert.setOnAction((in)->{
				String newUsername = tfUsername.getText();
				String newEmail = tfEmail.getText();
				String newPassword = pfPassword.getText();
				
				boolean checkStartEndEmail = false;
				boolean checkNewUsername = false;
				boolean checkNewPassword = false;
				boolean checkNewRole = false;
				
				int countAt = 0;
				int countFalse = 0;
				int countDot = 0;
				
				if(newEmail.charAt(0) == '@' || newEmail.charAt(newEmail.length()-1) == '.'
						|| newEmail.charAt(0)=='.' || newEmail.charAt(newEmail.length()-1) == '@')
				{
					checkStartEndEmail = true;
				}
				
				
				for(int i = 0; i < newEmail.length(); i++)
				{
					if(newEmail.charAt(i) == '@' && newEmail.charAt(i+1) == '.')
					{
						countFalse++;
					}
					
					if(newEmail.charAt(i) == '@')
					{
						countAt++;
					}
					
					if(newEmail.indexOf('@') > newEmail.indexOf('.'))
					{
						countFalse++;
					}
					
					if(newEmail.indexOf('.') > newEmail.indexOf('@'))
					{
						if(newEmail.charAt(i) == '.')
						{
							countDot++;
						}
						
					}
				}
				
				if(newUsername.length() < 5 || newUsername.length() > 30)
				{
					checkNewUsername = true;
				}
				
				if(newPassword.length() < 5 || newPassword.length() > 20)
				{
					checkNewPassword = true;
				}
				
				if(cbRole.getSelectionModel().getSelectedItem() == null) {
					countFalse++;
				}
				
				if(checkStartEndEmail == true || checkNewUsername == true 
						|| checkNewPassword == true || countDot > 2 || countFalse > 0)
				{
					String message = String.format("Role must be choosed\n"
							+"Email must be valid"+"\n"+"Username must be 5 - 30 characters"
							+"\nPassword must be 5 - 20 characters\n", tfEmail.getText());
					
					showErrorAlert(AlertType.ERROR, "Error", message);
				}
				else {
					String insert_user_query = "INSERT INTO user VALUES ( " +tfId.getText()
							+" , "+"'"+cbRole.getSelectionModel().getSelectedItem()+"'"+" , "+"'"+tfEmail.getText()+"'"
							+" , "+"'"+tfUsername.getText()+"'"+" , "+"'"+pfPassword.getText()+"' )";
					
					con.executeUpdate(insert_user_query);
					
					showErrorAlert(AlertType.INFORMATION, "Notification", "User "+tfUsername.getText()+" successfully inserted");
				}
				
			});
		});
	}
	
	
	private void getAllUserData() {
		
		String get_user_data = "SELECT * FROM user";
		
		con.rs = con.selectQuery(get_user_data);
		try {
			while(con.rs.next()) {
				Integer userId = con.rs.getInt("UserId");
				String userRole = con.rs.getString("UserRole");
				String userEmail = con.rs.getString("UserEmail");
				String userName = con.rs.getString("Username");
				String userPassword = con.rs.getString("UserPassword");
				
				ListAllUserData.add(new User(userId, userRole, userEmail, userName, userPassword));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void setCellUserValue() {
		userID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
		userRole.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
		userEmail.setCellValueFactory(new PropertyValueFactory<>("UserEmail"));
		userUsername.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		userPassword.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));
	}
	
	private void insertDatatoTableUser() {
		for(int i = 0; i < ListAllUserData.size(); i++)
		{
			tableViewAllUser.getItems().add(new User(
					ListAllUserData.get(i).getUserId(), ListAllUserData.get(i).getUserRole(),
					ListAllUserData.get(i).getUserEmail(), ListAllUserData.get(i).getUserName(),
					ListAllUserData.get(i).getUserPassword()
					));
		}
	}
	
	private void windowSetTitle() {
		manageUserWindow.setTitle("Manage User");
	}
	
	private void textfieldsStyle() {
		
		//id
		tfId.setDisable(true);
		
	}
	
	
	private void setLayout() {
		manageUserWindow.setPrefSize(900, 1000);
		manageUserWindow.getRightIcons().addAll(new MinimizeIcon(manageUserWindow), new CloseIcon(manageUserWindow));
		
		// flow pane add component label 
			flowLabelManageUserWindow.getChildren().add(labelId);
			flowLabelManageUserWindow.getChildren().add(labelUsername);
			flowLabelManageUserWindow.getChildren().add(labelRole);
			flowLabelManageUserWindow.getChildren().add(labelEmail);
			flowLabelManageUserWindow.getChildren().add(labelPassword);
			
			flowLabelManageUserWindow.setVgap(20);
			flowLabelManageUserWindow.setHgap(20);
			
		
		//flow pane add compnent textfields yg ini bossss
			flowTfManageUserWindow.getChildren().add(tfId);
			flowTfManageUserWindow.getChildren().add(tfUsername);
			flowTfManageUserWindow.getChildren().add(cbRole);
			flowTfManageUserWindow.getChildren().add(tfEmail);
			flowTfManageUserWindow.getChildren().add(pfPassword);
			
			flowTfManageUserWindow.setVgap(20);

			hboxManageUserForm.getChildren().add(tfId);
			hboxManageUserForm.getChildren().add(tfUsername);
			hboxManageUserForm.getChildren().add(cbRole);
			hboxManageUserForm.getChildren().add(tfEmail);
			hboxManageUserForm.getChildren().add(pfPassword);

			hboxManageUserForm.setSpacing(5);
			
			//hbox buttons crud
//			hboxbuttonsCrud.getChildren().add(btnInsert);
//			hboxbuttonsCrud.getChildren().add(btnUpdate);
//			hboxbuttonsCrud.getChildren().add(btnDelete);
			
			
			//set widht
			cbRole.setMinWidth(200);
			
			
		//grid data
		gridContManageUserWindow.add(tableViewAllUser, 3, 2);
		gridContManageUserWindow.add(flowLabelManageUserWindow, 3, 3);

		gridContManageUserWindow.add(flowTfManageUserWindow, 3, 4);
		gridContManageUserWindow.add(hboxManageUserForm, 3, 4);

		gridContManageUserWindow.add(flowtfMode, 3, 5);
		gridContManageUserWindow.add(hboxbuttonsChangeMode, 3, 5);
		
		hboxbuttonsChangeMode.setSpacing(10);
		
//		gridContManageUserWindow.add(flowbuttonsCrud, 4, 5);
//		gridContManageUserWindow.add(hboxbuttonsCrud, 4, 5);
		
		//flow pane buttons mode
		flowtfMode.getChildren().add(insertMode);
		flowtfMode.getChildren().add(changeMode);
		
		hboxbuttonsChangeMode.getChildren().add(insertMode);
		hboxbuttonsChangeMode.getChildren().add(changeMode);
		hboxbuttonsChangeMode.getChildren().add(flowbuttonsCrud);
		
		//flow pane buttons crud 
		flowbuttonsCrud.getChildren().add(btnInsert);
		flowbuttonsCrud.getChildren().add(btnUpdate);
		flowbuttonsCrud.getChildren().add(btnDelete);
		
		//set border pane as user window 
		manageUserWindow.setLayoutX(30);
		manageUserWindow.setLayoutY(30);
		manageUserWindow.getContentPane().getChildren().addAll(bpManageUserWindow);
		bpManageUserWindow.setPadding(new Insets(20));
		
		//border pane add grid pane
		bpManageUserWindow.setTop(gridContManageUserWindow);
		
		
		//set margin for grid container 
		gridContManageUserWindow.setMargin(tableViewAllUser, new Insets(20));
		gridContManageUserWindow.setMargin(flowLabelManageUserWindow, new Insets(20));
		
		
		//add table view user column 
		tableViewAllUser.getColumns().add(userID);
		tableViewAllUser.getColumns().add(userRole);
		tableViewAllUser.getColumns().add(userUsername);
		tableViewAllUser.getColumns().add(userEmail);
		tableViewAllUser.getColumns().add(userPassword);
	}

}
