import java.util.*;
import com.google.gson.*;
import java.io.*;


public class JsonHandler{
	
	private WarehouseHandler h;
	
	public JsonHandler(WarehouseHandler handler) {
		this.h = handler;
	}

	public List<Shipment> jsonToShipment(JsonObject jo){
		
        List<Shipment> shipList = new ArrayList<>();
        
        JsonArray shipArray = new JsonArray();
        try {
        	JsonObject shipObject = jo;
            shipArray = shipObject.getAsJsonArray("warehouse_contents");
        } catch(JsonSyntaxException e) {
        	System.out.println("Json syntax exception in file, cannot add shipments.");
        	return null; //break out of the function
        }
        
        try {
			for(JsonElement i : shipArray) {
				JsonObject shipmentObj = i.getAsJsonObject();
				String warehouseID = shipmentObj.get("warehouse_id").getAsString();
				String shipmentMethod = shipmentObj.get("shipment_method").getAsString();
				String shipmentID = shipmentObj.get("shipment_id").getAsString();
				//Check that weight and receipt date are correctly formatted
				try {
					Float.parseFloat(shipmentObj.get("weight").getAsString());
					Long.parseLong(shipmentObj.get("receipt_date").getAsString());
				} catch(NumberFormatException e)
				{
					System.out.println("Number format exception, unable to add shipment "+shipmentID+". A float and/or a long are not correctly formatted.");
					continue; //continue the for loop
				}
				float weight = shipmentObj.get("weight").getAsFloat();
				long receiptDate = shipmentObj.get("receipt_date").getAsLong();
				
				//If an id contains a comma, do not add that shipment
				if(warehouseID.contains(",") || shipmentID.contains(",")) {
					System.out.println("Value error, unable to add shipment "+shipmentID+". An ID contained a comma.");
					continue;
				}
				//Everything is good, create the shipment and the warehouse if needed
				h.addWarehouse(warehouseID);
				Shipment s = h.addShipment(warehouseID,shipmentID,shipmentMethod,weight,receiptDate);
				shipList.add(s);
			}
			System.out.println("Shipments successfully imported.");
        } catch(NullPointerException e) {
        	System.out.println("Shipment element mislabled, please check the file for typos.");
        	System.out.println("Could not import the rest of the shipments.");
        }
        return shipList;
    }
	
	public void shipmentToJson(ArrayList<Shipment> list) throws IOException { // takes a list of shipments and writes them to a Json file
		
		FileOperations fo = new FileOperations();
		File directory = fo.fileDirectory();
		
		if(directory == null) {
			System.out.println("Export cancelled.");
			return; //If the user cancelled the export, return
		}
		
		WarehouseContents contents = new WarehouseContents(list);
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); //Output with pretty indentation
		String json = gson.toJson(contents);
		
		File outFile = fo.createFile(directory.getPath(), "outputFile");
		
		if(outFile != null) { // writes warehouse contents into file
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			writer.write(json);
		    writer.close();
		    System.out.println("Shipments successfully exported.");
		}
	}
}
