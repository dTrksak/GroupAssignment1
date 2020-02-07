import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.util.Iterator;

//handles basic file operations
public class FileOperations
{
	//opens a JFileChooser GUI so the user can select a file
	public File fileInput()
    {	
    	JFileChooser fileChooser = new JFileChooser(); //open the file chooser
    	JDialog dialog = new JDialog();

	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //set the current directory to be the one where this program is in
	    int result = fileChooser.showOpenDialog(dialog);
	    if (result == JFileChooser.APPROVE_OPTION) //if a file is approved
	    {
	        File selectedFile = fileChooser.getSelectedFile();
	        return selectedFile; //return the file
	    }
	    else //if the file chooser is cancelled
	    {
	        System.out.println("Import shipments cancelled.");
	        return null;
	    }
    }
	
	public File fileDirectory()
	{
		JFileChooser fileChooser = new JFileChooser(); //open the file chooser
    	JDialog dialog = new JDialog();  

	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); //set the current directory to be the one where this program is in
	    fileChooser.setFileSelectionMode(fileChooser.DIRECTORIES_ONLY);
	    int result = fileChooser.showOpenDialog(dialog);
	    if (result == JFileChooser.APPROVE_OPTION) //if a file is approved
	    {
	    	return fileChooser.getSelectedFile();
	    }
	    else //if the file chooser is cancelled
	    {
	        return null;
	    }
	}
	
	//creates a new file from an inputed file
	public File createFile(String directory, String fileName) throws IOException //creates a file in the file directory
    {
    	File f = new File(directory + "\\"+fileName+".txt"); //needed to add double slashes to the directory for it to work correctly
		if (!f.createNewFile())
		{
		    System.out.println("Export unsuccessful. outputFile already in folder "+directory+", cannot create duplicate. Delete "+fileName+"."); //smaller file error handling block
		    return null;
		}
    	return f;
    }
	
	//takes a file and translates that to a string
	public String fileToString(File inputFile) throws FileNotFoundException
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(inputFile.getPath()))) 
	    {
	 
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null) 
	        {
	            contentBuilder.append(sCurrentLine).append("\n");
	        }
	    } 
	    catch (IOException e) 
	    {
	    	System.out.println("File not found.");
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
}

