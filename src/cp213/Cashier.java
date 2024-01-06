package cp213;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2023-09-06
 */
public class Cashier {

    // Attributes
    private Menu menu = null;
    private String LINE = "----------------------------------------";

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
   // System.out.println(this.menu.size());
    char maxInput = (char) this.menu.size();
    Order newOrder = new Order();
	System.out.println("Welcome to WLU Foodorama!");
	this.printCommands();
	Scanner input = new Scanner(System.in);
	Scanner input2 = new Scanner(System.in);
	String valueInserted = "1";
	boolean commandThrew = false;
	while(valueInserted.charAt(0) != '0') {
		try{
			//newOrder.add(name, quantity);
			System.out.print("Command: ");
			valueInserted = input.nextLine();
			//System.out.printf("\n");
			int inputValue = (int)(valueInserted.charAt(0) - '0');
			if(valueInserted.length() > 1) {
				commandThrew = true;
				throw(new InputMismatchException("Not a valid number"));
				
			}
			
			if(inputValue > (int)maxInput) {
				//System.out.println("Char value given: " + inputValue + " Max Char value: " + (int) maxInput);
				this.printCommands();
			}
			else if(valueInserted.charAt(0) != '0'){
				System.out.print("How many do you want? ");
				int quantity;
				if(!input2.hasNextInt()) {
					input2.nextLine();
					commandThrew = false;
					throw(new InputMismatchException("Not a valid number"));
				}
				quantity = input2.nextInt();
				newOrder.add(this.menu.getItem(inputValue - 1), quantity);
			}
		} catch(InputMismatchException e) {
			System.out.println(e.getMessage());
			if(commandThrew) {
				this.printCommands();
			}
			//valueInserted = input.nextLine();
		}

	}
	input.close();
	input2.close();
	// your code here
	System.out.println(LINE + "\nReceipt\n" + newOrder.toString());
	return null;
    }
}