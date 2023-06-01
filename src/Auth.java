import java.util.Vector;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.LoginModel;
import model.User;
import model.UserLogin;
import model.Waste;
import util.Connect;

public class Auth extends Application{
	
	Scene scene, scene2, userMainScene, sceneSellWaste, adminMainScene;
	BorderPane borderContainer;
	GridPane gridContainer;
	FlowPane flowContainer;
	Label titleLbl, usernameLbl, passwordLbl;
	TextField tfUsername;
	PasswordField tfPassword;
	Button loginBtn, registerBtn;
	
	boolean isLogin = false;
	boolean isAdmin = false;
	
	String userole = null;
	Integer logid = null;
	
	LoginModel lm;
	
	
	private Vector<User> loginusers = new Vector<>();
	public Vector<UserLogin> logs ;
	
	SellWastePage swp;

	
	
	//get connection database 
	private Connect connect = Connect.getConnection();
	
	
	
	
	
	private void initialize()
	{
		borderContainer = new BorderPane();
		flowContainer = new FlowPane();
		gridContainer = new GridPane();
		
		
		titleLbl = new Label("LOGIN");
		usernameLbl = new Label("username");
		passwordLbl = new Label("password");
		
		
		tfUsername = new TextField();
		tfPassword = new PasswordField();
		registerBtn = new Button("Register");
		loginBtn = new Button("Login");
		
		
		
		
		scene = new Scene(borderContainer, 500, 450);
	
		
	}
	
	private void getUsersData()
	{
		loginusers.removeAllElements();
		
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
				
				loginusers.add(new User(id, UserRole, UserEmail, UserName, UserPassword));
			}
			
		} catch(Exception e){
			
		}
		
	}
	
	private void getLoginData()
	{
		
		String username = tfUsername.getText();
		String password = tfPassword.getText();
		boolean flag = false;
		
		String roles = connect.getUserRoles(username, password);
		
//		lm = new LoginModel(username, password, userole);
//		
//		if(!(username.equals("admin") && password.equals("admin")))
//		{
//			userole = "Customer";
//		}
//		
//		if(tfUsername.getText().contains("admin") && tfPassword.getText().contains("admin"))
//		{
//			lm.setUserole("Admin");
//			String adminrole = "Admin";
//		
//			flag = connect.valdiateLogin(username, password, adminrole);
//		}
//		else {
//			flag = connect.valdiateLogin(username, password, userole);
//			
//		}
//		
		flag = connect.valdiateLogin(username, password, roles);
		
		if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty())
		{
			showErrorAlert(Alert.AlertType.ERROR, "error", "All field must be filled !");
		}
		else if(!flag)
		{
			showErrorAlert(Alert.AlertType.ERROR, "error", "invalid username password !");
		}
		else 
		{
			showSuccessAlert(Alert.AlertType.CONFIRMATION, "success", "login successful !");
			isLogin = true;
			
		}

		
	}
	
	
	private void setPos()
	{
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(gridContainer, Pos.CENTER);
		
		borderContainer.setPadding(new Insets(20));
		
	}
	
	private void addComponent()
	{
		borderContainer.setTop(titleLbl);
		borderContainer.setCenter(gridContainer);
		
		flowContainer.getChildren().add(registerBtn);
		flowContainer.getChildren().add(loginBtn);
		
		tfPassword.setMinWidth(200);
		tfUsername.setMinWidth(200);
		
		gridContainer.add(usernameLbl, 0, 0);
		gridContainer.add(passwordLbl, 0, 1);
		gridContainer.add(flowContainer, 0, 2);
		
		
		
		gridContainer.add(tfUsername, 1, 0);
		gridContainer.add(tfPassword, 1, 1);
		
		
	}
	
	public Pane getLoginPane()
	{
		return borderContainer;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}
	
	//show error alert REGISTER PAGE 
	private void showErrorAlert(Alert.AlertType alertType, String title,
			String message)
	{
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();;
	}
	
	//show success alert LOGIN PAGE 
	private void showSuccessAlert(Alert.AlertType alertType, String title,
			String message)
	{
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		

		initialize();
		addComponent();
		setPos();
		getUsersData();
	
		//Scene Customer Main Form Page 
		CustomerMainForm cmf = new CustomerMainForm();
		userMainScene = new Scene(cmf.borderContainer, 700, 600);
		
		
		//Scene AdminMainForm
		AdminMainForm amf = new AdminMainForm();
		adminMainScene = new Scene(amf.borderPn, 700, 600);
		
		

		
		//btn login 
		loginBtn.setOnAction(e -> {
			getLoginData();
			
			String userrole = connect.getUserRoles(tfUsername.getText(), tfPassword.getText());
			
			Integer id = connect.getIdUsers(tfUsername.getText(), tfPassword.getText());
			System.out.println(id);
			
			
			
			if(userrole.equals("Admin"))
			{
				arg0.setScene(adminMainScene);
				arg0.setTitle("admin dashboard");
			}
			else if(userrole.equals("Customer"))
			{
				arg0.setScene(userMainScene);
				
			}
		
			//Scene Sell Waste Internal Page
			swp = new SellWastePage(id);
			
		});
		
		

			
			//cmf sell waste menu button 
			cmf.custMI1.setOnAction(es->{
				cmf.borderContainer.getChildren().add(swp.w);
				
				
				

				swp.addToCart.setDisable(true);

				//select listener table waste summary 
				swp.wasteItems.addListener(
						new ListChangeListener<Waste>() {
							
							@Override
							public void onChanged(Change<? extends Waste> arg0) {
								
								
								
								boolean isSelect = true;
								
								
								if(isSelect == true)
								{
									swp.addToCart.setDisable(false);
								}
								
								swp.addToCart.setOnAction(e -> {
									
									boolean isDone = false;
									
									String insert_waste_summary_query = "INSERT INTO waste_summary "
											+ "VALUES ("+swp.wasteItems.get(0).getUserId()+","+swp.wasteItems.get(0).getWasteId()+","+swp.wasteItems.get(0).getWasteWeight()+")";
									
									
									
									for(int i = 0; i < swp.wasteSummaryList.size(); i++)
									{
										if (swp.wasteSummaryList.get(i).getWasteId() == swp.wasteItems.get(0).getWasteId())
										{
											isDone = true;
											showErrorAlert(AlertType.ERROR, "error", "waste already in cart !");
										}
									}
									
									if(isDone == false)
									{
										connect.executeUpdate(insert_waste_summary_query);
									}
									
									
								});
								
							}
							
						}
						);
				
			});
			
	
		
		
		

		
		
		//btn register to register page
		registerBtn.setOnAction(e -> {
			arg0.setScene(scene2);
			
		});
		
		Register r = new Register();
		scene2 = new Scene(r.borderContainer, 500, 200);
		
		//register back button to login 
		r.backBtn.setOnAction(e->{
			arg0.setScene(scene);
		});
		
		
		//Register Add insert Button 
		r.registerBtn.setOnAction(e-> {
			r.validateUserInput();
		});
		
		
		
		//ManageUserForm page
		ManageUserForm muf = new ManageUserForm();
		
		
		//amf menu item manage user
		amf.manageUserMI.setOnAction(e->{
			amf.borderPn.getChildren().add(muf.manageUserWindow);
		});
		
		//ManageWasteForm Page 
		ManageWasteForm mwf = new ManageWasteForm();
		
		//amf menu item manage waste bank 
		amf.manageWasteMI.setOnAction(e->{
			amf.borderPn.getChildren().add(mwf.manageWasteWindow);
		});
		
		
		arg0.setScene(scene);
		arg0.setTitle("Authentication");
		arg0.setResizable(true);
		arg0.show();
		
	}

}
