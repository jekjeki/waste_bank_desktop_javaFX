import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class AdminMainForm {
	
	BorderPane borderPn;
	MenuBar adminMenuBar;
	Menu adminMenuManage, adminMenuUser;
	MenuItem manageUserMI, manageWasteMI;
	
	
	public AdminMainForm()
	{
		init();
		setMenu();
		setPos();
	}
	
	private void init() {
		borderPn = new BorderPane();
		adminMenuBar = new MenuBar();
		adminMenuManage = new Menu("Manage");
		adminMenuUser = new Menu("User");
		
		manageUserMI = new MenuItem("Manage User");
		manageWasteMI = new MenuItem("Manage Waste Bank");
	}
	
	private void setMenu()
	{
		adminMenuBar.getMenus().addAll(adminMenuManage, adminMenuUser);
		
		adminMenuManage.getItems().add(manageUserMI);
		adminMenuManage.getItems().add(manageWasteMI);
	}
	
	private void setPos()
	{
		borderPn.setTop(adminMenuBar);
	}
}
