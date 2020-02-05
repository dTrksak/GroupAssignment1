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

	public List<Shipment> jsonToShipment(String shipJson){ //List<Shipment>
       
        Gson gson = new Gson();
		
		Type shipListType = new TypeToken<ArrayList<Shipment>>(){}.getType();

		List<Shipment> shipList = gson.fromJson(shipJson, shipListType);  
		
		for(int i = 0; i < shipList.size(); i++) {
			String id = shipList.get(i).getWarehouseID();
			h.addWarehouse(id);
		}
		
		shipList.forEach(System.out::println);
		return shipList;
    }
	
	public void shipmentToJson(ArrayList<Shipment> list) throws IOException {
		FileOperations fo = new FileOperations();
		String directory = fo.fileDirectory();
				//System.getProperty("user.dir");
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		
		File outputFile = fo.createFile(directory, "outputFile");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write(json); 
	    writer.close();
	}
}
