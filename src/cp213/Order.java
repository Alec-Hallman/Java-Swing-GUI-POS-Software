package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author Alec Hallman
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2023-09-06
 */
public class Order implements Printable {

    /**
     * The current tax rate on menu items.
     */
    public static final BigDecimal TAX_RATE = new BigDecimal(0.13);

    // Attributes
    HashMap<MenuItem,Integer> items = new HashMap<MenuItem,Integer>();
    // your code here

    /**
     * Increments the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added.
     *
     * @param item     The MenuItem to purchase - the HashMap key.
     * @param quantity The number of the MenuItem to purchase - the HashMap value.
     */
    public void add(final MenuItem item, final int quantity) {
	// your code here
    	if(quantity > 0) {
    		if(items.containsKey(item)) {
    			//System.out.println("The Item is in hash Here's the quantity: " + items.get(item));
    			int currentQuantity = items.get(item);
    			items.put(item, currentQuantity + quantity);
    		}
    		else {
    			items.put(item, quantity);
    		}
    	}
    	//System.out.println(items.toString());

    }

    /**
     * Calculates the total value of all MenuItems and their quantities in the
     * HashMap.
     *
     * @return the total price for the MenuItems ordered.
     */
    public BigDecimal getSubTotal() {

	// your code here
    	BigDecimal subTotal = null;
    	try {
    		Iterator<Map.Entry<MenuItem, Integer>> iterator = items.entrySet().iterator();
        	while(iterator.hasNext()) {
        		Map.Entry<MenuItem, Integer> entry = iterator.next();
        		BigDecimal totalQuantity = (new BigDecimal(entry.getValue().toString()).multiply(entry.getKey().getPrice()));
        		if(subTotal != null) {
        			subTotal = subTotal.add(totalQuantity);
        		}
        		else {
        			subTotal = totalQuantity;
        		}
        		//System.out.println(subTotal.toString());
        				
        	}
        	//System.out.println(subTotal.toString());
    	}catch(Exception e) {
    		subTotal = new BigDecimal("0");
    	}
    	
    	
	return subTotal;
    }

    /**
     * Calculates and returns the total taxes to apply to the subtotal of all
     * MenuItems in the order. Tax rate is TAX_RATE.
     *
     * @return total taxes on all MenuItems
     */
    public BigDecimal getTaxes() {

	// your code here
    	BigDecimal taxes = new BigDecimal("0");
    	BigDecimal subTotal = this.getSubTotal();
    	if(!subTotal.equals(new BigDecimal("0"))) {
    		//System.out.println("Doing this");
    		taxes = subTotal.multiply(TAX_RATE);
    	}
    	taxes = taxes.setScale(2,RoundingMode.HALF_EVEN);

	return taxes;
    }

    /**
     * Calculates and returns the total price of all MenuItems order, including tax.
     *
     * @return total price
     */
    public BigDecimal getTotal() {

	// your code here
    	BigDecimal taxes = this.getTaxes();
    	BigDecimal subTotal = this.getSubTotal();
    	BigDecimal total = subTotal.add(taxes);

	return total;
    }

    /*
     * Implements the Printable interface print method. Prints lines to a Graphics2D
     * object using the drawString method. Prints the current contents of the Order.
     */
    @Override
    public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
	    throws PrinterException {
	int result = PAGE_EXISTS;

	if (pageIndex == 0) {
	    final Graphics2D g2d = (Graphics2D) graphics;
	    g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	    // Now we perform our rendering
	    final String[] lines = this.toString().split("\n");
	    int y = 100;
	    final int inc = 12;

	    for (final String line : lines) {
		g2d.drawString(line, 100, y);
		y += inc;
	    }
	} else {
	    result = NO_SUCH_PAGE;
	}
	return result;
    }

    /**
     * Returns a String version of a receipt for all the MenuItems in the order.
     */
    @Override
    public String toString() {

	// your code here
    	int counter = 0;
    	String returnString = "";
    	Iterator<Map.Entry<MenuItem, Integer>> iterator = items.entrySet().iterator();
    	while(iterator.hasNext()) {
    		Map.Entry<MenuItem, Integer> entry = iterator.next();
    		BigDecimal totalQuantity = (new BigDecimal(entry.getValue().toString()).multiply(entry.getKey().getPrice()));
    		returnString += String.format("%-15s%2d @ $%5.2f = $%6.2f\n", entry.getKey().getName(),entry.getValue(),entry.getKey().getPrice(),totalQuantity);
    		counter++;
    	}
    	//System.out.println(counter);
    	if(counter > 0) {
	    	BigDecimal total = this.getTotal();
	    	BigDecimal subTotal = this.getSubTotal();
	    	BigDecimal taxes = this.getTaxes();
	    	returnString += String.format("\n\nSubtotal:                    $%6.2f\nTaxes:                       $%6.2f\nTotal:                       $%6.2f\n",subTotal,taxes,total);
	    }else {
	    	returnString += String.format("\n\nSubtotal:                    $%6.2f\nTaxes:                       $%6.2f\nTotal:                       $%6.2f\n",0f,0f,0f);
	    }
	return returnString;
    }

    /**
     * Replaces the quantity of a particular MenuItem in an Order with a new
     * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
     * or negative, the MenuItem is removed from the Order.
     *
     * @param item     The MenuItem to update
     * @param quantity The quantity to apply to item
     */
    public void update(final MenuItem item, final int quantity) {

	// your code here
    	
    	if(items.containsKey(item)) {
    		if(quantity > 0) {
    			//System.out.println("Updating");
    			items.put(item,quantity);
    		}
    		else {
    			//System.out.println("Updating");
    			items.remove(item);
    		}
    	}else {
    		//System.out.println("It's not in the order. Q: " + quantity);
    		if(quantity > 0) {
    			//System.out.println("Updating");
    			items.put(item, quantity);
    		}
    	}

    }
}