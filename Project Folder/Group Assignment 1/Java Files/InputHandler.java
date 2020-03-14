package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.JsonObject;

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
	 * Process to import a Json file with an array of Json objects
	 * @throws IOException
	 */
	public Boolean importShipmentProcess() throws IOException // imports shipments from json file given by user
	{
		
		File f = FileOperations.fileInput();
		JsonObject jo = null;
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

	public Boolean importXmlProcess() throws ParserConfigurationException, SAXException, IOException
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
	}

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
				System.out.println("Warehouse " + list.get(i).getWarehouseID() + ", " + list.get(i).getWarehouseName() + " - " + list.get(i).getShipmentList().toString());
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

	/**
	 * Gets input from the user on how they wish to proceed.
	 * 
	 * @throws IOException
	 */
	public void getInput() throws IOException
	{
		Scanner scan = new Scanner(System.in); // Create a Scanner object
		boolean exit = false;
		// help menu
		showHelp();

		while (!exit)
		{
			// System.out.print("input the command you would like the program to run\n");
			String input = scan.nextLine();

			// add warehouse process
			if (input.contains("add warehouse") || input.contains("0"))
			{
				System.out.print("Enter your Warehouse ID and Warehouse Name\n");
				String warehouse = scan.nextLine();
				String split[] = warehouse.split(",", 2);
				String warehouseID = split[0];
				String warehouseName = split[1];

				createWarehouseProcess(warehouseID, warehouseName);

			}
			// add shipment process
			else if (input.contains("add incoming shipment") || input.contains("1"))
			{
				System.out.print("Enter the warehouse ID, warehouseName, shipment ID, shipment method, weight, receipt date Separated by Commas\n");
				String w = scan.nextLine();
				String split[] = w.split(",", 6);
				if (split.length != 6)
				{
					System.out.println("Invalid input");
					continue;
				}
				split[5] = split[5].replaceAll(" ", "");

				try
				{
					Float.parseFloat(split[4]);
				}
				catch (Exception e)
				{
					System.out.println("Invalid input");
					continue; // Don't create a shipment, just go to the top of the while loop
				}
				try
				{
					Long.parseLong(split[5]);
				}
				catch (Exception e)
				{
					System.out.println("Invalid input");
					continue; // Don't create a shipment, just go to the top of the while loop
				}
				createShipmentProcess(split);

			}
			// importing a shipment from Json file
			else if (input.contains("import shipments") || input.contains("2"))
			{
				System.out.println("Please use JFileChooser to select a json file.");
				importShipmentProcess();
			}
			// exporting a specific warehouse to Json file
			else if (input.contains("export warehouse") || input.contains("3"))
			{
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				// Check that warehouse w exists
				if (handle.getWarehouse(w) == null)
				{
					System.out.println("Warehouse " + w + " doesn't exist.");
					continue;
				}
				System.out.println("Please use JFileChooser to select a folder.");
				exportWarehouse(w);
			}
			// exporting all warehouses to Json file
			else if (input.contains("export all shipments") || input.contains("4"))
			{
				System.out.println("Please use JFileChooser to select a folder.");
				exportAllWarehouse();
			}
			// enabling shipments to be received
			else if (input.contains("enable freight receipt") || input.contains("5"))
			{
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if (handle.getWarehouse(w) != null)
				{
					enableFreight(w);
				}
				else
				{
					System.out.println("Sorry, that warehouse doesn't exist");
				}
			}
			// stopping shipments from being received
			else if (input.contains("end freight receipt") || input.contains("6"))
			{
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();

				if (handle.getWarehouse(w) != null)
				{
					endFreight(w);
				}
				else
				{
					System.out.println("Sorry that warehouse doesn't exist");
				}
			}
			// Show data from current session
			else if (input.contains("show data") || input.contains("7"))
			{
				showData();
			}
			// clear screen
			else if (input.contains("clear") || input.contains("8"))
			{
				for (int i = 0; i < 50; i++)
				{
					System.out.print("\n");
				}
			}
			// show help
			else if (input.contains("help") || input.contains("9"))
			{
				showHelp();
			}
			// exit program
			else if (input.contains("exit"))
			{
				exit = true; // Exit the while loop
				System.out.print("Exiting the program...\n");
			}
			else
			{
				System.out.print("Sorry, that command doesn't exist, type help for a list of commands.\n");
			}
		}

		scan.close(); // Close the scanner
		reData.saveData();
		System.out.print("Program end.");
	}

	public String getWarehouseName(String warehouseID) 
	{	
		String result = "";
		List<Warehouse> listAll = handle.getAllWarehouses();
		if (listAll != null)
		{
			for (int i = 0; i < listAll.size(); i++)
			{
				if(listAll.get(i).getWarehouseID().equalsIgnoreCase(warehouseID))
				{
					result = listAll.get(i).getWarehouseName();
					break;
				}else
				{
					continue;
				}
			}	
		}
		return null;
	}
}
