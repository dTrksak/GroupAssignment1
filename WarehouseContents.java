package main;
import java.util.*;

//Used to get to a list inside of a Json object
public class WarehouseContents
{
	List<Shipment> warehouse_contents;
	
	public WarehouseContents(List<Shipment> list)
	{
		this.warehouse_contents = list;
	}
}
