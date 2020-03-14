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

	public static List<Shipment> oldData() throws IOException
	{
		JsonHandler jhandle = new JsonHandler();
		WarehouseHandler handle = WarehouseHandler.getInstance();
		File file = new File(filePath);

		// String directory = System.getProperty("user.dir");
		// File file = new File(directory,"\\RecoveredData.json");

		JsonObject data = null;
		data = FileOperations.convertFileToJSON(file);
		if (data != null)
		{
			List<Shipment> list = jhandle.jsonToShipment(data);
			return list;
		}
		return null;
	}

	public static void saveData(List<Shipment> list) throws IOException
	{
		WarehouseHandler handle = WarehouseHandler.getInstance();
		for(Shipment s : list)
		{
			// shipment warehouseName != the real warehouse's name
			if(!handle.getWarehouse(s.getWarehouseID()).getWarehouseName().equals(s.getWarehouseName()))
			{
				list.remove(s); //Get rid of the shipment
			}
		}
		
		List<Shipment> oldData = RecoverData.oldData(); // Get the already saved data
		if (oldData != null)
		{
			list.addAll(list); // Add the saved data if there is any
		}

		WarehouseContents contents = new WarehouseContents(new ArrayList<Shipment>(list)); // Does this work??
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Output with pretty indentation
		String json = gson.toJson(contents);

		// writes warehouse contents into file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writer.write(json);
		writer.close();
	}

}
