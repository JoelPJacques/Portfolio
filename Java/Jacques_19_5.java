//Joel Jacques
//Create Date: 9/16/2017
//Chapter 19 - Question 19.5

public class Jacques_19_5 {

	public static void main(String[] args) {

		System.out.println("String Entries Into Array: George, Sara, Tom, Bill, Whitney");
		String[] strEntries = {"George", "Sara", "Tom", "Bill", "Whitney"};
		System.out.println("The max entry in the Array is: " + max(strEntries));
		System.out.println();
		System.out.println("Integer Entries Into Array: 1902, 5, 300, 5, 1096");
		Integer[] intEntries = {1902, 5, 300, 5, 1096};
		System.out.println("The max entry in the Array is: " + max(intEntries));
		System.out.println();
		System.out.println("Double Entries Into Array: 5.6, 5.0, 300.92, 53.1, 1096.0");
		Double[] dblEntries = {5.6, 5.0, 300.92, 53.1, 1096.0};
		System.out.println("The max entry in the Array is: " + max(dblEntries));		
	}
	
	/**
	 * Determines the Max element in an array
	 * 
	 * @param list ArrayList containing user entries
	 */
	
	private static <E extends Comparable<E>> E max(E[] list) {
		E currentMax;
		currentMax = list[0];

		for (E e : list) {
			if (currentMax.compareTo(e) < 0) { //if currentMax is less than e return -1
				currentMax = e; //assign currentMax to e
			}
		}
		return currentMax;
	}

	/**
	 * Displays the items in the Generic Array to the console in the order they were
	 * entered into the ArrayList
	 * 
	 * @param list Generic Array containing user entries
	 */
	private static <E> void DisplayList(E[] list) {
		for (int i = 0; i < list.length; i++) {
			if (i < list.length - 1)
				System.out.print(list[i] + ", ");
			else
				System.out.print(list[i] + "\n");
		}
	}

}
