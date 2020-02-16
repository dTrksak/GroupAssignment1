public class Shipment
{
	String warehouse_id;
	String shipment_id;
	String shipment_method;
	float weight;
	long receipt_date;
	
	//creates a shipment object
	public Shipment(String warehouseID, String shipmentID, String shipmentMethod, float weight, long receiptDate)
	{
		this.warehouse_id = warehouseID;
		this.shipment_id = shipmentID;
		this.shipment_method = shipmentMethod;
		this.weight = weight;
		this.receipt_date = receiptDate;
	}
	
	public String getWarehouseID()
	{
		return this.warehouse_id;
	}
	public String getShipmentID()
	{
		return this.shipment_id;
	}
	public String getShipmentMethod()
	{
		return this.shipment_method;
	}
	public float getWeight()
	{
		return this.weight;
	}
	public long getReceiptDate()
	{
		return this.receipt_date;
	}
	@Override
	public String toString() {
	        return ("\n\twarehouse ID: "+this.getWarehouseID()+
	                    ", Shipment ID: "+ this.getShipmentID() +
	                    ", Shipment Method: "+ this.getShipmentMethod() +
	                    ", Weight : " + this.getWeight() +
	                    ", ReceiptDate : " + this.getReceiptDate());
	   }
}
