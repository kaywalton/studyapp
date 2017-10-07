package delmas.walton.studyapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class of the StudyApp application
 * Takes care of all the input/output and communication with the user
 * @author Isabelle Delmas
 * creation: October 6 2017
 * updates : October 7 2017 	by: Isabelle Delmas		Reason : continued implementation
 *
 */

public class QuizBankMenu {
	private ListofBanks list;
	private String fileName;
	private final String HEADER = "Main Menu";
	private final String[] OPTIONS = {"Start Quiz", 
			"Start Review",
			"Display Statistics",
			"Create a new bank of questions", 
			"Modify an existing bank of questions",
			"Erase a bank of questions",
			"Display all lists of banks"};
	private final String CHOICE_PROMPT = "Your choice: ";
	private final int EXIT_VALUE = 0;
	private final String EXIT = EXIT_VALUE + ") Exit";
	private final String NAME_PROMPT = "Name of the bank of question: ";

	/**
	 * Constructor
	 * @param newFileName : file of the name storing the list of bank
	 */
	public QuizBankMenu(String newFileName) {
		this.fileName = newFileName + ".dat";
		this.list = new ListofBanks();
		File file = new File(this.fileName);

		// Check if the file can be open
		if(file.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
			{
				this.list = (ListofBanks) in.readObject();
			}// If the file was corrupted, does not contain the right information, or could not be open, create a new file 
			catch (FileNotFoundException e) {
				this.list = new ListofBanks();
			} catch (IOException e) {
				this.list = new ListofBanks();
			} catch (ClassNotFoundException e) {
				this.list = new ListofBanks();
			} 
		// If the file does not exists yet.
		} else {
			this.list = new ListofBanks();
		}
	}
	/**
	 * Display the header and the menu to the user
	 */
	private void displayMenu() {
		
		// Display the header
		System.out.println(this.HEADER);
		for(int i=0; i < this.HEADER.length(); i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		// Display the Menu
		for(int i = 0; i < this.OPTIONS.length; i++) {
			System.out.println(i+1 + ") " + this.OPTIONS[i]);
		}
	}
	
	/**
	 * Run the application. To be called by main after initiating the QuizBankMenu object
	 */
	public void runApplication() {
		this.displayMenu();
		int choice = getUserChoice(this.OPTIONS.length);
		
		switch (choice) {
		case 4: if (!this.createNewBank()) {
			System.out.println("That bank of questions already exits");
		};
		}
	}
	
	/**
	 * Ask the user to input an int between EXIT_VALUE (0) and max
	 * @param max : max number of input
	 * @return the choice of the user
	 */
	private int getUserChoice(int max) {
		Scanner in = new Scanner(System.in);
		System.out.println(this.EXIT);
		int userChoice = -1;
		while(userChoice < 0 || userChoice > max) {
			System.out.print(this.CHOICE_PROMPT);
			if(in.hasNextInt()) {
				userChoice = in.nextInt();
			}
			in.nextLine();
		}
		return userChoice;
	}
	/**
	 * Add a new bank to the list of bank
	 * @param bankName : the name of the new bank
	 * @return : true is the bank was successfully added
	 */
	private boolean createNewBank() {
		String bankName = "";
		boolean returnValue = false;
		Scanner in = new Scanner(System.in);		
		// Ask the user for the name of the new bank of questions
		System.out.print(this.NAME_PROMPT);
		bankName = in.nextLine();
		
		// Check if the bank does not exists yet
		if(this.list.getBank(bankName) == null) {
			// Create the bank
			QuestionBank newBank = new QuestionBank(bankName);
			// Add the bank to the list
			this.list.addBank(newBank);
			// Gives the user the option to modify the bank to add the questions
			this.updateBank(bankName);
			// The bank was successfully created
			returnValue = true;
		}
		// Else return false,
		
		// Updates the bank in the hard drive file
		this.updateFile();
		
		// Exit with feedback
		return returnValue;
	}
	
	private void updateBank(String bankName) {
		
	}
	/**
	 * Updates the file storing the list of banks
	 * Display an error if the file could not be updated
	 */
	private void updateFile() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.fileName));
			out.writeObject(this.list);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not update file, your work will not be saved");
		}
	}
}
