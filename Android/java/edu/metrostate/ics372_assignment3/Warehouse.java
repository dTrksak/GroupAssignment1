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

	public Shipment addShipment(Shipment shipment) //
	{
		for(int i = 0; i < shipList.size(); i++){ // check to see if shipment ID is unique
			if(shipList.get(i).shipment_id.equals(shipment.shipment_id)){
				Log.i("IM HERE", "duplicate Warehouse class");
				System.out.println("Sorry shipment with id: " + shipment.shipment_id + " already exists cannot create duplicates");
				return null;//double shipment
			}
		}
		if(availability == true)
		{
			shipList.add(shipment);
			System.out.println("Shipment successfully added");
			return shipment;
		} else {
			Log.i("IM HERE", "Sorry this warehouse is not accepting shipments");
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
				System.out.println("Shipment " + shipmentID + " successfully removed");
				return s;
			}
		}
		System.out.println("Shipment " + shipmentID + " not found in warehouse " + warehouseID);
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
