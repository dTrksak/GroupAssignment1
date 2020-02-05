import java.util.ArrayList;
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
			return w.getShipmentList(); //Replace getShipmentList() if needed
		} else {
			//If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, warehouse "+warehouseID+" doesn't exist. Type help for a list of commands.\n");
			return null;
		}	
	}
	
	/**
	 * Gets a list of shipments from all warehouses
	 * @return an ArrayList of shipments from all warehouses, null if no warehouses exist
	 */
	public ArrayList<Shipment> getAllWarehouseShipments()
	{	
		//Check that warehouseList is not empty
		if(warehouseList.isEmpty())
		{
			//If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, no warehouses exist. Type help for a list of commands.\n"); //\n is return?
			return null;
		} else {
			ArrayList<Shipment> outputList = new ArrayList<>();
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
			System.out.print("Warehouse "+warehouseID+" already exists.\n");
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
			System.out.print("Sorry, warehouse "+warehouseID+" doesn't exist. Type help for a list of commands.\n");
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
				System.out.print("Warehouse "+warehouseID+"'s receipt is already set to "+input+".\n");
			} else {
				if(input == false)
				{
					w.disableFreightReceipt();
				} else {
					w.enableFreightReceipt();
				}
			}
		} else {
			System.out.print("Sorry, warehouse "+warehouseID+" doesn't exist. Type help for a list of commands.\n");
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
			System.out.print("Sorry, warehouse "+warehouseID+" doesn't exist. Type help for a list of commands.\n");
		}
	}	
}
