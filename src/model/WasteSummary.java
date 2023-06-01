package model;

public class WasteSummary {
	
	private Integer UserId, WasteId, WasteWeight, WastePrice, Total;
	String WasteName;
	
	public WasteSummary(Integer UserId, Integer WasteId, Integer WasteWeight, String WasteName, Integer WastePrice, Integer Total) {
		this.UserId = UserId;
		this.WasteId = WasteId;
		this.WasteWeight = WasteWeight;
		this.WasteName = WasteName;
		this.WastePrice = WastePrice;
		this.Total = Total;
	}

	public Integer getWastePrice() {
		return WastePrice;
	}

	public void setWastePrice(Integer wastePrice) {
		WastePrice = wastePrice;
	}

	public Integer getTotal() {
		return Total;
	}

	public void setTotal(Integer total) {
		Total = total;
	}

	public String getWasteName() {
		return WasteName;
	}

	public void setWasteName(String wasteName) {
		WasteName = wasteName;
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

	public Integer getWasteWeight() {
		return WasteWeight;
	}

	public void setWasteWeight(Integer wasteWeight) {
		WasteWeight = wasteWeight;
	}
	
	

}
