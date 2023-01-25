package Sketchbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * This class handles the loading and saving of csv data.
 * 
 * The method importCSV reads data from a csv and outputs it as an array of strings.
 * 
 * The method exportCSV takes a filechooser output and an arraylist containing strings
 * and writes them to the filechooser output as a csv file.
 * 
 * @author Felipe Vasquez
 */
public class CSVOperations {
	/**
	 * Reads a selected file and outputs them as an array of strings.
	 * 
	 * A LinkedList of Strings is initialized.
	 * File data is read using a default java Scanner, iterates through each line.
	 * As the lines are parsed, they are appended to the list.
	 * The list is finally converted to a String array on output.
	 * In theory works for any text file. 
	 * Any data parsing must be performed outside of this method.
	 * 
	 * INPUTS:
	 * File file - The results of a Swing fileChooser operation.
	 * 
	 * OUTPUTS:
	 * String[] list - An array where each item is the contents of one line of CSV.
	 */
	public static String[] importCSV(File file) {
		List<String> list = new LinkedList<String>();
		try {
			Scanner Reader = new Scanner(file);
			while (Reader.hasNextLine()) {
				String data = Reader.nextLine();
				list.add(data);
			}
			Reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error has occurred.");
			e.printStackTrace();
		}
		System.out.println("importCSV() Successful");
		return list.toArray(new String[0]);		
	}
	/**
	 * Takes an array of strings and writes them to a selected file.
	 * 
	 * A new java.io FileWriter is initialized.
	 * The method iterates through the input array "data".
	 * For each iteration a new line is written via the FileWriter
	 * Upon completion, prints a success message and closes the writer.
	 * The user should specialize the file format extension.
	 * This is mainly meant to ouput files in CSV format,
	 * but data should be pre-formatted for this application.
	 * 
	 * INPUTS:
	 * File f - The results of a Swing fileChooser operation.
	 * Arraylist<String> data - an ArrayList containing strings.
	 * 
	 * OUTPUTS:
	 * A file with user-specified name, location, and file extension.
	 */
	public static void exportCSV(File f, ArrayList<String> data) {
		try {
			FileWriter writer = new FileWriter(f.getAbsolutePath());
			for (int i = 0; i < data.size(); i++) {
				writer.write(data.get(i)+"\r\n");
			}
			
			writer.close();
			
			System.out.println("exportCSV() Successful");
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
