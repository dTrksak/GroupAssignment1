package edu.metrostate.ics372_assignment3;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHandler
{
	private WarehouseHandler h = WarehouseHandler.getInstance();

	/**
	 * Takes a jsonObject of shipments and creates those shipments and warehouses
	 * @param data
	 * @return the list of created shipments
	 * @throws IOException
	 */
	public List<Shipment> jsonToShipment(String data) throws IOException
	{
		List<Shipment> shipList = new ArrayList<>();

		JsonArray shipArray = new JsonArray();

		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject) parser.parse(data);
		try
		{
			shipArray = jo.getAsJsonArray("warehouse_contents");
		}
		catch (JsonIOException e)
		{
			System.out.println("Json syntax exception in file, cannot add shipments.");
			return null; // break out of the function
		}

		try
		{
			for (JsonElement i : shipArray) {
				JsonObject shipmentObj;
				String warehouseID;
				String warehouseName;
				String shipmentMethod;
				String shipmentID;
				try {
					shipmentObj = i.getAsJsonObject();
					warehouseID = shipmentObj.get("warehouse_id").getAsString();
					warehouseName = shipmentObj.get("warehouse_name").getAsString();
					shipmentMethod = shipmentObj.get("shipment_method").getAsString();
					shipmentID = shipmentObj.get("shipment_id").getAsString();

				} catch(Exception e) {
					return null; //Format error
				}
				// Check that weight and receipt date are correctly formatted
				try
				{
					Float.parseFloat(shipmentObj.get("weight").toString());
					Long.parseLong(shipmentObj.get("receipt_date").toString());
				}
				catch(JsonIOException e){
					e.printStackTrace();
				}

				catch (NumberFormatException e)
				{
					System.out.println("Number format exception, unable to add shipment " + shipmentID + ". A float and/or a long are not correctly formatted.");
					continue; // continue the for loop
				}
				float weight = shipmentObj.get("weight").getAsFloat();
				long receiptDate = shipmentObj.get("receipt_date").getAsLong();

				// If an id contains a comma, do not add that shipment
				if (warehouseID.contains(",") || shipmentID.contains(","))
				{
					System.out.println("Value error, unable to add shipment " + shipmentID + ". An ID contained a comma.");
					continue;
				}

				// Add the shipment to shiplist
				shipList.add(new Shipment(warehouseID, warehouseName, shipmentID, shipmentMethod, weight, receiptDate));

			}
			
			// Once all of the shipments have been added to shiplist without errors, add
			// them to warehouseHandler
			System.out.println("Shipments successfully imported.");

		}
		catch(JsonIOException e){
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			System.out.println("Shipment element mislabled, please check the file for typos.");
			System.out.println("Could not import the shipment file.");
			e.printStackTrace();
			return null;
		}

		return shipList;
	}

	/**
	 * Exports a list of shipments to a json file
	 * @param list the shipments
	 * @throws IOException
	 */
	public void shipmentToJson(List<Shipment> list, String fileName) throws IOException
	{ // takes a list of shipments and writes them to a Json file

		FileOperations fo = new FileOperations();
		String directory = Environment.getExternalStorageDirectory().getPath();

		if (directory == null)
		{
			Log.d("jsonhandler","Export cancelled.");
			return; // If the user cancelled the export, return
		}

		WarehouseContents contents = new WarehouseContents(list);
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Output with pretty indentation
		String json = gson.toJson(contents);

		File outFile = new File(directory+"/"+fileName+".json");

		if (outFile != null)
		{ // writes warehouse contents into file
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			writer.write(json);
			writer.close();
		}
	}
}
