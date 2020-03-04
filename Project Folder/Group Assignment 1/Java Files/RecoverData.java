import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class RecoverData {
	
	public void oldData(WarehouseHandler handle, JsonHandler jhandle) throws FileNotFoundException {
		File file = new File("src\\RecoveredData.json");
		
		//String directory = System.getProperty("user.dir");
		//File file = new File(directory,"\\RecoveredData.json");
		
		FileOperations fileIO = new FileOperations();
		JsonObject data = null;
		data = fileIO.convertFileToJSON(file);
		System.out.println(data);
		if(data != null) {
			System.out.println("in if statement");
			jhandle.jsonToShipment(data);
			
		}
		
	}

	public void saveData(ArrayList<Shipment> list) throws IOException {
		WarehouseContents contents = new WarehouseContents(list);
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); //Output with pretty indentation
		String json = gson.toJson(contents);
		
		
		 // writes warehouse contents into file
			BufferedWriter writer = new BufferedWriter(new FileWriter("src\\RecoveredData.json"));
			writer.write(json);
		    writer.close();
		    System.out.println("Shipments successfully exported.");
		}
	
}
