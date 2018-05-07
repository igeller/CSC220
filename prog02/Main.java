package prog02;
import java.io.*;
/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				// Ask for the name.
				name = ui.getInfo("Enter name: ");
				// !!! Check for null (cancel) or "" (blank)
				if(name == null)
					break;
				else if (name.equals("")) {
					ui.sendMessage("Blank names not allowed");
					break;
				}
				// Ask for the number.
				number = ui.getInfo("Enter the new number: ");
				// !!! Check for cancel.  Blank is o.k.
				if(number == null)
					break;
				// Call addOrChangeEntry
			//	number = pd.
				oldNumber = pd.addOrChangeEntry(name, number);
				// Report the result
				if(oldNumber != null) {
					ui.sendMessage("Number for " + name + " was changed.\n"
							+ "Old Number: " + oldNumber
							+ "\nNew Number: " + number);
				}
				else	{
					ui.sendMessage(name + " was added to the directory.\n"
							+ "New number: " + number);
				}
				break;
			case 1:
				// implement
				name = ui.getInfo("Who do you want to look up?");
				if(name == null)
					break;
				if(name.equals("")) {
					ui.sendMessage("Blank names not allowed");
					break;
				}
				ui.sendMessage("You want to look up: " + name);
				number = pd.lookupEntry(name);
				//System.out.println(number);
				if(number == null)
					ui.sendMessage("Name is not there.");
				else
					ui.sendMessage("The number is: " + number);
				
				/*if(number != null)
					ui.sendMessage("The number is: " + number);
				else
					ui.sendMessage("Name is not there.");*/
				break;
			case 2:
				// implement
				name = ui.getInfo("Who do you want to remove?");
				
				if(name == null)
					break;
				else if (name.equals("")) {
					ui.sendMessage("Blank names not allowed");
					break;
				}
				
				number = pd.removeEntry(name);
				if(number != null)
					ui.sendMessage("Removed entry with name " + name + " and number " + number);
				else
					ui.sendMessage("Name was not in directory.");
				
				break;
			case 3:
				// implement
				pd.save();
				break;
			case 4:
				// implement
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
