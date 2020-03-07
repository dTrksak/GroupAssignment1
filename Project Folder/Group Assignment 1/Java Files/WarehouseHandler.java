package main;

import java.util.ArrayList;
import java.util.List;

//WarehouseHandler contains the warehouses and all the methods for the warehouses
public class WarehouseHandler
{
	List<Warehouse> warehouseList = new ArrayList<>();

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
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist. Type help for a list of commands.\n");
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
			System.out.print("Sorry, no warehouses exist. Type help for a list of commands.\n"); // \n is return?
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
	 * @param l list of shipments
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
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist. Type help for a list of commands.\n");
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
			System.out.print("Sorry, warehouse " + warehouseID + " doesn't exist. Type help for a list of commands.\n");
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

		if (w == null) // if warehouse doesnt exist create it
		{
			w = addWarehouse(warehouseID, warehouseName);
		}
		if(w.getAvailability()) {

			Shipment s = new Shipment(warehouseID, warehouseName, shipmentID, shipmentMethod, weight, receiptDate); // Switch around the data if needed
			Shipment s2 = w.addShipment(s);
			if (s2 != null)
			{
				return s;
			}
			return null;
		}else{
			System.out.println("Warehouse is not receiving shipments at this time");
			return null;
		}

	}
}
