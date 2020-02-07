public class Shipment
{
	String warehouseID;
	String shipmentID;
	String shipmentMethod;
	float weight;
	long receiptDate;
	
	public Shipment(String warehouseID, String shipmentID, String shipmentMethod, float weight, long receiptDate)
	{
		this.warehouseID = warehouseID;
		this.shipmentID = shipmentID;
		this.shipmentMethod = shipmentMethod;
		this.weight = weight;
		this.receiptDate = receiptDate;
	}
	
	public String getWarehouseID()
	{
		return this.warehouseID;
	}
	public String getShipmentID()
	{
		return this.shipmentID;
	}
	public String getShipmentMethod()
	{
		return this.shipmentMethod;
	}
	public float getWeight()
	{
		return this.weight;
	}
	public long getReceiptDate()
	{
		return this.receiptDate;
	}
	@Override
	public String toString() {
	        return ("\nwarehouse ID: "+this.getWarehouseID()+
	                    ", Shipment ID: "+ this.getShipmentID() +
	                    ", Shipment Method: "+ this.getShipmentMethod() +
	                    ", Weight : " + this.getWeight() +
	                    ", ReceiptDate : " + this.getReceiptDate());
	   }
}
