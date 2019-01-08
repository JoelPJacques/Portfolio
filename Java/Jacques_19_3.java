
//Joel Jacques
//Create Date: 9/13/2017
//Modified Date: 9/16/2017
//Chapter 19 - Question 19.3

import java.util.ArrayList;
import java.util.Scanner;

public class Jacques_19_3 {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		String inpUser;
		String inpContinue = "Y";

		System.out.println("Enter items to be inserted into the ArrayList as a ");
		System.out.println("comma seperated list");
		System.out.println("ex. 102,100,103 etc..\n");

		while (inpContinue.matches("[Yy]")) {
			
			System.out.print("Enter Items: ");
			inpUser = input.next();
		
			System.out.print("The entries you made were: ");
			DisplayList(fillArrayList(inpUser));

			System.out.print("Removal of duplicates yeilds: ");
			DisplayList(removeDuplicates(fillArrayList(inpUser)));

			System.out.print("Continue y/n:");
			inpContinue = input.next();	
			
			while (!isValidContInput(inpContinue)) {
				System.out.print("Not a valid input: Please enter either y or n");
				inpContinue = input.next();				
			}	
		}

		input.close(); // Close Scanner Object
	}

	/**
	 * Remove duplicates items from ArrayList and return the new ArrayList with
	 * duplicates removed
	 * 
	 * @param Generic
	 *            ArrayList containing user all user entries
	 */
	private static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
		ArrayList<E> retArr = new ArrayList<>();
		int j = 0; // counter variable
		do {

			if (list.size() > 0) {
				retArr.add(list.get(0)); // element at index 0 will not be in retArr
											// added to retArr before it is removed

				while (list.indexOf(retArr.get(j)) != -1) { // loop until items equal to element j in retArr are removed
					list.remove(list.indexOf(retArr.get(j))); // remove item if equal to element added to retArr
				}
				j += 1; // increment counter by 1
			} else
				System.out.print("The ArrayList Is Empty");

		} while (!list.isEmpty()); // check if list is empty

		return retArr;
	}

	/**
	 * Populates an empty ArrayList with comma separated string
	 * 
	 * @precondition string with items separated with commas with including no
	 *               whitespace characters
	 * @param String
	 *            containing items separated by commas
	 */
	private static <E> ArrayList<E> fillArrayList(String s) {

		ArrayList<E> retVal = new ArrayList<E>();
		String[] tokens = s.split(","); // fill String array with entries
		for (int i = 0; i < tokens.length; i++) { // iterate through items in array
			retVal.add((E) tokens[i]); // Add array items to ArrayList Object
		}
		return retVal;
	}

	/**
	 * Displays the items in the ArrayList to the console in the order they were
	 * entered into the ArrayList
	 * 
	 * @param list ArrayList containing user entries
	 */
	private static <E> void DisplayList(ArrayList<E> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() - 1)
				System.out.print(list.get(i) + ", ");
			else
				System.out.print(list.get(i) + "\n");
		}
	}
	
	//check the continuation character entered by the user
	private static boolean isValidContInput (String s) {
		return s.matches("[YyNn]");	
	}

}
