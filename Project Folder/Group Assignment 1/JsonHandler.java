import java.util.*;
import com.google.gson.*;
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
		
        List<Shipment> shipList = new ArrayList<>();
        
        JsonElement jElem = gson.fromJson(shipJson, JsonElement.class);
        JsonObject shipObject = jElem.getAsJsonObject();
        JsonArray shipArray = shipObject.getAsJsonArray("warehouse_contents");
        
		for(JsonElement i : shipArray) {
			JsonObject shipmentObj = i.getAsJsonObject();
			String warehouseID = shipmentObj.get("warehouse_id").getAsString();
			String shipmentMethod = shipmentObj.get("shipment_method").getAsString();
			String shipmentID = shipmentObj.get("shipment_id").getAsString();
			float weight = shipmentObj.get("weight").getAsFloat();
			long receiptDate = shipmentObj.get("receipt_date").getAsLong();
			
			if(h.getWarehouse(warehouseID) == null)
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
		
		WarehouseContents contents = new WarehouseContents(list);
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); //Output with pretty indentation
		String json = gson.toJson(contents);
		
		File outFile = fo.createFile(directory.getPath(), "outputFile");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		writer.write(json);
	    writer.close();
	    
	}
}
