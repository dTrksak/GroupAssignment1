import java.io.*;
import java.util.*;
import com.google.gson.*;

//InputHandler handles various input choices for the user
public class InputHandler {
	WarehouseHandler handle = new WarehouseHandler();
	JsonHandler jhandle = new JsonHandler(handle);

/**
 * Process to create a warehouse
 * @param warehouseID
 */
	private void createWarehouseProcess(String warehouseID){

		Warehouse w = handle.addWarehouse(warehouseID);
		if(w == null) {
			System.out.println("Warehouse "+warehouseID+" already exists.");
		} else {
			System.out.println("Created Warehouse "+warehouseID+".");
		}
	}
	/**
	 * Process to create a shipment
	 * @param split
	 */
	private void createShipmentProcess(String[] split){
		
		Shipment s = handle.addShipment(split[0], split[1], split[2], Float.parseFloat(split[3]), Long.parseLong(split[4]));
		if(s != null) {
			System.out.println("Shipment successfully added to warehouse "+split[0]+".");
		}
		
	}
	/**
	 * Process to import a Json file with an array of Json objects
	 */
	private void importShipmentProcess() {
		
		FileOperations newship = new FileOperations();
		File f = newship.fileInput();
		JsonObject jo = null;
		jo = newship.convertFileToJSON(f);
		if(jo != null) {
			jhandle.jsonToShipment(jo);
		}
	}
	/**
	 * Exports warehouse shipment information for a single warehouse ID
	 * @param w
	 * @throws IOException
	 */
	private void exportWarehouse(String w) throws IOException{
		
		ArrayList<Shipment> l = (ArrayList<Shipment>) handle.getWarehouse(w).getShipmentList();
		if(l != null) {
			jhandle.shipmentToJson(l);
		}
	}
	/**
	 * Exports all warehouses and their data to Json file
	 * @throws IOException
	 */
	private void exportAllWarehouse()throws IOException{
		
		ArrayList<Shipment> l = handle.getAllWarehouseShipments();
		if(l != null) {
			jhandle.shipmentToJson(l);
		}
	}
	/**
	 * Enables a warehouse to accept shipments
	 * @param w
	 */
	private void enableFreight(String w){
		
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
	/**
	 * Stops a warehouse from accepting shipments
	 * @param w
	 */
	private void endFreight(String w){
		
			if(handle.getWarehouseReceipt(w) == true) {
				handle.getWarehouse(w).disableFreightReceipt();
				System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is now disabled.");
			} else {
				System.out.println("The freight receipt of warehouse "+handle.getWarehouse(w).getWarehouseID()+" is already disabled.");
			}
					
	}
	/**
	 * Shows the data entered in the current session
	 */
	private void showData(){
		
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
	/**
	 * Shows the help menu
	 */
	private void showHelp(){
		
		System.out.print("Commands:\n"
	    		+ "add warehouse (0) - creates a warehouse\n"
	    		+ "add incoming shipment (1) - add a single shipment to a warehouse\n"
	    		+ "import shipments (2) - input a json file of shipments, automatically adds warehouses.\n"
	    		+ "export warehouse (3) - exports one warehouse's shipments to a json file\n"
	    		+ "export all shipments (4) - exports all warehouse shipments to a json file\n"
	    		+ "enable freight receipt (5) - sets a warehouse receipt to true\n"
	    		+ "end freight receipt (6) - sets a warehouse receipt to false\n"
	    		+ "show data (7) - displays all warehouse IDs and their shipment IDs in the console.\n"
	    		+ "clear (8) - clears the visible console\n"
	    		+ "help (9) - display this list of commands\n"
	    		+ "exit - exits the program\n");
	}
	
	/**
	 * Gets input from the user on how they wish to proceed.
	 * @throws IOException
	 */
	public void getInput() throws IOException {
		
	    Scanner scan = new Scanner(System.in);  // Create a Scanner object
	    boolean exit = false;
	    // help menu   
	    showHelp();
	    while(!exit) {
			//System.out.print("input the command you would like the program to run\n");
			String input = scan.nextLine();
			
			//add warehouse process
			if(input.contains("add warehouse") || input.contains("0")) {
				System.out.print("Enter your Warehouse ID\n");
				String warehouseID = scan.nextLine();
				if(warehouseID.contains(",")) {
					System.out.println("The warehouse ID cannot contain a comma.");
					continue; //return to top of while loop
				}
				createWarehouseProcess(warehouseID);
				
			}
			//add shipment process
			else if(input.contains("add incoming shipment") || input.contains("1")) {
				System.out.print("Enter the warehouse ID, shipment ID, shipment method, weight, receipt date Separated by Commas\n");
				String w = scan.nextLine();
				String split[] = w.split(",", 5);
				if(split.length != 5) {
					System.out.println("Invalid input");
					continue;
				}
				split[4] = split[4].replaceAll(" ","");
				
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
				createShipmentProcess(split);
				
				
			}
			//importing a shipment from Json file
			else if(input.contains("import shipments") || input.contains("2")) {
				System.out.println("Please use JFileChooser to select a json file.");
				importShipmentProcess();
		    }
			//exporting a specific warehouse to Json file
			else if(input.contains("export warehouse") || input.contains("3")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				//Check that warehouse w exists
				if(handle.getWarehouse(w) == null) {
					System.out.println("Warehouse "+w+" doesn't exist.");
					continue;
				}
				System.out.println("Please use JFileChooser to select a folder.");
				exportWarehouse(w);
			}
			//exporting all warehouses to Json file
			else if(input.contains("export all shipments") || input.contains("4")) {
				System.out.println("Please use JFileChooser to select a folder.");
				exportAllWarehouse();
			}
			//enabling shipments to be received
			else if(input.contains("enable freight receipt") || input.contains("5")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
				if(handle.getWarehouse(w) != null) {
					enableFreight(w);
				}
				else{
					System.out.println("Sorry, that warehouse doesn't exist");
				}
			}
			//stopping shipments from being received
			else if(input.contains("end freight receipt") || input.contains("6")) {
				System.out.print("enter the Warehouse ID\n");
				String w = scan.nextLine();
	
		        	if(handle.getWarehouse(w) != null){
		        	endFreight(w);
				}else{
					System.out.println("Sorry that warehouse doesn't exist");
				}
			}
			//Show data from current session
			else if(input.contains("show data") || input.contains("7")) {
				showData();
			}
			//clear screen
			else if (input.contains("clear") || input.contains("8")) {
				for(int i=0;i<50;i++) {System.out.print("\n");}
			}
			//show help
			else if(input.contains("help") || input.contains("9")) {
				showHelp();
			}
			//exit program
			else if(input.contains("exit")) {
				exit = true; //Exit the while loop
				System.out.print("Exiting the program...\n");
			} 
			else {
				System.out.print("Sorry, that command doesn't exist, type help for a list of commands.\n");
			}
	    }
	    scan.close(); //Close the scanner
	    System.out.print("Program end.");
	}
	
}

