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
	        System.out.println("Program canceled.");
	        System.exit(0); //exit the program
	        return null;
	    }
    }
	
	//creates a new file from an inputed file
	public File createFile(String directory, String fileName) throws IOException //creates a file in the file directory
    {
    	File f = new File(directory + "\\"+fileName+".txt"); //needed to add double slashes to the directory for it to work correctly
		if (!f.createNewFile())
		{
		    System.out.println("ERROR: outputFile already in folder, cannot create duplicate. Delete outputFile and restart the program."); //smaller file error handling block
		    System.out.println("Program unsuccessful."); //tell the user the program failed, and they must delete the other outputFile (I could have used a for loop to create outputFile2...n, but I don't want file spam)
		    System.exit(0); //quit the program
		}
    	return f;
    }
	
	//takes a file and an integer, reads the file input into an int array, but ignores the first (linesToSkip) lines
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
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	//converts an array-list to an integer array
    public static int[] convertIntegers(List<Integer> integers) //This is from: https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}

