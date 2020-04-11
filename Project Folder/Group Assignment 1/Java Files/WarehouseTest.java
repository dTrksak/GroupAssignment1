
import static org.junit.Assert.*;


import org.junit.Test;

public class WarehouseTest 
{

	@Test
	public void testConstructor() 
	{
		//arrange/act
		Warehouse warehouseTest = new Warehouse("123456", "thisWarehouse");
		//assert
		assertTrue(warehouseTest instanceof Warehouse);
		assertEquals("123456", warehouseTest.getWarehouseID());
		assertTrue(warehouseTest.getAvailability());
	}
	

	@Test
	public void testAvailability()
	{
		//arrange
		Warehouse warehouseTester = new Warehouse("123456", "thisWarehouse");
		//act
		warehouseTester.disableFreightReceipt();
		//assert
		assertFalse(warehouseTester.getAvailability());
		warehouseTester.enableFreightReceipt();
		assertTrue(warehouseTester.getAvailability());
		
	}
	@Test
	public void testAddShipment()
	{
		//arrange
		Warehouse warehouseTester = new Warehouse("23456", "thisWarehouse");
		Shipment testerShipment = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
		//act
		warehouseTester.addShipment(testerShipment);
		//assert
		assertEquals("23456", warehouseTester.getShipmentList().get(0).getWarehouseID());
		assertEquals("1234", warehouseTester.getShipmentList().get(0).getShipmentID());
		assertEquals("air", warehouseTester.getShipmentList().get(0).getShipmentMethod());
		assertEquals(34.6f, warehouseTester.getShipmentList().get(0).getWeight(), .000001);
		assertEquals(345323, warehouseTester.getShipmentList().get(0).getReceiptDate());
		assertEquals(warehouseTester.getShipmentList().get(0), testerShipment);
	}
	@Test
	public void testMultiShipment()
	{
		//arrange
		Warehouse warehouseTester = new Warehouse("23456", "thisWarehouse");
		
		Shipment testerShipment1 = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
		Shipment testerShipment2 = new Shipment("23456", "thisOtherWarehouse", "1234", "air", 34.6f, 345323);
		Shipment testerShipment3 = new Shipment("23456", "oneMoreWarehouse", "1234", "air", 34.6f, 345323);
		//act
		warehouseTester.addShipment(testerShipment1);
		warehouseTester.addShipment(testerShipment2);
		warehouseTester.addShipment(testerShipment3);
		//assert
		assertEquals(3, warehouseTester.getShipmentList().size());
	}
	@Test
	public void testRejectShipment()
	{
		//arrange
		Warehouse warehouseTester = new Warehouse("23456", "thisWarehouse");
		Shipment testerShipment1 = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
		//act
		warehouseTester.disableFreightReceipt();
		//assert
		assertNull(warehouseTester.addShipment(testerShipment1));
		
	}
}