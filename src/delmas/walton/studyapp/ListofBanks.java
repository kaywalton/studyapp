package delmas.walton.studyapp;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public void addBank(QuestionBank bank) {
		if(bank != null) {
			this.list.add(bank);
		}
	}
	
	public String toString() {
		return this.list.toString();
	}
	
}
