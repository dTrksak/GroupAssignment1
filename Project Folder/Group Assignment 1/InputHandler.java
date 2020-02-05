import java.io.File;
import java.util.Scanner;

import javax.swing.*;  // Import the Scanner class

public class InputHandler {
	WarehouseHandler handle;
	JsonHandler jhandle;
	public InputHandler(WarehouseHandler h , JsonHandler j){
		handle = h;
		jhandle = j;
	}
	public void getInput(String S) {
	    Scanner scan = new Scanner(System.in);  // Create a Scanner object

		System.out.println("input the command you would like the program to run");
		String input = scan.toString();
		if(input.contains("create warehouse")) {
			System.out.println("Enter your Warehouse ID");
			String warehouseID = scan.toString();
			handle.addWarehouse(warehouseID); 
		}
		else if(input.contains("import shipments"))
		{
			FileOperations newship = new FileOperations();
			File f = newship.fileInput();
			String Str = newship.fileToString(f);
			jhandle.jsonToShipment(Str);
	    }
		else if(input.contains("enable freight receipt"))
		{
			System.out.println("enter the Warehouse ID you would like to check");
			String w = scan.toString();
			if(handle.getWarehouse(w).getAvailability() == true)
				System.out.println("Warehouse is already enabled");
			else 
				handle.getWarehouse(w).enableFreightReceipt();
			
		}
		else if(input.contains("end freight receipt")) {
			System.out.println("enter the Warehouse ID you would like to check");
			String w = scan.toString();
			if(handle.getWarehouse(w).getAvailability() == false)
				System.out.println("Warehouse is already enabled");
			else 
				handle.getWarehouse(w).disableFrieghtReceipt();
		}
		else if(input.contains("export all shipments")) {
			handle.getAllWarehouseShipments();
		}
		else if(input.contains("add incoming shipment")) {
			System.out.println("Enter your warehouseID, shipmentID, shipmentMethod, weight, receiptDate Separated by Commas");
			String w = scan.toString();
			String split[] = w.split(",", 5);
			handle.addShipment(split[0], split[1], split[2], Float.parseFloat(split[3]), Long. parseLong(split[4]));
		}
		else if(input.contains("add incoming shipment")) {
			System.out.println("Enter your warehouseID");
			String w = scan.toString();
			handle.getWarehouseShipments(w);
		}
		}
	

}
