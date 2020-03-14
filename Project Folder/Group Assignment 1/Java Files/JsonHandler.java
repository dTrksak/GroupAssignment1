package main;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonHandler
{
	private WarehouseHandler h = WarehouseHandler.getInstance();

	/**
	 * Takes a jsonObject of shipments and creates those shipments and warehouses
	 * @param jo
	 * @return the list of created shipments
	 * @throws IOException
	 */
	public List<Shipment> jsonToShipment(JsonObject jo) throws IOException
	{
		List<Shipment> shipList = new ArrayList<>();

		JsonArray shipArray = new JsonArray();
		try
		{
			JsonObject shipObject = jo;
			shipArray = shipObject.getAsJsonArray("warehouse_contents");
		}
		catch (JsonSyntaxException e)
		{
			System.out.println("Json syntax exception in file, cannot add shipments.");
			return null; // break out of the function
		}

		try
		{
			for (JsonElement i : shipArray)
			{
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
					Float.parseFloat(shipmentObj.get("weight").getAsString());
					Long.parseLong(shipmentObj.get("receipt_date").getAsString());
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
			for (Shipment s : shipList)
			{
				h.addShipment(s.getWarehouseID(), s.getWarehouseName(), s.getShipmentID(), s.getShipmentMethod(), s.getWeight(), s.getReceiptDate());
			}
			System.out.println("Shipments successfully imported.");
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
	public void shipmentToJson(ArrayList<Shipment> list, String fileName) throws IOException
	{ // takes a list of shipments and writes them to a Json file

		FileOperations fo = new FileOperations();
		File directory = fo.fileDirectory();

		if (directory == null)
		{
			System.out.println("Export cancelled.");
			return; // If the user cancelled the export, return
		}

		WarehouseContents contents = new WarehouseContents(list);
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Output with pretty indentation
		String json = gson.toJson(contents);

		File outFile = fo.createFile(directory.getPath(), fileName);

		if (outFile != null)
		{ // writes warehouse contents into file
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			writer.write(json);
			writer.close();
			System.out.println("Shipments successfully exported.");
		}
	}
}
