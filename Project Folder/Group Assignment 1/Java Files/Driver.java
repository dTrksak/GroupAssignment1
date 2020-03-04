import java.io.IOException;
import java.util.ArrayList;

public class Driver
{
	public static void main(String[] args) throws IOException //throws IOException
	{

		InputHandler iHandler = new InputHandler();
		iHandler.getInput();
		
		CompleteUI UI = new CompleteUI();
		UI.launchUI();
 
	}
}