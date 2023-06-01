import java.util.Vector;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.MinimizeIcon;
import jfxtras.labs.scene.control.window.Window;
import model.WasteDashboard;
import util.Connect;

public class ManageWasteForm {
	
	Window manageWasteWindow;
	
	BorderPane borderCont;
	GridPane gridCont;
	
	TableView<WasteDashboard> tableViewAllWaste;
	TableColumn<WasteDashboard, Integer> wasteId;
	TableColumn<WasteDashboard, String> wasteName;
	TableColumn<WasteDashboard, String> wasteType;
	TableColumn<WasteDashboard, Integer> wastePrice;
	TableColumn<WasteDashboard, String> wasteDescription;
	
	TextField tfWasteId, tfWasteName;
	ComboBox<String> cbWasteType;
	TextArea taWasteDesc;
	Spinner<Integer> spwastePrice;
	
	Label lblWasteId, lblWasteName, lblWasteType, lblWastePrice, lblWasteDesc;
	
	
	VBox idWasteVbox, nameWasteVbox, typeWasteVbox, priceWasteVbox, descVbox;
	HBox inputTfWrapBox, btnWrapBox;
	VBox wrapChangeMode;
	
	Button nextDay, insertMode, changeMode, insertData, updateData, deleteData;
	
	Vector<WasteDashboard> wasteList = new Vector<>();
	
	private Connect conn = Connect.getConnection();
	
	public ManageWasteForm() {
		// TODO Auto-generated constructor stub
		init();
		setLayout();
		
		getWasteData();
		
		setCellColumnValue();
		
		insertWastetoTable();
	}
	
	private void init() {
		manageWasteWindow = new Window();
		
		borderCont = new BorderPane();
		gridCont = new GridPane();
		
		tableViewAllWaste = new TableView<>();
		wasteId = new TableColumn<>("ID");
		wasteName = new TableColumn<>("Name");
		wasteType = new TableColumn<>("Type");
		wastePrice = new TableColumn<>("Price");
		wasteDescription = new TableColumn<>("Description");
		
		spwastePrice = new Spinner<>();
		
		//text field 
		tfWasteId = new TextField();
		tfWasteName = new TextField();
		
		//text area
		taWasteDesc = new TextArea();
		
		//label 
		lblWasteId = new Label("ID");
		lblWasteName = new Label("Name");
		lblWasteType = new Label("Type");
		lblWastePrice = new Label("Price");
		lblWasteDesc = new Label("Description");
		
		//spinner price 
		SpinnerValueFactory<Integer> spValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
		spValueFactory.setValue(0);
		spwastePrice.setValueFactory(spValueFactory);
		
		//button 
		nextDay = new Button("NextDay >>");
		insertMode = new Button("Insert Mode");
		changeMode = new Button("Change Mode");
		insertData = new Button("Insert");
		updateData = new Button("Update");
		deleteData = new Button("Delete");
		
		
		//cb box
		cbWasteType = new ComboBox<>();
		cbWasteType.getItems().add("Plastic");
		cbWasteType.getItems().add("Paper");
		cbWasteType.getItems().add("Metal");
		cbWasteType.getItems().add("Price");
		
		
		//vbox
		idWasteVbox = new VBox(lblWasteId, tfWasteId);
		nameWasteVbox = new VBox(lblWasteName, tfWasteName);
		typeWasteVbox = new VBox(lblWasteType, cbWasteType);
		priceWasteVbox = new VBox(lblWastePrice, spwastePrice);
		descVbox = new VBox(lblWasteDesc, taWasteDesc);
		
		//wrap change mode 
		wrapChangeMode = new VBox(insertMode, changeMode);
		
		//btn wrap box 
		btnWrapBox = new HBox(nextDay, insertMode, wrapChangeMode, updateData, deleteData); 
		
		
		//hbox 
		inputTfWrapBox = new HBox(idWasteVbox, nameWasteVbox, typeWasteVbox, priceWasteVbox, descVbox);
	}
	
	private void setLayout() {
		manageWasteWindow.setPrefSize(900, 10000);
		manageWasteWindow.getRightIcons().addAll(new MinimizeIcon(manageWasteWindow), new CloseIcon(manageWasteWindow));
		manageWasteWindow.setTitle("Manage Waste Bank");
		
		manageWasteWindow.getContentPane().getChildren().addAll(borderCont);
		borderCont.setPadding(new Insets(20));
		
		//add column to table
		tableViewAllWaste.getColumns().add(wasteId);
		tableViewAllWaste.getColumns().add(wasteName);
		tableViewAllWaste.getColumns().add(wasteType);
		tableViewAllWaste.getColumns().add(wastePrice);
		tableViewAllWaste.getColumns().add(wasteDescription);
		
		//
		taWasteDesc.setPrefWidth(400);
		
		cbWasteType.setMinWidth(150);
		
		//set margin input data
		HBox.setMargin(idWasteVbox, new Insets(10));
		HBox.setMargin(nameWasteVbox, new Insets(10));
		HBox.setMargin(typeWasteVbox, new Insets(10));
		HBox.setMargin(priceWasteVbox, new Insets(10));
		HBox.setMargin(descVbox, new Insets(10));
		
		//set margin hbox button data 
		HBox.setMargin(nextDay, new Insets(10, 10, 10, 10));
		HBox.setMargin(wrapChangeMode, new Insets(10, 0,0,0));
		HBox.setMargin(insertData, new Insets(10, 10, 10, 10));
		HBox.setMargin(updateData, new Insets(10, 10, 10, 10));
		
		
		
		
		borderCont.setTop(gridCont);
		
		gridCont.add(tableViewAllWaste, 1, 1);
		gridCont.add(inputTfWrapBox, 1, 2);
		gridCont.add(btnWrapBox, 1, 3);
		
		
	}
	
	private void getWasteData() {
		
		String all_query = "SELECT * FROM waste JOIN waste_type wt ON waste.WasteTypeId = wt.WasteTypeId";
		
		System.out.println(all_query);
		
		conn.rs = conn.selectQuery(all_query);
		
		try {
			
			while (conn.rs.next()) {
				Integer WasteId = conn.rs.getInt("WasteId");
				String WasteName = conn.rs.getString("WasteName");
				String WasteType = conn.rs.getString("WasteTypeName");
				Integer WastePrice = conn.rs.getInt("WastePrice");
				String WasteDescription = conn.rs.getString("WasteDescription");
				
				wasteList.add(new WasteDashboard(WasteId,WasteName,WasteType,WastePrice,WasteDescription));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void setCellColumnValue() {
		wasteId.setCellValueFactory(new PropertyValueFactory<>("WasteId"));
		wasteName.setCellValueFactory(new PropertyValueFactory<>("WasteName"));
		wasteType.setCellValueFactory(new PropertyValueFactory<>("WasteTypeName"));
		wastePrice.setCellValueFactory(new PropertyValueFactory<>("WastePrice"));
		wasteDescription.setCellValueFactory(new PropertyValueFactory<>("WasteDescription"));
	}
	
	
	
	private void insertWastetoTable() {
		for(int i = 0; i < wasteList.size(); i++) {
			tableViewAllWaste.getItems().add(new WasteDashboard(wasteList.get(i).getWasteId(),
					wasteList.get(i).getWasteName(), wasteList.get(i).getWasteTypeName(), wasteList.get(i).getWastePrice(),
					wasteList.get(i).getWasteDescription()));
		}
	}


}
