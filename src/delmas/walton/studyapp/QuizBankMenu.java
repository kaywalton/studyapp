package delmas.walton.studyapp;

import java.util.ArrayList;

public class QuizBankMenu {
	private ArrayList<String> bankList;
	private String fileName;
	
	/**
	 * Constructor
	 * @param newFileName : file of the name storing the list of bank
	 */
	public QuizBankMenu(String newFileName) {
		this.fileName = newFileName + ".txt";
		this.bankList = new ArrayList<String>();
		
		// Check if the file can be open
			// If open read in the list of bank
		
		// Else create a file.
	}
	
	/**
	 * Add a new bank to the list of bank
	 * @param bankName : the name of the new bank
	 * @return : true is the bank was successfully added
	 */
	public boolean createNewBank(String bankName) {
		// Check if the file already exists
		// If so return false
		
		// Else create the file
			// ad the name of the file to the list
			// update the file storing the list of banks
			this.updateFile();
		return false;
	}
	
	/**
	 * Updates the file storing the list of banks
	 * @return : true if the file was successfully updated
	 */
	private boolean updateFile() {
		return false;
	}
}
