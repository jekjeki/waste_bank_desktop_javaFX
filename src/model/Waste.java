package model;

public class Waste {
	
	private String wasteName, wasteType, wasteDescription;
	private Integer wasteWeight, wastePrice;
	private Integer UserId, WasteId;
	public Waste(Integer UserId, Integer WasteId ,String wasteName, String wasteType, String wasteDescription, Integer wasteWeight, Integer wastePrice) {
		super();
		this.UserId = UserId;
		this.WasteId = WasteId;
		
		this.wasteName = wasteName;
		this.wasteType = wasteType;
		this.wasteDescription = wasteDescription;
		this.wasteWeight = wasteWeight;
		this.wastePrice = wastePrice;
	}
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
	public Integer getWasteId() {
		return WasteId;
	}
	public void setWasteId(Integer wasteId) {
		WasteId = wasteId;
	}
	
	public String getWasteName() {
		return wasteName;
	}
	public void setWasteName(String wasteName) {
		this.wasteName = wasteName;
	}
	public String getWasteType() {
		return wasteType;
	}
	public void setWasteType(String wasteType) {
		this.wasteType = wasteType;
	}
	public String getWasteDescription() {
		return wasteDescription;
	}
	public void setWasteDescription(String wasteDescription) {
		this.wasteDescription = wasteDescription;
	}
	public Integer getWasteWeight() {
		return wasteWeight;
	}
	public void setWasteWeight(Integer wasteWeight) {
		this.wasteWeight = wasteWeight;
	}
	public Integer getWastePrice() {
		return wastePrice;
	}
	public void setWastePrice(Integer wastePrice) {
		this.wastePrice = wastePrice;
	}
	
	
	


	

}
