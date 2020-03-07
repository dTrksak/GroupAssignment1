package main;

import java.io.IOException;

public class Driver
{
	public static void main(String[] args) throws IOException
	{
		RecoverData.oldData();
		InputHandler iHandler = new InputHandler();
		CompleteUI UI = new CompleteUI();

		// iHandler.getInput();
		UI.launchUI();
	}
}
