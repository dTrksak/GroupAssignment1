import java.util.*;

public class Warehouse {
	
private String warehouseID;
private List<Shipment> shipList = new ArrayList<Shipment>();
private boolean availability = true;

public Warehouse(String wareID){
	warehouseID = wareID;
}
public void enableFreightReceipt(){
	availability = true;
}
public void disableFrieghtReceipt(){
	availablity = false;
}
public boolean  getAvailability(){
	return availability;
}
public void addShipment(Shipment shipment){
	if(availability == true){
	shipList.add(shipment);
	}else{
		System.out.println("Sorry that warehouse is not accepting shipments");
	}
	}
}
