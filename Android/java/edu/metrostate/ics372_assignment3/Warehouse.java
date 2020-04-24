package edu.metrostate.ics372_assignment3;
import java.util.*;

public class Warehouse 
{
	private String warehouseID;
	private String warehouseName;
	private List<Shipment> shipList = new ArrayList<Shipment>();
	private boolean availability = true;
	
	public Warehouse(String warehouseID, String warehouseName)
	{
		this.warehouseID = warehouseID;
		this.warehouseName = warehouseName;
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
	
	public Shipment removeShipment(String shipmentID)
	{
		for(int i = 0; i < shipList.size(); i++) {
			if(shipList.get(i).getShipmentID().equals(shipmentID))
			{
				Shipment s = shipList.get(i);
				System.out.println(s);
				shipList.remove(i);
				return s;
			}
		}
		return null;
	}

	public String getWarehouseID()
	{
		return warehouseID;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public List<Shipment> getShipmentList()
	{
		return shipList;
	}
}
