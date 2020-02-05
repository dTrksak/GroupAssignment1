package com.json.warehouse;
import com.google.gson.*;
import java.util.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class JsonHandler{
	
	private WarehouseHandler h;
	
	public void JsonHandler(WarehouseHandler handler) {
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

	

	
	public void warehouseToJson(ArrayList<Shipment> list) {
		Gson gson = new Gson();
		String directory = System.getProperty("user.dir");
		
		
		String json = gson.toJson(list);
		  try {
			   FileWriter writer = new FileWriter(directory);
			   writer.write(json);
			   writer.close();
			  
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	}
}
	


