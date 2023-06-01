
import java.sql.SQLException;
import java.util.Optional;
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.MinimizeIcon;
import jfxtras.labs.scene.control.window.Window;
import model.Waste;
import model.WasteSummary;
import util.Connect;

public class SellWastePage {
	
	Window w;
	Label wasteInventoryLabel;
	
	BorderPane borderContainer;
	GridPane gridContainer;
	
	private TableView<Waste> table ;
	TableColumn<Waste, String> nameColumn;
	TableColumn<Waste, String> typeColumn;
	TableColumn<Waste, Integer> priceColumn;
	TableColumn<Waste, String> descColumn;
	TableColumn<Waste, Integer> weightColumn;
	
	private TableView<WasteSummary> tableWasteSummary;
	TableColumn<WasteSummary, String> nameColumnWasteSummary;
	TableColumn<WasteSummary, Integer> priceColumnWasteSummary;
	TableColumn<WasteSummary, Integer> weightColumnWasteSummary;
	TableColumn<WasteSummary, Integer> totalColumnsWasteSumamry;
	
	
	//get value table view
	TableViewSelectionModel<Waste> selectionWaste;
	ObservableList<Waste> wasteItems;
	
	//get value table view WasteSummary 
	TableViewSelectionModel<WasteSummary> selectWasteSummary;
	ObservableList<WasteSummary> wasteSummaryItems;
	
	
	Button addToCart;
	Button btnSellWasteSummary;
	
	
	private Connect con = Connect.getConnection();
	public Vector<Waste> wasteInventoryList = new Vector<>();
	public Vector<WasteSummary> wasteSummaryList = new Vector<>(); 
	
	Integer userid;
	
	
	public SellWastePage(Integer userid) {
		// TODO Auto-generated constructor stub
		this.userid = userid;
		
		init();
		setLayout();
		setCellValue();
		getWasteInventoryData();
		insertDataToTableView();
		selectRowWaste();
		
		getWasteSummaryData();
		setCellWasteSummaryValue();
		insertDataToTableViewWasteSumamry();
		
		selectRowWasteSummary();
		
		SellWasteSummaryClick();
	}
	
	
	private void selectRowWaste() {
		selectionWaste = table.getSelectionModel();
		selectionWaste.setSelectionMode(SelectionMode.SINGLE);
		wasteItems = selectionWaste.getSelectedItems();
	}
	
	
	private void selectRowWasteSummary() {
		selectWasteSummary = tableWasteSummary.getSelectionModel();
		selectWasteSummary.setSelectionMode(SelectionMode.SINGLE);
		wasteSummaryItems = selectWasteSummary.getSelectedItems();
	}
	
	
	private void init()
	{
		
		 w = new Window("Sell Waste");
		 wasteInventoryLabel = new Label("Waste Inventory");
		 borderContainer = new BorderPane();
		 gridContainer = new GridPane();
		 
		 table = new TableView<Waste>();
		 
		 
		 nameColumn = new TableColumn<>("Name");
		 typeColumn = new TableColumn<>("Type");
		 priceColumn = new TableColumn<>("Price");
		 descColumn = new TableColumn<>("Description");
		 weightColumn = new TableColumn<>("Weight");
		 
		 addToCart = new Button("Add To Cart");
		 
		 btnSellWasteSummary = new Button("Sell");
		 
		 tableWasteSummary = new TableView<WasteSummary>();
		 
		 nameColumnWasteSummary = new TableColumn<>("Name");
		 priceColumnWasteSummary = new TableColumn<>("Price");
		 weightColumnWasteSummary = new TableColumn<>("Weight");
		 totalColumnsWasteSumamry = new TableColumn<>("Total");
		 
		 
		
	}
	
	private void getWasteSummaryData() {
		
		String query_get_waste_summary = "SELECT ws.UserId, ws.WasteId, w.WasteName, w.WastePrice, "
				+ "ws.Weight, (w.WastePrice*ws.Weight) as Total FROM waste_summary ws "
				+ "JOIN waste w ON ws.WasteId = w.wasteId";
		
		con.rs = con.selectQuery(query_get_waste_summary);
		
		try {
			while(con.rs.next()) {
				Integer UserId = con.rs.getInt("UserId");
				Integer WasteId = con.rs.getInt("WasteId");
				String WasteName = con.rs.getString("WasteName");
				Integer WastePrice = con.rs.getInt("WastePrice");
				Integer WasteWeight = con.rs.getInt("Weight");
				Integer Total = con.rs.getInt("Total");
				
				wasteSummaryList.add(new WasteSummary(UserId, WasteId, WasteWeight, WasteName, WastePrice, Total));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void getWasteInventoryData() {
		
		String query = "SELECT wi.UserId,wi.WasteId, wi.Weight ,wa.WasteName, wt.WasteTypeName, wa.WastePrice, wa.WasteDescription, wi.Weight "
				+ "FROM waste_inventory wi JOIN waste wa "
				+ "ON wi.WasteId = wa.WasteId JOIN waste_type wt "
				+ "ON wa.WasteTypeId = wt.WasteTypeId WHERE wi.UserId = " + userid;
		
		System.out.println(query);
		
		con.rs = con.selectQuery(query);
		
		try {
			while(con.rs.next()) { 
				Integer UserId = con.rs.getInt("UserId");
				Integer WasteId = con.rs.getInt("WasteId");
				String WasteName = con.rs.getString("WasteName");
				String WasteTypeName = con.rs.getString("WasteTypeName");
				Integer WastePrice = con.rs.getInt("WastePrice");
				String WasteDescription = con.rs.getString("WasteDescription");
				Integer WasteWeight = con.rs.getInt("Weight");
				
				wasteInventoryList.add(new Waste(UserId,WasteId,WasteName, WasteTypeName, WasteDescription, WasteWeight, WastePrice));

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//insert data to waste summary table view 
	private void insertDataToTableViewWasteSumamry() {
		for(int i = 0; i < wasteSummaryList.size(); i++) {
			tableWasteSummary.getItems().add(new WasteSummary(
					wasteSummaryList.get(i).getUserId(), wasteSummaryList.get(i).getWasteId(),
					wasteSummaryList.get(i).getWasteWeight(), wasteSummaryList.get(i).getWasteName(), 
					wasteSummaryList.get(i).getWastePrice(), wasteSummaryList.get(i).getTotal()));
		}
	}
	
	private void insertDataToTableView() {
		
		for(int i = 0; i < wasteInventoryList.size(); i++) {
			table.getItems().add(new Waste(wasteInventoryList.get(i).getUserId(), wasteInventoryList.get(i).getWasteId(),
					wasteInventoryList.get(i).getWasteName(), 
					wasteInventoryList.get(i).getWasteType(), wasteInventoryList.get(i).getWasteDescription(),
					wasteInventoryList.get(i).getWasteWeight(), wasteInventoryList.get(i).getWastePrice()));
		}
		
//		table.getItems().add(new Waste("haha", "plastic", "hahahihi",10,1000000));
		
	}
	
	private void setCellWasteSummaryValue() {
		nameColumnWasteSummary.setCellValueFactory(new PropertyValueFactory<>("WasteName"));
		priceColumnWasteSummary.setCellValueFactory(new PropertyValueFactory<>("WastePrice"));
		weightColumnWasteSummary.setCellValueFactory(new PropertyValueFactory<>("WasteWeight"));
		totalColumnsWasteSumamry.setCellValueFactory(new PropertyValueFactory<>("Total"));
		
		weightColumnWasteSummary.setCellFactory(new Callback<TableColumn<WasteSummary, Integer>, TableCell<WasteSummary, Integer>>(){

			@Override
			public TableCell<WasteSummary, Integer> call(TableColumn<WasteSummary, Integer> arg0) {
				TableCell<WasteSummary, Integer> cell = new TableCell<WasteSummary, Integer>(){
					@Override
					public void updateItem(Integer item, boolean empty) {
						if(item!=null) {
							Spinner<Integer> weight = new Spinner<Integer>(0, item, 1);
							weight.valueProperty().addListener(
									(obs, oldValue, newValue)->{
										System.out.println(newValue);
									}
								);
							setGraphic(weight);
						}
					}
				};
				return cell;
			}
			
		});
	}
	
	
	private Optional<ButtonType> setAlert(AlertType alertType, String message, String header){
		
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.setHeaderText(header);
		return alert.showAndWait();
		
	}
	
	
	private void SellWasteSummaryClick() {
		
		
		btnSellWasteSummary.setOnAction((e)->{
			String query = "DELETE FROM waste_summary";
			con.executeUpdate(query);
			setAlert(AlertType.INFORMATION, "All wastes in summary has been sold!","Notification");
		});
	}
	
	private void setCellValue() {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("wasteName"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("wasteType"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("WastePrice"));
		descColumn.setCellValueFactory(new PropertyValueFactory<>("WasteDescription"));
		weightColumn.setCellValueFactory(new PropertyValueFactory<>("wasteWeight"));
		
	}
	
	private void setLayout()
	{
		w.setLayoutX(30);
		w.setLayoutY(30);
		
		
//		btnSellWasteSummary.setDisable(true);
		
		//gridContainer set Pos
		gridContainer.add(table, 0, 0);
		gridContainer.add(addToCart, 0, 2);
		gridContainer.add(tableWasteSummary, 0, 3);
		gridContainer.add(btnSellWasteSummary, 1, 3);
		
		//Waste Label set Position 
		borderContainer.setTop(wasteInventoryLabel);
		borderContainer.setCenter(gridContainer);
		
		
		
		
		borderContainer.setPadding(new Insets(30,30,30,30));
		
		
		w.setPrefSize(700, 600);
		w.getRightIcons().addAll(new MinimizeIcon(w), new CloseIcon(w));
		
		w.getContentPane().getChildren().addAll(borderContainer);
		
		
		table.getColumns().add(nameColumn);
		table.getColumns().add(typeColumn);
		table.getColumns().add(priceColumn);
		table.getColumns().add(descColumn);
		table.getColumns().add(weightColumn);
		
		tableWasteSummary.getColumns().add(nameColumnWasteSummary);
		tableWasteSummary.getColumns().add(priceColumnWasteSummary);
		tableWasteSummary.getColumns().add(weightColumnWasteSummary);
		tableWasteSummary.getColumns().add(totalColumnsWasteSumamry);
		
		
		
		
		
	}
}