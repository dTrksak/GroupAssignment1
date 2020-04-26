package edu.metrostate.ics372_androidstart_master;
import android.util.Log;
import android.widget.TextView;

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
		for(int i = 0; i < shipList.size(); i++){ // check to see if shipment ID is unique
			if(shipList.get(i).shipment_id.equals(shipment.shipment_id)){
				return null;//double shipment
			}
		}
		shipList.add(shipment);
		return shipment;

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
