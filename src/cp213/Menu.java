package cp213;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Stores a List of MenuItems and provides a method return these items in a
 * formatted String. May be constructed from an existing List or from a file
 * with lines in the format:
 *
 * <pre>
1.25 hot dog
10.00 pizza
...
 * </pre>
 *
 * @author Alec Hallman
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2023-09-06
 */
public class Menu {

    // Attributes.
    // define a List of MenuItem objects
	List<MenuItem> menuItems = new ArrayList<>();
    // your code here

    /**
     * Creates a new Menu from an existing Collection of MenuItems. MenuItems are
     * copied into the Menu List.
     *
     * @param items an existing Collection of MenuItems.
     */
    public Menu(Collection<MenuItem> items) {

	// your code here
    	Iterator<MenuItem> allItems = items.iterator();
    	while(allItems.hasNext()) {
    		//System.out.println("Ran");
    		MenuItem newItem = allItems.next();
    		//System.out.println(newItem.toString());
    		menuItems.add(newItem);
    	}
    	
    }

    /**
     * Constructor from a Scanner of MenuItem strings. Each line in the Scanner
     * corresponds to a MenuItem. You have to read the Scanner line by line and add
     * each MenuItem to the List of items.
     *
     * @param fileScanner A Scanner accessing MenuItem String data.
     */
    public Menu(Scanner fileScanner) {

	// your code here
    	String newLine = fileScanner.nextLine();
    	while(newLine != null) {
    		newLine.strip();
    		String[] splitLine = newLine.split(" ");
    		String price = splitLine[0];
    		BigDecimal priceNumber = new BigDecimal(price);
    		String name = "";
    		for(int i = 1; i < splitLine.length; i++) {
    			name += splitLine[i] + " ";
    		}
    		//System.out.println("Item: " + name + "" + priceNumber + "$");
    		MenuItem newItem = new MenuItem(name, priceNumber);
    		menuItems.add(newItem);
    		//System.out.println(newItem.toString());
    		if(fileScanner.hasNext()) {
    			newLine = fileScanner.nextLine();
    		}
    		else {
    			break;
    		}
    	}
    	

    }

    /**
     * Returns the List's i-th MenuItem.
     *
     * @param i Index of a MenuItem.
     * @return the MenuItem at index i
     */
    public MenuItem getItem(int i) {

	// your code here
    	MenuItem fetchedItem = menuItems.get(i);

	return fetchedItem;
    }

    /**
     * Returns the number of MenuItems in the items List.
     *
     * @return Size of the items List.
     */
    public int size() {

	// your code here
    	int itemsSize = menuItems.size();

	return itemsSize;
    }

    /**
     * Returns the Menu items as a String in the format:
     *
     * <pre>
    5) poutine      $ 3.75
    6) pizza        $10.00
     * </pre>
     *
     * where n) is the index + 1 of the MenuItems in the List.
     */
    @Override
    public String toString() {

	// your code here
    	String returnString = "";
    	Integer counter = 1;
    	for(MenuItem item : menuItems) {
    		returnString += counter.toString() + ") " + item.toString();
    		if(counter != this.size()) {
    			returnString += "\n";
    		}
    		counter++;
    	}

	return returnString;
    }
}