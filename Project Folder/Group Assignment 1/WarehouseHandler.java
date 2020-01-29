import java.util.LinkedList;

public class WarehouseHandler
{
	LinkedList<Warehouse> warehouseList = new LinkedList<>();
	
	/**
	 * Gets a list of shipments from a single warehouse
	 * @param warehouseID
	 * @return a linked list of shipments from a warehouse, null if the warehouse doesn't exist
	 */
	public LinkedList<Shipment> getWarehouseShipments(String warehouseID)
	{
		Warehouse w = getWarehouse(warehouseID); //Get the warehouse in warehouseList using warehouseID
		
		//Check that the warehouse exists
		if(w != null)
		{
			return w.getShipmentList(); //Replace getShipmentList() if needed
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
	public LinkedList<Shipment> getAllWarehouseShipments()
	{	
		//Check that warehouseList is not empty
		if(warehouseList.isEmpty())
		{
			//If the warehouse doesn't exist, tell the user
			System.out.println("Sorry, no warehouses exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n"); //\n is return?
			return null;
		} else {
			LinkedList<Shipment> outputList = new LinkedList<>();
			for(int i = 0; i < warehouseList.size(); i++)
			{
				Warehouse w = warehouseList.get(i);
				outputList.addAll(w.getShipmentList()); //Replace getShipmentList() if needed
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
			if(warehouseList.get(i).getWarehouseID().equals(warehouseID)) //Replace getWarehouseID if needed
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
			Warehouse n = new Warehouse(warehouseID); //Replace warehouseID if needed
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
			return w.getReceipt(); //Replace getReceipt() if needed
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
			w.setReceipt(input); //Replace setReceipt() if needed
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
			w.addShipment(warehouseID,shipmentID,shipmentMethod,weight,receiptDate); //Replace addShipment() and the variables if needed
		} else {
			System.out.println("Sorry, warehouse "+warehouseID+" doesn't exist. Create a warehouse by typing 'create warehouse ' then the ID of the warehouse, or import a json file by typing 'import shipments'.\n");
		}
	}	
}
