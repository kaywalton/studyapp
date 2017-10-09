package delmas.walton.studyapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * List storing all the banks of questions
 * Used to easily write all the banks to file
 * @author Isabelle Delmas
 * creation: October 6 2017
 * updates : October 7 2017 	by: Isabelle Delmas		Reason : continued implementation
 * updates : October 8 2017 	by: Isabelle Delmas		Reason : continued implementation
 *
 */

public class ListofBanks implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554421568867838164L;
	private ArrayList<QuestionBank> list;
	
	/**
	 * Default constructor
	 */
	public ListofBanks() {
		this.list = new ArrayList<QuestionBank>();
	}
	
	/**
	 * Get a bank of question
	 * @param index : where the bank is in the list
	 * @return : a reference to the bank
	 */
	public QuestionBank getBank(int index) {
		if (index < this.list.size()) {
			return this.list.get(index);
		} else {
			return null;
		}
	}
	
	/**
	 * Get a bank of question
	 * @param bankName : name of the bank in the list
	 * @return : a reference to the bank
	 */
	public QuestionBank getBank(String bankName) {
		QuestionBank bank = null;
		for(int i = 0; i < this.list.size(); i++) {
			if(this.list.get(i).getName().toUpperCase().equals(bankName.toUpperCase())) {
				bank = this.list.get(i);
			}
		}
		return bank;
	}
	
	/**
	 * Add a bank to the list
	 * @param bank : bank to add
	 */
	public void addBank(QuestionBank bank) {
		if(bank != null) {
			this.list.add(bank);
		}
	}
	
	/**
	 * Temp toString
	 * Use the default toString of the ArrayList
	 */
	public String toString() {
		return this.list.toString();
	}
	
}
