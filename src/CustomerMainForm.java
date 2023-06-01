
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;


public class CustomerMainForm {

	
	BorderPane borderContainer;
	MenuBar custmenubar;
	Menu custMenu, custMenu2;
	MenuItem custMI1;

	
	public CustomerMainForm() {
		setMenu();
		init();
		setPos();
		
	}
	
	
	private void init()
	{
		borderContainer = new BorderPane();
		borderContainer.setTop(custmenubar);
		
	}
	
	private void setMenu()
	{
		
		
		custmenubar = new MenuBar();
		
		
		//init menu item 
		custMI1 = new MenuItem("Sell Waste");
		
		
		
		//init menu 
		custMenu = new Menu("Bank");
		custMenu2 = new Menu("User");
		
		// add menu item 
		custMenu.getItems().add(custMI1);
		
		
		custmenubar.getMenus().addAll(custMenu, custMenu2);
		
		
		
	}
	
	private void setPos()
	{
		
	}

}
