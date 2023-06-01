package model;

public class WasteDashboard {

	int WasteId, WastePrice; String wasteTypeName;
	String WasteName, WasteDescription;
	public WasteDashboard(int wasteId, String wasteName, String wasteTypeName, int wastePrice, String wasteDescription) {
		super();
		WasteId = wasteId;
		WastePrice = wastePrice;
		this.wasteTypeName = wasteTypeName;
		WasteName = wasteName;
		WasteDescription = wasteDescription;
	}
	public int getWasteId() {
		return WasteId;
	}
	public void setWasteId(int wasteId) {
		WasteId = wasteId;
	}
	public int getWastePrice() {
		return WastePrice;
	}
	public void setWastePrice(int wastePrice) {
		WastePrice = wastePrice;
	}
	public String getWasteTypeName() {
		return wasteTypeName;
	}
	public void setWasteTypeName(String wasteTypeName) {
		this.wasteTypeName = wasteTypeName;
	}
	public String getWasteName() {
		return WasteName;
	}
	public void setWasteName(String wasteName) {
		WasteName = wasteName;
	}
	public String getWasteDescription() {
		return WasteDescription;
	}
	public void setWasteDescription(String wasteDescription) {
		WasteDescription = wasteDescription;
	}
	
	
	
	

}
