import java.io.IOException;

public class Driver
{
	public static void main(String[] args) throws IOException
	{
		WarehouseHandler handle = new WarehouseHandler();
		RecoverData reData = new RecoverData();
		InputHandler iHandler = new InputHandler();
		CompleteUI UI = new CompleteUI();
		
		try {
			iHandler.getInput();
			UI.launchUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ArrayList<Shipment> l = handle.getAllWarehouseShipments();
			reData.saveData(l);
		}
 
	}
}

