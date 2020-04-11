
import static org.junit.Assert.*;

import org.junit.Test;

public class ShipmentTest 
{

	@Test
	public void testAddShipment()
	{
		//arrange
		Warehouse warehouseTester = new Warehouse("23456", "thisWarehouse");
		Shipment testerShipment = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
		//act
		warehouseTester.addShipment(testerShipment);
		//assert
		assertEquals("23456", testerShipment.getWarehouseID());
		assertEquals("1234", testerShipment.getShipmentID());
		assertEquals("air", testerShipment.getShipmentMethod());
		assertEquals(34.6f, testerShipment.getWeight(), .000001);
		assertEquals(345323, testerShipment.getReceiptDate());
		assertEquals(warehouseTester.getShipmentList().get(0), testerShipment);
	}
	@Test
	public void testToString()
	{
		
		Shipment testerShipment = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
		
		assertEquals(testerShipment.toString(), "\n\twarehouse ID: "+testerShipment.getWarehouseID()+
	                    ", Shipment ID: "+ testerShipment.getShipmentID() +
	                    ", Shipment Method: "+ testerShipment.getShipmentMethod() +
	                    ", Weight : " + testerShipment.getWeight() +
	                    ", ReceiptDate : " + testerShipment.getReceiptDate());
	}

}
