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
	    		+ "create warehouse (0) - creates a warehouse\n"
	    		+ "add incoming shipment (1) - add a single shipment to a warehouse\n"
	    		+ "import shipments (2) - input a json file of shipments, automatically adds warehouses.\n"
	    		+ "export all shipments (3) - exports all warehouse contents to a json file\n"
	    		+ "enable freight receipt (4) - sets a warehouse receipt to true\n"
	    		+ "end freight receipt (5) - sets a warehouse receipt to false\n"
	    		+ "show data (6) - displays all warehouse IDs and their shipment IDs in the console.\n"
	    		+ "clear (7) - clears the visible console\n"
	    		+ "help (8) - display this list of commands\n"
	    		+ "exit (9) - exits the program\n");
	    
	    while(!exit) {
			//System.out.print("input the command you would like the program to run\n");
			String input = scan.nextLine();
			
			if(input.contains("create warehouse") || input.contains("0")) {
				System.out.print("Enter your Warehouse ID\n");
				String warehouseID = scan.nextLine();
				if(warehouseID.contains(",")) {
					System.out.println("The warehouse ID cannot contain a comma.");
					continue; //return to top of while loop
				}
				Warehouse w = handle.addWarehouse(warehouseID);
				if(w == null) {
					System.out.println("Warehouse "+warehouseID+" already exists.");
				} else {
					System.out.println("Created Warehouse "+warehouseID+".");
				}
			}
			else if(input.contains("add incoming shipment") || input.contains("1")) {
				System.out.print("Enter the warehouse ID, shipment ID, shipment method, weight, receipt date Separated by Commas\n");
				String w = scan.nextLine();
				String split[] = w.split(",", 5);
				try{
			        Float.parseFloat(split[3]);
			    }catch(Exception e){
			        System.out.println("Invalid input");
			        continue; //Don't create a shipment, just go to the top of the while loop
			    }
				try{
			        Long.parseLong(split[4]);
			    }catch(Exception e){
			        System.out.println("Invalid input");
			        continue; //Don't create a shipment, just go to the top of the while loop
			    }
				Shipment s = handle.addShipment(split[0], split[1], split[2], Float.parseFloat(split[3]), Long.parseLong(split[4]));
				if(s != null) {
					System.out.println("Shipment successfully added to warehouse "+split[0]+".");
				}
			}
			else if(input.contains("import shipments") || input.contains("2")) {
				System.out.println("Please use JFileChooser to select a json file.");
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
			else if(input.contains("export all shipments") || input.contains("3")) {
				System.out.println("Please use JFileChooser to select a folder.");
				ArrayList<Shipment> l = handle.getAllWarehouseShipments();
				if(l != null) {
					jhandle.shipmentToJson(l);
				}
			}
			else if(input.contains("enable freight receipt") || input.contains("4")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if(handle.getWarehouse(w) != null) {
					if(handle.getWarehouseReceipt(w) != true) {
						handle.getWarehouse(w).enableFreightReceipt();
						System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is now enabled.");
					} else {
						System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is already enabled.");
					}
				} else {
					//If the warehouse doesn't exist, tell the user
					System.out.print("Sorry, warehouse "+handle.getWarehouse(w).getWarehouseID()+" doesn't exist. Type help for a list of commands.\n");
				}
			}
			else if(input.contains("end freight receipt") || input.contains("5")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if(handle.getWarehouse(w) != null) {
					if(handle.getWarehouseReceipt(w) == true) {
						handle.getWarehouse(w).disableFreightReceipt();
						System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is now disabled.");
					} else {
						System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is already disabled.");
					}
				} else {
					//If the warehouse doesn't exist, tell the user
					System.out.print("Sorry, warehouse "+handle.getWarehouse(w).getWarehouseID()+" doesn't exist. Type help for a list of commands.\n");
				}	
			}
			else if(input.contains("show data") || input.contains("6")) {
				List<Warehouse> list = handle.getAllWarehouses();
				if(list != null)
				{
					for(int i = 0;i < list.size(); i++)
					{
						System.out.println("Warehouse "+list.get(i).getWarehouseID()+" - "+list.get(i).getShipmentList().toString());
					}
				} else {
					System.out.println("There are no warehouses to display.");
				}
			}
			else if (input.contains("clear") || input.contains("7")) {
				for(int i=0;i<50;i++) {System.out.print("\n");}
			}
			else if(input.contains("help") || input.contains("8")) {
				System.out.print("Commands:\n"
			    		+ "create warehouse (0) - creates a warehouse\n"
			    		+ "add incoming shipment (1) - add a single shipment to a warehouse\n"
			    		+ "import shipments (2) - input a json file of shipments, automatically adds warehouses.\n"
			    		+ "export all shipments (3) - exports all warehouse contents to a json file\n"
			    		+ "enable freight receipt (4) - sets a warehouse receipt to true\n"
			    		+ "end freight receipt (5) - sets a warehouse receipt to false\n"
			    		+ "show data (6) - displays all warehouse IDs and their shipment IDs in the console.\n"
			    		+ "clear (7) - clears the visible console\n"
			    		+ "help (8) - display this list of commands\n"
			    		+ "exit (9) - exits the program\n");
			}
			else if(input.contains("exit") || input.contains("9")) {
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
