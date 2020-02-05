
public class Driver
{
	public static void main(String[] args)
	{
		WarehouseHandler wHandler = new WarehouseHandler();
		JsonHandler jHandler = new JsonHandler();
		InputHandler iHandler = new InputHandler(wHandler, jHandler);
	}
}
