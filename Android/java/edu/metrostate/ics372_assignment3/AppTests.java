package edu.metrostate.ics372_assignment3;

import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class AppTests {

    MainActivityPresenter presenter;

    @Mock
    MainActivityView  view;


    @Before
    public void setUp() throws Exception {
        presenter = new MainActivityPresenter(view);
    }

    @Test
    public void textUpdated() throws Exception{
        //Arrange
        String givenString = "test123";
        //Act
        presenter.textUpdated(givenString);
        //Assert
        Mockito.verify(view).checkText(givenString);
    }
    @Test
    public void launchOtherActivity() throws Exception{
        //Arrange
        Class mockClass = InfoActivity.class;
        //Act
        presenter.launchOtherActivityClicked(mockClass);
        //Assert
        Mockito.verify(view).launchOtherActivity(mockClass);
    }
    @Test
    public void fileChooser() throws Exception{
        //Arrange
        Intent mockIntent = new Intent();
        //Act
        presenter.fileChooserOpened(mockIntent);
        //Assert
        Mockito.verify(view).fileOpener(mockIntent);
        assertNotNull(mockIntent);
    }
    @Test
    public void printSomething() throws Exception{
        //Arrange
        TextView testView = Mockito.mock(TextView.class);
        //Act
        presenter.printAllSelected(testView);
        //Assert
        assertNotNull(testView);

    }
    @Test
    public void testConstructor()
    {
        //arrange/act
        Warehouse warehouseTest = Mockito.mock(Warehouse.class);
        warehouseTest = new Warehouse("123456", "thisWarehouse");
        //assert
        assertTrue(warehouseTest instanceof Warehouse);
        assertEquals("123456", warehouseTest.getWarehouseID());
        assertTrue(warehouseTest.getAvailability());
    }


    @Test
    public void testAvailability()
    {
        //arrange
        Warehouse warehouseTester = Mockito.mock(Warehouse.class);
        warehouseTester = new Warehouse("123456", "thisWarehouse");
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
        Warehouse warehouseTester = Mockito.mock(Warehouse.class);
        Shipment testerShipment = Mockito.mock(Shipment.class);
        warehouseTester = new Warehouse("23456", "thisWarehouse");
        testerShipment = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
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
    public void testRejectShipment()
    {
        //arrange
        Warehouse warehouseTester = Mockito.mock(Warehouse.class);
        Shipment testerShipment1 = Mockito.mock(Shipment.class);
        warehouseTester = new Warehouse("23456", "thisWarehouse");
        testerShipment1 = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
        //act
        warehouseTester.disableFreightReceipt();
        //assert
        assertNull(warehouseTester.addShipment(testerShipment1));

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
    @Test
    public void testingAddShipment()
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
        Shipment testerShipment = Mockito.mock(Shipment.class);
        testerShipment = new Shipment("23456", "thisWarehouse", "1234", "air", 34.6f, 345323);
        DateFormat simple = new SimpleDateFormat("MMM dd yyy HH:mm:ss");
        Date result = new Date(testerShipment.getReceiptDate());
        assertEquals(testerShipment.toString(), ("\n\t warehouse ID:" + testerShipment.getWarehouseID()+
                ",  Warehouse Name: "+testerShipment.getWarehouseName()+
                ",  Shipment ID: "+ testerShipment.getShipmentID() +
                ",  Shipment Method:  "+ testerShipment.getShipmentMethod() +
                ",  Weight : " + testerShipment.getWeight() +
                ",  ReceiptDate : " + simple.format(result)));
    }

}





