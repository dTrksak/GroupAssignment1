package edu.metrostate.ics372_assignment3.fileio;
import android.os.Environment;

import java.io.*;
import com.google.gson.*;

import org.json.JSONObject;

//handles basic file operations
public class FileOperations
{
	/**
	 * creates a new file from an inputed file
	 * @param directory the directory of a file
	 * @param fileName the name of a file
	 * @return the file
	 * @throws IOException
	 */
	public File createFile(String directory, String fileName) throws IOException //creates a file in the file directory
	{
		File f = new File(directory + "/"+fileName+".json"); //needed to add double slashes to the directory for it to work correctly
		if (!f.createNewFile())
		{
			System.out.println("Export unsuccessful. outputFile already in folder "+directory+", cannot create duplicate. Delete "+fileName+"."); //smaller file error handling block
			return null;
		}
		return f;
	}

	/**
	 * checks if a file exists in the external storage directory based on a fileName
	 * @param fileName
	 * @return true/false
	 */
	public Boolean fileExists(String fileName)
	{
		String directory = Environment.getExternalStorageDirectory().getPath();
		File f = new File(directory+"/"+fileName);
		if(f.exists())
			return true;
		return false;
	}

	/**
	 * returns a string from an input stream
	 * @param inputStream
	 * @return a string
	 * @throws IOException
	 */
	public static String getFileContents(final InputStream inputStream) throws IOException {

		final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		final StringBuilder stringBuilder = new StringBuilder();

		boolean done = false;

		while (!done) {
			final String line = reader.readLine();
			done = (line == null);

			if (line != null) {
				stringBuilder.append(line);
			}
		}

		reader.close();
		inputStream.close();

		return stringBuilder.toString();
	}

}

