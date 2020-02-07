import java.util.*;
import com.google.gson.Gson;

import java.io.*;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class JsonHandler{
	
	private WarehouseHandler h;
	
	public JsonHandler(WarehouseHandler handler) {
		this.h = handler;
	}

	public List<Shipment> jsonToShipment(String shipJson){
       
        Gson gson = new Gson();
		
		Type shipListType = new TypeToken<ArrayList<Shipment>>(){}.getType();
		
		List<Shipment> shipList = new ArrayList<>();
		
		JsonObject shipObject = gson.fromJson(shipJson, shipListType);
        JsonArray jsonArray = shipObject.getAsJsonArray("warehouse_contents");
        
		for(JsonElement i : jsonArray) {
			JsonObject shipmentObj = i.getAsJsonObject();
			String warehouseID = shipmentObj.get("warehouse_id").getAsString();
			String shipmentMethod = shipmentObj.get("shipment_method").getAsString();
			String shipmentID = shipmentObj.get("shipment_id").getAsString();
			float weight = shipmentObj.get("weight").getAsFloat();
			long receiptDate = shipmentObj.get("receipt_date").getAsLong();
			
			if(h.getWarehouse(warehouseID).equals(null))
			{
				h.addWarehouse(warehouseID);
			}
			Shipment s = h.addShipment(warehouseID,shipmentMethod,shipmentID,weight,receiptDate);
			shipList.add(s);
		}
		return shipList;
    }
	
	public void shipmentToJson(ArrayList<Shipment> list) throws IOException {
		FileOperations fo = new FileOperations();
		File directory = fo.fileDirectory();
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		
		File outFile = fo.createFile(directory.getPath(), "outputFile");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		writer.write(json); 
	    writer.close();
	}
}
