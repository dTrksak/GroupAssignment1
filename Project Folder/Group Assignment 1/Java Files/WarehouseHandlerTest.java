
import static org.junit.Assert.*;

import org.junit.Test;

public class WarehouseHandlerTest 
{

	@Test
	public void addShipmentTest()
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		//act
		handleIt.addWarehouse("2345", "Warehouse1");
		handleIt.addShipment("2345", "Warehouse1", "345", "air", 45.0f, 79867687);
		//assert
		assertEquals(1, handleIt.getWarehouseShipments("2345").size());
		//act
		handleIt.addShipment("2345", "Warehouse1", "345", "ship", 145.0f, 79867687);
		handleIt.addShipment("2345", "Warehouse1", "343", "air", 45.0f, 79867687);
		handleIt.addShipment("2345", "Warehouse1", "342", "truck", 5.0f, 79867687);
		//assert
		assertEquals(4, handleIt.getWarehouseShipments("2345").size());
		assertEquals("342", handleIt.getWarehouseShipments("2345").get(3).getShipmentID());
		assertEquals("ship", handleIt.getWarehouseShipments("2345").get(1).getShipmentMethod());
		
		
		
	}

	@Test
	public void getWarehouseShipmentsTest() 
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		WarehouseHandler handleMe2 = new WarehouseHandler();
		//act
		handleIt.addWarehouse("2345", "Warehouse1");
		handleIt.addShipment("2345", "Warehouse1", "345", "truck", 45.9f, 34534365);
		handleIt.addShipment("2345", "Warehouse1", "356", "truck", 45.8f, 34534365);
		handleIt.addShipment("2345", "Warehouse1", "347", "air", 45.0f, 34534365);

		
		//assert
		assertEquals(3, handleIt.getWarehouseShipments("2345").size());
		assertEquals("345", handleIt.getWarehouseShipments("2345").get(0).getShipmentID());
		assertEquals("air", handleIt.getWarehouseShipments("2345").get(2).getShipmentMethod());
		assertTrue("AIR".equalsIgnoreCase(handleIt.getWarehouseShipments("2345").get(2).getShipmentMethod()));
		assertEquals(45.8, handleIt.getWarehouseShipments("2345").get(1).getWeight(), .00001);
		
		assertNull(handleMe2.getWarehouseShipments("567"));
	}

	@Test
	public void getAllWarehouseShipmentsTest() 
	{
		// arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		WarehouseHandler handleMe = new WarehouseHandler();
		// act
		handleIt.addWarehouse("2345", "Warehouse1");
		handleIt.addShipment("2345", "Warehouse1", "345", "truck", 45.9f, 34534365);
		handleIt.addShipment("2345", "Warehouse1", "356", "truck", 45.8f, 34534365);
		handleIt.addShipment("2345", "Warehouse1", "347", "air", 45.0f, 34534365);

		handleIt.addWarehouse("2346", "Warehouse2");
		handleIt.addShipment("2346", "Warehouse2", "345", "truck", 45.9f, 34534365);
		handleIt.addShipment("2346", "Warehouse2", "356", "truck7", 45.8f, 34534365);
		handleIt.addShipment("2346", "Warehouse2", "347", "air", 45.0f, 34534365);
		// assert
		assertEquals(6, handleIt.getAllWarehouseShipments().size());
		assertEquals("truck7", handleIt.getAllWarehouseShipments().get(4).getShipmentMethod());
		assertNull(handleMe.getAllWarehouseShipments());
	}
	
	@Test
	public void addWarehouseTest() 
	{
		// arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		// act
		handleIt.addWarehouse("2345", "Warehouse1");
		// assert
		assertEquals(1, handleIt.getAllWarehouses().size());
		assertEquals("2345", handleIt.getAllWarehouses().get(0).getWarehouseID());
		assertEquals("Warehouse1", handleIt.getAllWarehouses().get(0).getWarehouseName());

		assertNull(handleIt.addWarehouse("2345", "Warehouse2"));

	}

	@Test
	public void getAllWarehousesTest() 
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		//assert
		assertNull(handleIt.getAllWarehouses());
		//act
		handleIt.addWarehouse("2345", "Warehouse1");
		handleIt.addWarehouse("2346", "Warehouse2");
		handleIt.addWarehouse("2347", "Warehouse3");
		//assert
		assertEquals(3, handleIt.getAllWarehouses().size());
		assertEquals("2346", handleIt.getAllWarehouses().get(1).getWarehouseID());

	}
	@Test
	public void getWarehouseTest()
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		//assert
		assertNull(handleIt.getWarehouse("2345"));
		//act
		handleIt.addWarehouse("2345", "warehouse1");
		//assert
		assertEquals("2345", handleIt.getWarehouse("2345").getWarehouseID());
	}
	@Test
	public void getWarehouseReceiptTest()
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		//act
		handleIt.addWarehouse("2345", "warehouse1");
		//assert
		assertTrue(handleIt.getWarehouseReceipt("2345"));
		assertNull(handleIt.getWarehouseReceipt("3245"));
		
	}
	@Test
	public void setWarehouseRecieptTest()
	{	//arrange
		WarehouseHandler handleIt = new WarehouseHandler();
		//act
		handleIt.addWarehouse("2345", "warehouse1");
		handleIt.setWarehouseReceipt("2345", false);
		//assert
		assertFalse(handleIt.getWarehouse("2345").getAvailability());
		
		
	}

}
