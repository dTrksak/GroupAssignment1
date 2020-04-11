
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

public class Shipment
{
	String warehouse_id;
	String warehouse_name;
	String shipment_id;
	String shipment_method;
	float weight;
	long receipt_date;
	long leave_date;
	private List<String> shipHistory = new ArrayList<String>();
	
	//creates a shipment object
	public Shipment(String warehouseID, String warehouseName, String shipmentID, String shipmentMethod, float weight, long receiptDate)
	{
		this.warehouse_id = warehouseID;
		this.warehouse_name = warehouseName;
		this.shipment_id = shipmentID;
		this.shipment_method = shipmentMethod;
		this.weight = weight;
		this.receipt_date = receiptDate;
	}
	
	public String getWarehouseID()
	{
		return this.warehouse_id;
	}
	public String getWarehouseName() {
		return this.warehouse_name;
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
	public long getLeaveDate()
	{
		return this.leave_date;
	}
	public void setLeaveDate(long leave) 
	{
		this.leave_date = leave;
	}
	@Override
	public String toString() {
		DateFormat simple = new SimpleDateFormat("MMM dd yyy HH:mm:ss");
		Date result = new Date(this.getReceiptDate());
		return ("\n\t 	    Shipment ID: "+ this.getShipmentID() + 
	                    ",  Shipment Method:  "+ this.getShipmentMethod() +
	                    ",  Weight : " + this.getWeight() +
	                    ",  Receipt Date: " + simple.format(result));
	   }
}
