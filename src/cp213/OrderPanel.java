package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterException;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The GUI for the Order class.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2023-09-06
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    // Attributes
    private Menu menu = null; // Menu attached to panel.
    private final Order order = new Order(); // Order to be printed by panel.
    // GUI Widgets
    private final JButton printButton = new JButton("Print");
    private final JLabel subtotalLabel = new JLabel("$0");
    private final JLabel taxLabel = new JLabel("$0");
    private final JLabel totalLabel = new JLabel("$0");
    private final JLabel subtotal = new JLabel("Subtotal:");
    private final JLabel tax = new JLabel("Tax:");
    private final JLabel total = new JLabel("Total:");
    private final JLabel item = new JLabel("Item");
    private final JLabel price = new JLabel("Price");
    private final JLabel quantity = new JLabel("Quantity");
    


    private JLabel nameLabels[] = null;
    private JLabel priceLabels[] = null;
    // TextFields for menu item quantities.
    private JTextField quantityFields[] = null;

    /**
     * Displays the menu in a GUI.
     *
     * @param menu The menu to display.
     */
    public OrderPanel(final Menu menu) {
	this.menu = menu;
	//System.out.println(this.menu.size());
	this.nameLabels = new JLabel[this.menu.size()];
	//System.out.println(this.nameLabels.length);
	this.priceLabels = new JLabel[this.menu.size()];
	this.quantityFields = new JTextField[this.menu.size()];
	this.layoutView();
	this.registerListeners();
    }

    private void FillLabels() {
    	
    	if(this.menu != null && this.nameLabels.length > 0) {
    		int counter = 0;
    		for(JLabel label:this.nameLabels) {
    			
    			String name = this.menu.getItem(counter).getName();
    			BigDecimal price = this.menu.getItem(counter).getPrice();
    			JLabel prices = new JLabel("$" + price.toString());
    			prices.setHorizontalAlignment(SwingConstants.RIGHT);
    			this.priceLabels[counter] = prices;
    			//label.setText(name);
    			label = new JLabel(name);
    			label.setHorizontalAlignment(SwingConstants.LEFT);
    			this.nameLabels[counter] = label;
    			//System.out.println(label.getText());
    			counter++;
    		}
    	}
    }
    private void InitializeQuantityFields() {
    	int counter = 0;
    	for(JTextField enterBox:this.quantityFields) {
    		enterBox = new JTextField("0");
    		enterBox.setHorizontalAlignment(SwingConstants.RIGHT);
    		enterBox.addFocusListener(new QuantityListener(this.menu.getItem(counter)));
    		this.quantityFields[counter] = enterBox;
    		counter++;
    	}
    }
    
    
    /**
     * Implements an ActionListener for the 'Print' button. Prints the current
     * contents of the Order to a system printer or PDF.
     */
    private class PrintListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

	    // your code here
		//This correctly prints a PDF document in the easiest way I could find. But I couldn't get the string formatting to transition
		//Hopefully that's not a huge deal in terms of marking, I tried for quite a while but the internet wasn't making a whole lot of sense.
		//A lot of people were talking about converting it to some HTML string with </div> and wacky stuff like that
		//and yeah that's beyond my current understanding, so I'm just gonna leave it at this.
		JEditorPane editorPane = new JEditorPane();
		editorPane.setText(order.toString());
		try {
			editorPane.print();
		} catch (PrinterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
    }
    /**
     * Implements a FocusListener on a JTextField. Accepts only positive integers,
     * all other values are reset to 0. Adds a new MenuItem to the Order with the
     * new quantity, updates an existing MenuItem in the Order with the new
     * quantity, or removes the MenuItem from the Order if the resulting quantity is
     * 0.
     */
    private class QuantityListener implements FocusListener {
	private MenuItem item = null;
	Integer currentValue = 0;
	QuantityListener(final MenuItem item) {
	    this.item = item;
	   // System.out.println(item.getName());
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		JTextField source = (JTextField) e.getSource();
		//System.out.println("Focused on: " + source.getText());
		currentValue = Integer.parseInt(source.getText());
		//System.out.println("Focused on: " + source.getText());
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(order.toString());
		JTextField source = (JTextField) e.getSource();
		Integer stringInt;
		stringInt = Integer.parseInt(source.getText().replace(",", ""));
		if(!stringInt.equals(currentValue)) {
			//If the value has been changed
			order.update(this.item, stringInt);
			UpdateTotals();
			//System.out.println("Value Changed");
		}
		
	}
	

	// your code here
    }
    private void UpdateTotals() {
    	//order.getSubTotal();
    	//String format = String.format("%.2", )
    	this.subtotalLabel.setText("$" +order.getSubTotal().toString());
    	this.taxLabel.setText("$" + order.getTaxes().toString());
    	this.totalLabel.setText("$" + order.getTotal().toString());
    	//order.getTotal();
    	//;
    	//order.
    }

    /**
     * Layout the panel.
     */
    private void layoutView() {
	// your code here
    	this.FillLabels();
    	this.InitializeQuantityFields();
    	//this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	int gridSize = this.menu.size() + 5;
    	this.setLayout(new GridLayout(gridSize,3));
    	//this.setLayout(new BorderLayout());
    	//System.out.println(this.nameLabels[0].getText());
    	int counter = 0;
    	this.add(item);
    	this.add(price);
    	this.add(quantity);
    	//this.setLayout(new GridLayout(11,3));
    	for(JLabel label:this.nameLabels) {
    		this.add(label);
    		this.add(this.priceLabels[counter]);
    		this.add(this.quantityFields[counter]);
    		counter++;
    	}
    	this.TotalInformation();
    	
    	
    	
    }
    private void TotalInformation() {
    	subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	for(int i = 0; i < 4; i++) {
    		if(i == 0) {
    			this.add(this.subtotal);
    			this.add(new JLabel(" "));
    			this.add(this.subtotalLabel);
    		}else if(i == 1) {
    			this.add(this.tax);
    			this.add(new JLabel(" "));
    			this.add(this.taxLabel);
    		}else if(i == 2) {
    			this.add(this.total);
    			this.add(new JLabel(" "));
    			this.add(this.totalLabel);
    		}else if(i == 3) {
    			this.add(new JLabel(" "));
    			this.add(this.printButton);
    			this.add(new JLabel(" "));
    		}
    	}
    }

    /**
     * Register the widget listeners.
     */
    private void registerListeners() {
	// Register the PrinterListener with the print button.
	this.printButton.addActionListener(new PrintListener());
	//this.
	// your code here
    }

}