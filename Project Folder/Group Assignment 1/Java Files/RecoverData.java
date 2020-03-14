package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class RecoverData
{
	static String filePath = "RecoveredData.json";

	public static void oldData() throws IOException
	{
		JsonHandler jhandle = new JsonHandler();
		WarehouseHandler handle = WarehouseHandler.getInstance();

		File file = new File(filePath);

		JsonObject data = null;
		data = FileOperations.convertFileToJSON(file);
		if (data != null)
		{
			List<Shipment> list = jhandle.jsonToShipment(data);
			handle.addShipmentList(list);
		}
	}

	public static void saveData() throws IOException
	{
		WarehouseHandler handle = WarehouseHandler.getInstance();
		
		List<Shipment> list = handle.getAllWarehouseShipments(); //get all current data

		for(Shipment s : list)
		{
			// shipment warehouseName != the real warehouse's name
			if(!handle.getWarehouse(s.getWarehouseID()).getWarehouseName().equals(s.getWarehouseName()))
			{
				list.remove(s); //Get rid of the shipment
			}
		}
		

		WarehouseContents contents = new WarehouseContents(new ArrayList<Shipment>(list)); 
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Output with pretty indentation
		String json = gson.toJson(contents);

		// writes warehouse contents into file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writer.write(json);
		writer.close();
	}

}
