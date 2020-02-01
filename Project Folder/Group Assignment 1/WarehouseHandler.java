import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WarehouseHandler
{
	List<Warehouse> warehouseList = new ArrayList<>();
	
	/**
	 * Gets a list of shipments from a single warehouse
	 * @param warehouseID
	 * @return a linked list of shipments from a warehouse, null if the warehouse doesn't exist
	 */
	public List<Shipment> getWarehouseShipments(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID); //Get the warehouse in warehouseList using warehouseID
		
		//Check that the warehouse exists
		if(w != null)
		{
			return w.getShipmentList();
		} else {
			//If the warehouse doesn't exist, tell the user
			System.out.println("Sorry, warehouse "+warehouseID+" doesn't exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n"); //\n is return?
			return null;
		}	
	}
	
	/**
	 * Gets a list of shipments from all warehouses
	 * @return a linked list of shipments from all warehouses, null if no warehouses exist
	 */
	public List<Shipment> getAllWarehouseShipments()
	{	
		//Check that warehouseList is not empty
		if(warehouseList.isEmpty())
		{
			//If the warehouse doesn't exist, tell the user
			System.out.println("Sorry, no warehouses exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n"); //\n is return?
			return null;
		} else {
			List<Shipment> outputList = new ArrayList<>();
			for(int i = 0; i < warehouseList.size(); i++)
			{
				Warehouse w = warehouseList.get(i);
				outputList.addAll(w.getShipmentList());
			}
			return outputList;
		}	
	}
	
	/**
	 * Finds a warehouse in warehouseList given the warehouseID
	 * @param warehouseID
	 * @return a Warehouse object if found, null if not found
	 */
	public Warehouse getWarehouse(String warehouseID)
	{
		//Iterate through warehouseList checking warehouses until the warehouseID is found
		for(int i = 0; i < warehouseList.size(); i++)
		{
			if(warehouseList.get(i).getWarehouseID().equals(warehouseID))
			{
				return warehouseList.get(i); //Return the warehouse
			}
		}
		return null;
	}
	
	/**
	 * Creates a warehouse given a warehouse ID
	 * @param warehouseID
	 */
	public void addWarehouse(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID);
		
		//Check that the warehouse doesn't exist before creating a new one
		if(w == null)
		{
			Warehouse n = new Warehouse(warehouseID);
			warehouseList.add(n);
		} else {
			System.out.println("Warehouse "+warehouseID+" already exists.");
		}
	}
	
	/**
	 * Gets a specified warehouse's receipt
	 * @param warehouseID
	 * @return the warehouse receipt, null if the warehouse doesn't exist
	 */
	public Boolean getWarehouseReceipt(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID);
		
		//Check that the warehouse exists
		if(w != null)
		{
			return w.getAvailability();
		} else {
			System.out.println("Sorry, warehouse "+warehouseID+" doesn't exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or by importing shipments by typing 'import shipments'.\n");
			return null;
		}
	}
	
	/**
	 * Sets a boolean value in a specified warehouse
	 * @param warehouseID
	 * @param input what the boolean should be set to
	 */
	public void setWarehouseReceipt(String warehouseID, boolean input)
	{
		Warehouse w = getWarehouse(warehouseID);
		
		//Check that the warehouse exists
		if(w != null)
		{
			//Check that the user input will change the receipt
			if(input == w.getAvailability())
			{
				System.out.println("Warehouse "+warehouseID+"'s receipt is already set to "+input+".");
			} else {
				if(input == false)
				{
					w.disableFreightReceipt();
				} else {
					w.enableFreightReceipt();
				}
			}
		} else {
			System.out.println("Sorry, warehouse "+warehouseID+" doesn't exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n");
		}
	}
	
	/**
	 * Adds a shipment to the specified warehouse
	 * @param warehouseID
	 * @param shipmentID
	 * @param shipmentMethod
	 * @param weight
	 * @param receiptDate
	 */
	public void addShipment(String warehouseID, String shipmentID, String shipmentMethod, float weight, long receiptDate)
	{
		Warehouse w = getWarehouse(warehouseID);
		
		//Check that the warehouse exists
		if(w != null)
		{
			Shipment s = new Shipment(warehouseID,shipmentID,shipmentMethod,weight,receiptDate); //Switch around the data if needed
			w.addShipment(s);
		} else {
			System.out.println("Sorry, warehouse "+warehouseID+" doesn't exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n");
		}
	}	
}
