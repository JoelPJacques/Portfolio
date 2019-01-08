//Joel Jacques
//Create Date: 9/28/2017
//Modified Date: 9/30/2017
//Chapter 21 - Question 21.9

/* 
 * The program prompts the user for the name state then returns the capital for 
 * the state back to the console.
 * 
 * Text File Must Have the Format
 * 
 * Alabama,Montgomery
 * Alaska,Juneau
 * Arizona,Phoenix
 * Arkansas,Little Rock
 * .....
 */

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.File;
import java.io.FileNotFoundException;

public class Jacques_21_09 {
	// The file being used to populate the HashMap (StateCapitals.txt) needs to be
	// stored on the desktop
	public static void main(String[] args) {
 
		File txtStCaps = new File(getFilePath("StateCapitals.txt")); //file object pointing to 'StateCapitals.txt'
		HashMap<String, String> hashMap = new HashMap<>();
		try {
			hashMap = populateMapObject(txtStCaps);
			
			//display key value names
			displayStates(hashMap);

			 System.out.println("\n\nEnter The State From the Above List - Case Sensitive! Must Match Exactly!");
			 System.out.print("Enter State Name: ");
			 Scanner input = new Scanner(System.in);
			 String strState = input.nextLine();
			
			 //Check if user input a valid key
			 if (isState(strState, hashMap)) {
				 System.out.print("Capital of " + strState + " is: " + getCapital(strState, hashMap));
			 }
			 else {
				 System.out.print(strState + " is not a state name!");	
			 }
			 
			input.close();
		}
		//catch block to handle if the source file for the population of the hashmap is not located on desktop
		//or an incorrect name
		catch (FileNotFoundException e) {
			System.out.print("The File 'StateCapitals.txt' Does Not Exist On Your Desktop!");
		}
	}

	/*
	 * populateMapObject - populates a HashMap<String, String> with States and Capitals
	 * 
	 * @pre - The file object must be the 'StateCapitals.txt' file stored on the desktop
	 * @post - HashMap<String, String> with key values being the name of the State and the data
	 * being the corresponding capital city
	 * @param - File Object
	 * 
	 */
	
	private static HashMap<String, String> populateMapObject(File f) throws FileNotFoundException {
		Scanner input = new Scanner(f).useDelimiter("[\\n\\t.,;:!?(){}<>\\\"]");
		HashMap<String, String> hashMap = new HashMap<>();
		while (input.hasNext()) {
			String state = input.next();
			String capital = input.next();	
			hashMap.put(state, capital);	//insert into hashmap 
											//each execution of this block reflects a newline in text file
		}
		return hashMap;
	}

	/*
	 * displayStates - Displays all States in ascending order
	 * from left to right with 5 states on each line
	 * 
	 * @pre - hashMap of type <String, String> where State Names occupy the Key Value Parameter
	 * @param - HashMap<String, String>
	 * 
	 */	
	
	private static void displayStates(HashMap<String, String> h) {

		TreeSet<String> treeSet = new TreeSet<String>(h.keySet());
		int i = 0; 	//set counter
		for (String elem : treeSet) {
			System.out.printf("%-15s", elem);
			i += 1; //increment counter
			
			if (i == 5) {	//new line printed for every 5 states
				System.out.println();
				i = 0; //reset counter
			}		
		}
	}

	/*
	 * isState - checks if key is located in the hashmap boolean return type
	 * 
	 * @pre - hashMap of type <String, String> where State Names occupy the Key Value Parameter
	 * @post - returns true if key is present and false if key is not present
	 * @param1 - String containing key to be tested against hashmap
	 * @param2 - HashMap<String, String>
	 * 
	 */	
	
	private static boolean isState(String key, HashMap<String, String> hashMap) {
		return hashMap.containsKey(key);
	}
	
	/*
	 * getCapital - retrieve capital from hashmap and pass it back as a string
	 * 
	 * @pre - hashMap of type <String, String> where State Names occupy the Key Value Parameter
	 * @post - returns string containing the capital city corresponding to the State passed in
	 * @param1 - String containing key to be tested against hashmap
	 * @param2 - HashMap<String, String>
	 * 
	 */	
	
	private static String getCapital(String key, HashMap<String, String> hashMap) {
		return hashMap.get(key);
	}

	/*
	 * getFilePath - generates and returns a string with the path for 'StateCapitals.txt' only 
	 * tested on Windows platform
	 * 
	 * @post - returns string containing the file path for the file name being passed in
	 * @param - String containing text file containing State and Capital Data
	 * 
	 */		
	private static String getFilePath(String s) {

		File desktop = new File(System.getProperty("user.home"), "Desktop"); //C:\Users\'UserName'\Desktop
		return desktop.getPath() + "\\" + s; //append \\ and filename
	}

}
