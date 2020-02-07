import java.io.*;
import java.util.*;

public class InputHandler {
	WarehouseHandler handle;
	JsonHandler jhandle;
	public InputHandler(WarehouseHandler h , JsonHandler j){
		handle = h;
		jhandle = j;
	}
	
	public void getInput() throws IOException {
	    Scanner scan = new Scanner(System.in);  // Create a Scanner object
	    boolean exit = false;
	    
	    System.out.print("Commands:\n"
	    		+ "create warehouse - creates a warehouse\n"
	    		+ "import shipments - input a json file of shipments\n"
	    		+ "enable freight receipt - sets a warehouse receipt to true\n"
	    		+ "end freight receipt - sets a warehouse receipt to false\n"
	    		+ "export all shipments - exports all warehouse contents to a json file\n"
	    		+ "add incoming shipment - add a single shipment to a warehouse\n"
	    		+ "help - display this list of commands\n"
	    		+ "exit - exits the program\n");
	    
	    while(!exit)
	    {
			//System.out.print("input the command you would like the program to run\n");
			String input = scan.nextLine();
			
			if(input.contains("create warehouse")) {
				System.out.print("Enter your Warehouse ID\n");
				String warehouseID = scan.nextLine();
				handle.addWarehouse(warehouseID); 
			}
			else if(input.contains("import shipments"))
			{
				FileOperations newship = new FileOperations();
				File f = newship.fileInput();
				String str = null;
				try {
					str = newship.fileToString(f);
					jhandle.jsonToShipment(str);
				} catch (FileNotFoundException e) {
					System.out.print("The file cannot be found.\n");
				}
		    }
			else if(input.contains("enable freight receipt"))
			{
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if(handle.getWarehouse(w) != null)
				{
					handle.getWarehouse(w).enableFreightReceipt();
				} else {
					//If the warehouse doesn't exist, tell the user
					System.out.print("Sorry, that warehouse doesn't exist. Type help for a list of commands.\n");
				}
			}
			else if(input.contains("end freight receipt")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if(handle.getWarehouse(w) != null)
				{
					handle.getWarehouse(w).disableFrieghtReceipt();
				} else {
					//If the warehouse doesn't exist, tell the user
					System.out.print("Sorry, that warehouse doesn't exist. Type help for a list of commands.\n");
				}	
			}
			else if(input.contains("export all shipments")) {
				ArrayList<Shipment> l = handle.getAllWarehouseShipments();
				jhandle.shipmentToJson(l);
			}
			else if(input.contains("add incoming shipment")) {
				System.out.print("Enter your warehouseID, shipmentID, shipmentMethod, weight, receiptDate Separated by Commas\n");
				String w = scan.nextLine();
				String split[] = w.split(",", 5);
				handle.addShipment(split[0], split[1], split[2], Float.parseFloat(split[3]), Long. parseLong(split[4]));
			}
			else if(input.contains("add incoming shipment")) {
				System.out.print("Enter your warehouseID\n");
				String w = scan.nextLine();
				handle.getWarehouseShipments(w);
			}
			else if(input.contains("help"))
			{
				System.out.print("Commands:\n"
			    		+ "create warehouse - creates a warehouse\n"
			    		+ "import shipments - input a json file of shipments\n"
			    		+ "enable freight receipt - sets a warehouse receipt to true\n"
			    		+ "end freight receipt - sets a warehouse receipt to false\n"
			    		+ "export all shipments - exports all warehouse contents to a json file\n"
			    		+ "add incoming shipment - add a single shipment to a warehouse\n"
			    		+ "help - display this list of commands\n"
			    		+ "exit - exits the program\n");
			}
			else if(input.contains("exit")) {
				exit = true; //Exit the while loop
				System.out.print("Exiting the program...\n");
			} else {
				System.out.print("Sorry, that command doesn't exist, type help for a list of commands.\n");
			}
	    }
	    scan.close(); //Close the scanner
	    System.out.print("Program end.");
	}
}
