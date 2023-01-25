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
 * This class is intended to handle the loading and saving of csv data to the SQL database.
 * 
 * TODO Add Documentation, review imports
 * 
 * @author Felipe Vasquez
 */
public class CSVOperations {
	public static String[] importCSV(File file) {
		List<String> list = new LinkedList<String>();
		try {
			Scanner Reader = new Scanner(file);
			while (Reader.hasNextLine()) {
				String data = Reader.nextLine();
				//String[] subString = data.split(", ", 0);
				//String type = subString[subString.length - 1];
				list.add(data);
				//System.out.println(data);
			}
			Reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error has occurred.");
			e.printStackTrace();
		}
		System.out.println("importCSV() Successful");
		return list.toArray(new String[0]);		
	}
	
	public static void exportCSV(File f, ArrayList<String> data) {
		try {
			FileWriter writer = new FileWriter(f.getAbsolutePath());
			for (int i = 0; i < data.size(); i++) {
				//System.out.println(data[i]);
				writer.write(data.get(i)+"\r\n");
			}
			
			writer.close();
			
			System.out.println("exportCSV() Successful");
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
