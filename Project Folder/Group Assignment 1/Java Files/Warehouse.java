import java.util.*;

public class Warehouse 
{
	private String warehouseID;
	private List<Shipment> shipList = new ArrayList<Shipment>();
	private boolean availability = true;
	
	public Warehouse(String warehouseID)
	{
		this.warehouseID = warehouseID;
	}
	public void enableFreightReceipt()
	{
		availability = true;
	}
	public void disableFreightReceipt()
	{
		availability = false;
	}
	public boolean  getAvailability()
	{
		return availability;
	}
	public Shipment addShipment(Shipment shipment)
	{ 
		if(availability == true)
		{ // add shipment to warehouse
			shipList.add(shipment);
			return shipment;
		}
		else
		{ // warehouse is closed
			System.out.println("Sorry that warehouse is not accepting shipments");
			return null;
		}
	}
	public String getWarehouseID()
	{
		return warehouseID;
	}
	public List<Shipment> getShipmentList()
	{
		return shipList;
	}
}
