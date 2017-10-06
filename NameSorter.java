package com.examples.coding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/** ========================================================================================================
 * class NameSorter
 * 
 * [HOMEWORK HELP] Completed a homework assignment for an individual; provided a walkthrough of solution.
 * This is simply a quick-and-dirty solution. It functions as it should and meets the requirements
 * described in the document.
 * 
 * INSTRUCTIONS:
 * - Ask users to enter a first, middle and last name. Implement this using a loop to store a collection
 *   of names.
 * - Users can sort names in ascending or descending order. No mention of implementing a sorting 
 *   algorithm was mentioned, so I used the Collections.sort() method.
 * - Print sorted names to the console.
 * 
 * @author  Samone Morris
 * @version 1.0.0
 * @done	10/05/17
 * ========================================================================================================
 */

public class NameSorter {
	/** =============================================================================================== **/
	/** ========================================== VARIABLES ========================================== **/
	/** =============================================================================================== **/		
	private ArrayList<String> names;
	
	private byte sort_state;					// For efficiency-sake, I do not want to perform a				
	private final byte STATE_EMPTY = -1,		// a sort task on list of names, that I have already
					   STATE_UNSORTED = 0,		// sorted. I should only sort a list of names
				  	   STATE_SORTED = 1,		// when 1 or more names have been inputed and 
				  	   ORDER_ASCENDING = 2,		// successfully stored. These states should help
				  	   ORDER_DESCENDING = 3;	// prevent this problem.
	
	/** =============================================================================================== **/
	/** ======================================== CONSTRUCTORS ========================================= **/
	/** =============================================================================================== **/	
	public NameSorter(){
		this.names = new ArrayList<String>();
		this.sort_state = STATE_EMPTY;
	}// end NameSorter()
	
	/** =============================================================================================== **/
	/** ===================================== METHODS / FUNCTIONS ===================================== **/
	/** =============================================================================================== **/
	
	/** --------------------------------------------------------------------------------------------------
	 * addName()
	 * 
	 * Inserts a new name into the list of names. Blank names are not accepted.
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @return				true if the name was stored, false otherwise
	 * @throws Exception	if all parameters are NULL, cease the application
	 */
	public boolean addName(String firstName, String middleName, String lastName) throws Exception {
		if( firstName == "\0" && firstName == middleName && firstName == lastName ){
			throw new Exception("First, Middle, and Last Name are ALL blank");
		}// end if
		
		StringBuilder fullName = new StringBuilder()
									.append(firstName + " ")
									.append(middleName + " ")
									.append(lastName);
				
		names.add( fullName.toString() );
		System.out.println("\n[SUCCESS] Added name : " + fullName.toString() + "\n");
		sort_state = STATE_UNSORTED;

		return true;
	}// end addName(...)
	
	/** --------------------------------------------------------------------------------------------------
	 * ascending()
	 * 
	 * Sorts and prints the name in ascending order
	 */
	public void ascending(){
		if( sort_state == STATE_EMPTY ){
			System.out.println("[FAIL] No names have been stored");
			return;
		}// end if

		/* -----------------------------------------------------------------
		 * Collections.sort() will already sort these names in ascending
		 * order. Only do this when the list is unsorted. It is wasteful
		 * to try to sort a previously sorted list 	
		 */
		if( sort_state == STATE_UNSORTED ){
			Collections.sort( names );
			sort_state = STATE_SORTED;
		}// end if
			
		print(ORDER_ASCENDING);
	}// end ascending()

	/** --------------------------------------------------------------------------------------------------
	 * descending()
	 * 
	 * Sorts and prints the name in descending order
	 */
	public void descending(){
		if( sort_state == STATE_EMPTY ){
			System.out.println("[FAIL] No names have been stored");
			return;
		}// end if

		/* -----------------------------------------------------------------
		 * I could user Collections.reverse() to sort in the opposite order.
		 * But why not save time/space re-arranging the entire list, 
		 * storing it, and then looping through it anyway to print it? 
		 * This is especially important, because I would have to re-sort
		 * an ascended-sorted list again.
		 * Just sort it in ascending order and use a loop to access the
		 * elements beginning from the last element.
		 */	
		if( sort_state == STATE_UNSORTED ){
			Collections.sort( names );
			sort_state = STATE_SORTED;
		}// end if
			
		print(ORDER_DESCENDING);		
	}// end descending()
	
	/** --------------------------------------------------------------------------------------------------
	 * print()
	 * 
	 * Prints the names in ascending or descending order (based on the parameter flag)
	 */
	private void print(byte order){
		int x;
		
		if( order == ORDER_ASCENDING ){
			x = 0;			
			System.out.println("[PRINT] Names in Ascending Order\n");
			
			while( x < names.size() ){
				System.out.println( names.get( x++ ) );
			}// end while
			
			return;
		} else {
			x = names.size() - 1;
			
			System.out.println("[PRINT] Names in Descending Order\n");

			while( x >= 0 ){
				System.out.println( names.get( x-- ) );
			}// end while
		}// end if / else 
	}// end print(...)
	
	/** =============================================================================================== **/
	/** ============================================ MAIN ============================================= **/
	/** =============================================================================================== **/	
	
	public static void main(String[] args) {
		NameSorter sorter = new NameSorter();
		Scanner scanner = new Scanner(System.in);
		String input = "\0";
		boolean quit = false;
		
		while( !quit ){
			System.out.print("\n[>] Press 'E' to enter a name. Press 'S' to sort. Press 'Q' to quit : ");
			input = scanner.nextLine().toUpperCase();
			
			switch( input.charAt(0) ){
				case 'E' : add(scanner, sorter);	break;
				case 'S' : sort(scanner, sorter);	break;
				case 'Q' : quit = true;				break;
				default  : System.out.println("\n[!] Enter a valid command. Returning to Main Menu");
			}// end switch
			
			System.out.println("------------------------------------------------------------------------");
		}// end while
	}// end main(...)
	
	/** --------------------------------------------------------------------------------------------------
	 * add()
	 * 
	 * Requests users to input a first, middle and last name. If all fields are left blank, it will 
	 * trigger an exception.
	 * 
	 * @param scanner		consumes user input
	 * @param sorter		Object that will insert the name into the backend list
	 */
	private static void add(final Scanner scanner, final NameSorter sorter){
		String firstName = null,
			   middleName = null,
			   lastName = null;

		System.out.print("Enter a first name (or leave blank): ");		
		firstName = scanner.nextLine().trim();
		if( firstName.isEmpty() ){ firstName = "\0"; }
		
		System.out.print("Enter a middle name (or leave blank): ");
		middleName = scanner.nextLine().trim();					
		if( middleName.isEmpty() ){ middleName = "\0"; }
		
		System.out.print("Enter a last name (or leave blank): ");
		lastName = scanner.nextLine().trim();
		if( lastName.isEmpty() ){ lastName = "\0"; }

		try {
			sorter.addName(firstName, middleName, lastName);
		} catch (Exception e) {
			e.printStackTrace();
		}// end try / catch
	}// end add()
	
	/** --------------------------------------------------------------------------------------------------
	 * sort()
	 * 
	 *  Requests if user wants to sort in Ascending Order ('1') or Descending Order ('2')
	 *  
	 * @param scanner		consumes user input
	 * @param sorter		Object that will sort all names previously entered and print them
	 */
	private static void sort(final Scanner scanner, final NameSorter sorter){
		System.out.println("\n[>] Press '1' for Ascending Order. Press '2' for Descending Order\n");
		String input = scanner.nextLine().toUpperCase();
		
		switch( input.charAt(0) ){
			case '1': sorter.ascending(); 	break;
			case '2': sorter.descending();	break;
			default : System.out.println("[!] Command not recognized. Returning to Main Menu");
		}// end switch
	}// end sort(...)
}// end class NameSorter
