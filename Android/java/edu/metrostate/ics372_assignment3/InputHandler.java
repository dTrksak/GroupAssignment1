package edu.metrostate.ics372_assignment3;

import android.net.Uri;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import org.json.*;

//InputHandler handles various input choices for the user
public class InputHandler
{
	WarehouseHandler handle = WarehouseHandler.getInstance();
	XmlHandler xhandle = new XmlHandler();
	JsonHandler jhandle = new JsonHandler();
	RecoverData reData = new RecoverData();
	

	/**
	 * Process to create a warehouse
	 * 
	 * @param warehouseID
	 */
	public void createWarehouseProcess(String warehouseID, String warehouseName)
	{
		Warehouse w = handle.addWarehouse(warehouseID, warehouseName);
		if (w == null)
		{
			System.out.println("Warehouse " + warehouseID + " already exists.");
		}
		else
		{
			System.out.println("Created Warehouse " + warehouseID + ".");
		}
	}

	/**
	 * Process to create a shipment
	 * 
	 * @param split
	 * @throws IOException
	 */
	public Shipment createShipmentProcess(String[] split) throws IOException // adds a shipments given by user to data
	{
		Shipment s;
		try {
			s = handle.addShipment(split[0], split[1], split[2], split[3], Float.parseFloat(split[4]), Long.parseLong(split[5]));
		} catch(Exception e) {
			return null;
		}
		
		if (s != null)
		{
			RecoverData.saveData(); // Saves changes
			System.out.println("Shipment successfully added to warehouse " + split[0] + ".");
			return s;
		}
		return null;
	}
	
	/**
	 * Process to remove a shipment for a warehouse
	 * Places removed shipment into a In Transit category
	 * @throws IOException
	 */
	public Shipment removeShipmentProcess(String warehouseID, String shipID) throws IOException {
		Shipment s;
		try {
			Warehouse w = handle.getWarehouse(warehouseID);
			s = w.removeShipment(shipID);
		} catch (Exception e) {
			return null;
		}

		if (s != null) {
			System.out.println("Shipment successfully removed");
			RecoverData.saveData();
			return s;
		}
		return null;
	}

	/**
	 * Process to import a Json file with an array of Json objects
	 * @throws IOException
	 */
	public Boolean importShipmentProcess(File f) throws IOException // imports shipments from json file given by user
	{
		
		//File f = FileOperations.fileInput();
		JsonObject jo = new JsonObject();
		jo = FileOperations.convertFileToJSON(f);
		if (jo != null)
		{
			List<Shipment> list = jhandle.jsonToShipment(jo);
			if(list != null)
			{
				handle.addShipmentList(list); // add all shipments to data
				RecoverData.saveData(); // saves all data
				return true;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/*
	 * Imports an xml file
	 */
	/*public Boolean importXmlProcess() throws ParserConfigurationException, SAXException, IOException
	{
		File f = FileOperations.fileInput();
		if (f != null)
		{
			List<Shipment> xmlList = xhandle.parseXml(f.getAbsolutePath());
			handle.addShipmentList(xmlList); // add all shipments to data
			RecoverData.saveData(); // saves all data
			return true;
		} else {
			return null;
		}
	}*/

	/**
	 * Exports warehouse shipment information for a single warehouse ID
	 * 
	 * @param w
	 * @throws IOException
	 */
	public void exportWarehouse(String w) throws IOException
	{
		ArrayList<Shipment> l = (ArrayList<Shipment>) handle.getWarehouse(w).getShipmentList();
		if (l != null)
		{
			jhandle.shipmentToJson(l, "outputFile");
		}
	}

	/**
	 * Exports all warehouses and their data to Json file
	 * 
	 * @throws IOException
	 */
	public Boolean exportAllWarehouse() throws IOException
	{
		ArrayList<Shipment> l = handle.getAllWarehouseShipments();
		if (l != null)
		{
			jhandle.shipmentToJson(l, "outputFile");
			return true;
		}
		return null;
	}

	/**
	 * Enables a warehouse to accept shipments
	 * 
	 * @param w
	 */
	public void enableFreight(String w) //allows warehouse to receive shipments
	{
		if (handle.getWarehouse(w) != null)
		{
			if (handle.getWarehouseReceipt(w) != true)
			{
				handle.getWarehouse(w).enableFreightReceipt();
				System.out.println("The freight receipt of warehouse " + handle.getWarehouse(w).getWarehouseID() + " is now enabled.");
			}
			else
			{
				System.out.println("The freight receipt of warehouse " + handle.getWarehouse(w).getWarehouseID() + " is already enabled.");
			}
		}
		else
		{
			// If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, warehouse " + w + " doesn't exist.\n");
		}
	}

	/**
	 * Stops a warehouse from accepting shipments
	 * 
	 * @param w
	 */
	public void endFreight(String w) // closes warehouse
	{
		if (handle.getWarehouse(w) != null)
		{
			if (handle.getWarehouseReceipt(w) == true)
			{
				handle.getWarehouse(w).disableFreightReceipt();
				System.out.println("The freight receipt of warehouse " + handle.getWarehouse(w).getWarehouseID() + " is now disabled.");
			}
			else
			{
				System.out.println("The freight receipt of warehouse " + handle.getWarehouse(w).getWarehouseID() + " is already disabled.");
			}
		}
		else
		{
			// If the warehouse doesn't exist, tell the user
			System.out.print("Sorry, warehouse " + w + " doesn't exist.\n");
		}
	}

	/**
	 * Shows the data entered in the current session
	 */
	public void showData() //prints all data onto console
	{
		List<Warehouse> list = handle.getAllWarehouses();
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				System.out.println("Warehouse " + list.get(i).getWarehouseID() + ", " + list.get(i).getWarehouseName() + " - " + list.get(i).getShipmentList().toString() + "\n");
			}
		}
		else
		{
			System.out.println("There are no warehouses to display.");
		}
	}

	/**
	 * Shows the help menu
	 */
	public void showHelp()
	{
		System.out.print("Commands:\n" + "add warehouse (0) - creates a warehouse\n" + "add incoming shipment (1) - add a single shipment to a warehouse\n" + "import shipments (2) - input a json file of shipments, automatically adds warehouses.\n" + "export warehouse (3) - exports one warehouse's shipments to a json file\n" + "export all shipments (4) - exports all warehouse shipments to a json file\n" + "enable freight receipt (5) - sets a warehouse receipt to true\n" + "end freight receipt (6) - sets a warehouse receipt to false\n" + "show data (7) - displays all warehouse IDs and their shipment IDs in the console.\n" + "clear (8) - clears the visible console\n" + "help (9) - display this list of commands\n" + "exit - exits the program\n");
	}
}
