package edu.metrostate.ics372_androidstart_master;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class RecoverData {
	static String directory = Environment.getExternalStorageDirectory().getPath();
	static String filePath = directory + "/RecoveredData.json";

	@RequiresApi(api = Build.VERSION_CODES.O)
	public static void oldData() throws IOException {
		JsonHandler jhandle = new JsonHandler();
		WarehouseHandler handle = WarehouseHandler.getInstance();
		FileOperations fo = new FileOperations();

		if (!fo.fileExists(filePath)) {
			fo.createFile(directory, "RecoveredData");
		}
		File file = new File(filePath);

		try {
			InputStream is = new FileInputStream(filePath);


			String data = FileOperations.getFileContents(is);

			if (data != null && data != "") {
				List<Shipment> list = jhandle.jsonToShipment(data);
				handle.addShipmentList(list);
			}
		} catch (Exception e) {
			//File is empty
			InputStream is = new FileInputStream(filePath);
			String data = FileOperations.getFileContents(is);
			if (data != null) {
				List<Shipment> list = jhandle.jsonToShipment(data);
				handle.addShipmentList(list);
			}
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

