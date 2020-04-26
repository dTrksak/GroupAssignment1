package edu.metrostate.ics372_androidstart_master;

import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//WarehouseHandler contains the warehouses and all the methods for the warehouses
public class WarehouseHandler
{
	List<Warehouse> warehouseList = new ArrayList<>();
	private String msg = null;

	// static variable single_instance of type Singleton
	private static WarehouseHandler warehouse_instance = null;

	/**
	 * Singleton implementation, there should only be one class of warehouseHandler
	 * @return
	 */
	public static WarehouseHandler getInstance()
	{
		if (warehouse_instance == null) warehouse_instance = new WarehouseHandler();

		return warehouse_instance;
	}

	/**
	 * Gets a list of shipments from a single warehouse
	 * @param warehouseID
	 * @return a linked list of shipments from a warehouse, null if the warehouse
	 *         doesn't exist
	 */
	public List<Shipment> getWarehouseShipments(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID); // Get the warehouse in warehouseList using warehouseID

		// Check that the warehouse exists
		if (w != null)
		{
			return w.getShipmentList(); // Replace getShipmentList() if needed
		}
		else
		{
			// If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist.\n");
			return null;
		}
	}

	/**
	 * Gets a list of shipments from all warehouses
	 * @return an ArrayList of shipments from all warehouses, null if no warehouses
	 *         exist
	 */
	public ArrayList<Shipment> getAllWarehouseShipments()
	{
		// Check that warehouseList is not empty
		if (warehouseList.isEmpty())
		{
			// If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, no warehouses exist.\n"); // \n is return?
			return null;
		}
		else
		{
			ArrayList<Shipment> outputList = new ArrayList<>();
			for (int i = 0; i < warehouseList.size(); i++)
			{
				Warehouse w = warehouseList.get(i);
				outputList.addAll(w.getShipmentList()); // Replace getShipmentList() if needed
			}
			return outputList;
		}
	}

	/**
	 * Getter for the warehouse list
	 * @return an arraylist of warehouses
	 */
	public List<Warehouse> getAllWarehouses()
	{
		if (warehouseList.size() == 0)
		{
			return null;
		}
		return warehouseList;
	}

	/**
	 * Finds a warehouse in warehouseList given the warehouseID
	 * @param warehouseID
	 * @return a Warehouse object if found, null if not found
	 */
	public Warehouse getWarehouse(String warehouseID)
	{
		// Iterate through warehouseList checking warehouses until the warehouseID is
		// found
		for (int i = 0; i < warehouseList.size(); i++)
		{
			if (warehouseList.get(i).getWarehouseID().equals(warehouseID)) // Replace getWarehouseID if needed
			{
				return warehouseList.get(i); // Return the warehouse
			}
		}
		return null;
	}

	/**
	 * Creates a warehouse given a warehouse ID
	 * @param warehouseID
	 */
	public Warehouse addWarehouse(String warehouseID, String warehouseName)
	{
		Warehouse w = getWarehouse(warehouseID);

		// Check that the warehouse doesn't exist before creating a new one
		if (w == null)
		{
			Warehouse n = new Warehouse(warehouseID, warehouseName); // Replace warehouseID if needed
			warehouseList.add(n);
			return n;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Adds a list of shipments to the warehouse list
	 * @param list of shipments
	 */
	public void addShipmentList(List<Shipment> list)
	{
		for (Shipment s : list)
		{
			addShipment(s.getWarehouseID(), s.getWarehouseName(), s.getShipmentID(), s.getShipmentMethod(), s.getWeight(), s.getReceiptDate());
		}
	}

	/**
	 * Gets a specified warehouse's receipt
	 *
	 * @param warehouseID
	 * @return the warehouse receipt, null if the warehouse doesn't exist
	 */
	public Boolean getWarehouseReceipt(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID);

		// Check that the warehouse exists
		if (w != null)
		{
			return w.getAvailability();
		}
		else
		{
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist.\n");
			return null;
		}
	}

	/**
	 * Sets a boolean value in a specified warehouse
	 *
	 * @param warehouseID
	 * @param input       what the boolean should be set to
	 */
	public void setWarehouseReceipt(String warehouseID, boolean input)
	{
		Warehouse w = getWarehouse(warehouseID);

		// Check that the warehouse exists
		if (w != null)
		{
			// Check that the user input will change the receipt
			if (input == w.getAvailability())
			{
				System.out.print("Warehouse " + warehouseID + "'s receipt is already set to " + input + ".\n");
			}
			else
			{
				if (input == false)
				{
					w.disableFreightReceipt();
				}
				else
				{
					w.enableFreightReceipt();
				}
			}
		}
		else
		{
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist.\n");
		}
	}

	/**
	 * Adds a shipment to the specified warehouse
	 *
	 * @param warehouseID
	 * @param shipmentID
	 * @param shipmentMethod
	 * @param weight
	 * @param receiptDate
	 */
	public Shipment addShipment(String warehouseID, String warehouseName, String shipmentID, String shipmentMethod, float weight, long receiptDate)
	{
		Warehouse w = getWarehouse(warehouseID);

		if (w == null) // if warehouse doesn't exist create it
		{
			w = addWarehouse(warehouseID, warehouseName);
		}

		if(w.getAvailability() && w.getWarehouseName().equals(warehouseName)) { //Prevents warehouses with identical ids but different names
			Shipment s = new Shipment(warehouseID, warehouseName, shipmentID, shipmentMethod, weight, receiptDate);
			Shipment s2 = w.addShipment(s);
			if (s2 != null) {
				msg = "Shipment " + shipmentID + " successfully added";
				try {
					RecoverData.saveData();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return s;
			}
			msg = "Shipment " + shipmentID + " already exists cannot create duplicate shipments";
			return null;
		}else{

			if(!w.getWarehouseName().equals(warehouseName)) {
				msg = "Warehouse "+w.getWarehouseID()+"'s name is not correct, it should be '"+w.getWarehouseName()+"'.";
				return null;
			} else {
				msg = "Warehouse is not receiving shipments at this time";
				return null;
			}
		}
	}

	/**
	 * Removes a shipment from a specified warehouse
	 *
	 * @param warehouseID
	 * @param shipmentID
	 */
	public Shipment removeShipment(String warehouseID, String shipmentID) {
		Warehouse w = getWarehouse(warehouseID);
		if (w == null) {
			msg = "Warehouse " + warehouseID + " does not exist, please double check the Warehouse ID";
			return null;
		}
		Shipment s = w.removeShipment(shipmentID);
		if (s != null) {
			msg = "Shipment " + shipmentID + " successfully removed!";
			try {
				RecoverData.saveData();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s;
		} else {
			msg = "Shipment " + shipmentID + " not found in Warehouse " + shipmentID;
			return null;
		}

	}

	/**
	 * Shows the data entered in the current session
	 * @param textView
	 */
	public void showAllData(TextView textView) //prints all warehouse and shipments into the view
	{
		List<Warehouse> list = getAllWarehouses();
		textView.setText(""); // clears out any previous text in the view
		if(list != null) {
			for (int i = 0; i < list.size(); i++)
			{
				textView.append("\nWarehouse " + list.get(i).getWarehouseID() + ", " + list.get(i).getWarehouseName() + " - \t" + list.get(i).getShipmentList().toString() + "\n");
			}
			textView.setMovementMethod(new ScrollingMovementMethod());
		}else{
			textView.setText("No warehouses to display");
		}
	}

	/**
	 * Shows the data entered in the current session
	 * @param textView
	 */
	public void showData(String warehouseID, TextView textView) //prints one warehouses and its shipments into the view
	{
		Warehouse w = getWarehouse(warehouseID);
		textView.setText(""); // clears out any previous text in the view
		List<Shipment> list = w.getShipmentList();
		if(list != null) {
			for (int i = 0; i < list.size(); i++)
			{
				textView.append("\nWarehouse " + warehouseID + ", " + w.getWarehouseName() + " - \t" + list.toString() + "\n");
			}
			textView.setMovementMethod(new ScrollingMovementMethod());
		}else{
			textView.setText("No shipments to display");
		}
	}

	public String getMessage(){
		return msg;
	}

}
