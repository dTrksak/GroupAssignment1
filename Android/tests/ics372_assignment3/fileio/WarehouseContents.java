package edu.metrostate.ics372_assignment3.fileio;
import java.util.*;

import edu.metrostate.ics372_assignment3.data.Shipment;

//Used to get to a list inside of a Json object
public class WarehouseContents
{
	List<Shipment> warehouse_contents;

	public WarehouseContents(List<Shipment> list)
	{
		this.warehouse_contents = list;
	}
}
