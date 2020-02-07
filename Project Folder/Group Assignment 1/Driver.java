
public class Driver
{
	public static void main(String[] args) throws IOException
	{
		WarehouseHandler wHandler = new WarehouseHandler();
		JsonHandler jHandler = new JsonHandler(wHandler);
		InputHandler iHandler = new InputHandler(wHandler, jHandler);
		iHandler.getInput();
	}
}
