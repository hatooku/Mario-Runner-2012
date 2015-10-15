/**
 * @(#)FileDriver.java
 *
 *
 * @author Matt Nguyen
 * @version 1.00 2012/5/24
 */
import java.io.*;
import java.util.*;

public class FileDriver {


	public FileDriver()  {

    }

	//returns an array of strings from the file
    public String[]  getStringArray(String fileName)  {

    	ArrayList<String> fileArray = new ArrayList<String>();
    	String[] strArray = null;
    	try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			String str;
			while((str = in.readLine()) != null ) {
				fileArray.add(str);
			}

			strArray = fileArray.toArray(new String[fileArray.size()]);

			} catch (IOException e) {
				System.out.println(e);
			}


    	return strArray;

    }

	//will write a string of text to a file
    public void addToFile(String fileName, String[] txt) {

    	try {
    		PrintWriter out = new PrintWriter(new FileWriter(fileName));
    		for(int i = 0; i < txt.length; i++) {
    			out.println(txt[i]);
    		}
    		out.close();
    	} catch (IOException e) {
			System.out.println(e);

		}


    }



}
