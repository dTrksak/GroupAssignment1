package main;

import java.io.IOException;

public class Driver
{
	public static void main(String[] args) throws IOException
	{
		RecoverData.oldData();
		CompleteUI UI = new CompleteUI();
		UI.launchUI();
	}
}

